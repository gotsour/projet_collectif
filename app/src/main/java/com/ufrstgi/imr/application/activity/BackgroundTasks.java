package com.ufrstgi.imr.application.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.ufrstgi.imr.application.MainActivity;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.object.ClientScanResult;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.WifiApManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Thomas Westermann on 17/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class BackgroundTasks  extends AsyncTask<Void, String, String> {

    Context context;

    public BackgroundTasks(Context context) {
        this.context = context;
    }

    @Override
    // Méthode qui effectue le background
    protected String doInBackground(Void... params) {
        String error = "";
        publishProgress("Background en cours...");

        // On vérifie les colis qui sont connectés à notre Access Point
        // Premièrement on vériffier que l'AP est lancé
        if (checkIfAPEnabled()) {

            // Ensuite on regarde quels sont les colis connectées
            ArrayList<ClientScanResult> clients = scanColis();
            Log.d("Test", "Nb Colis connected : " + clients.size());

            // Recupération du nombre de Colis dans la base de données
            ArrayList<String> mesAdresseMac = new ArrayList<>();
            // On requêtes les adresses MAC des colis de la base et on les compare à ceux du tableaux de clients
            ColisManager colisManager = new ColisManager(context);
            colisManager.open();
            // TODO : Prendre l'id de la tournée qui va bien
            mesAdresseMac = colisManager.getMacAdressesFromTournee(1);
            colisManager.close();

            ArrayList<String> adressesMacClient = new ArrayList<>();
            ArrayList<String> temp = new ArrayList<>();
            for (int i = 0; i < clients.size(); i++) {
                adressesMacClient.add(clients.get(i).getHWAddr());
                temp.add(clients.get(i).getHWAddr());
            }

            // On compare les deux arraylists = suppression des elements egaux
            ArrayList<String> temp2 = mesAdresseMac;
            adressesMacClient.removeAll(temp2);
            mesAdresseMac.removeAll(temp);

            /*Log.d("Test2", "## Adresses mac connectées ##");
            for (int i = 0 ; i < adressesMacClient.size() ; i++) {
                Log.d("Test2", adressesMacClient.get(i).toString());
            }
            Log.d("Test2", "## Adresses mac en BDD ##");
            for (int i = 0 ; i < mesAdresseMac.size() ; i++) {
                Log.d("Test2", mesAdresseMac.get(i).toString());
            }*/

            // Si mesAdresseMac n'est pas vide, cela signifie qu'il manque des colis dans le camion
            if (mesAdresseMac.size() != 0) {
                // Il faut alerter le chauffeur
                Log.d("Test", "Colis missing : " + mesAdresseMac.size());
                error = "Missing "+mesAdresseMac.size();
            }
            // Si adresseMacClient n'est pas vide, cela signifie qu'il y a trop de colis dans le camion
            if (adressesMacClient.size() != 0) {
                // Il faut alerter le chauffeur
                Log.d("Test", "Too much Colis : " + adressesMacClient.size());
                error = "Too Much "+adressesMacClient.size();
            }

        } else {
            error = "AP";
        }

        return error;
    }

    @Override
    // Méthode appelée avant que l'on lance le background
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Test", "Background va commencer");
    }

    @Override
    // Méthode appelée à la fin du background
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d("Test", "Background terminé");
    }

    @Override
    // Méthode appellée par publishProress pendant le background
    protected void onProgressUpdate(String... text) {
        super.onProgressUpdate(text);
        Log.d("Test", text[0]);
    }

    private ArrayList<ClientScanResult> scanColis() {
        WifiApManager wifiApManager = new WifiApManager(context);
        ArrayList<ClientScanResult> clients = new ArrayList<>();
        clients = wifiApManager.getClientList(true);

        for (ClientScanResult clientScanResult : clients) {
            Log.d("Test", "IpAddr: " + clientScanResult.getIpAddr());
            //Log.d("Test", "Device: " + clientScanResult.getDevice());
            Log.d("Test", "MacAddr: " + clientScanResult.getHWAddr());
            //Log.d("Test", "isReachable: " + clientScanResult.isReachable());
        }

        return clients;
    }

    private boolean checkIfAPEnabled() {
        boolean response = false;
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final int apState;
        try {
            apState = (Integer) wifiManager.getClass().getMethod("getWifiApState").invoke(wifiManager);
            if (apState == 13) {
                Log.d("Test", "Wifi Enabled");
                response = true;
            } else {
                Log.d("Test", "Wifi Disabled");
                response = false;

            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return response;
    }
}