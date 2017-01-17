package com.ufrstgi.imr.application.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrstgi.imr.application.MainActivity;
import com.ufrstgi.imr.application.object.ClientScanResult;
import com.ufrstgi.imr.application.object.WifiApManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by Thomas Westermann on 17/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class BackgroundTasks  extends AsyncTask<Void, Void, Void> {

    Context context;

    public BackgroundTasks(Context context) {
        this.context = context;
    }

    @Override
    // Méthode qui effectue le background
    protected Void doInBackground(Void... params) {
        publishProgress();

        // On vérifie les colis qui sont connectés à notre Access Point
        // Premièrement on vériffier que l'AP est lancé
        checkIfAPEnabled();

        // Ensuite on regarde quels sont les colis connectées
        ArrayList<ClientScanResult> clients = scanColis();
        Log.d("Test", "Nb Colis connected : "+ clients.size());

        // Recupération du nombre de Colis dans la base de données
        int nbColisFromDataBase = 0;

        // On compare les deux indices
        if (clients.size() != nbColisFromDataBase) {
            // On requêtes les adresses MAC des colis de la base et on les compare à ceux du tableaux de clients

        }

        return null;
    }

    @Override
    // Méthode appelée avant que l'on lance le background
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("Test", "Background va commencer");
    }

    @Override
    // Méthode appelée à la fin du background
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Test", "Background terminé");
    }

    @Override
    // Méthode appellée par publishProress pendant le background
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        Log.d("Test", "Background en cours.....");
    }



    private ArrayList<ClientScanResult> scanColis() {
        WifiApManager wifiApManager = new WifiApManager(context);
        ArrayList<ClientScanResult> clients = wifiApManager.getClientList(false);

        /*for (ClientScanResult clientScanResult : clients) {
            Log.d("Test", "IpAddr: " + clientScanResult.getIpAddr());
            //Log.d("Test", "Device: " + clientScanResult.getDevice());
            Log.d("Test", "MacAddr: " + clientScanResult.getHWAddr());
            //Log.d("Test", "isReachable: " + clientScanResult.isReachable());
        }*/

        return clients;
    }

    private void checkIfAPEnabled() {
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        final int apState;
        try {
            apState = (Integer) wifiManager.getClass().getMethod("getWifiApState").invoke(wifiManager);
            if (apState == 13) {
                Log.d("Test", "Wifi Enabled");
            } else {
                Log.d("Test", "Wifi Disabled");
                // Affichage d'une alert pour lancer l'AP

                // On arrette l'execution de la tache
                cancel(true);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}