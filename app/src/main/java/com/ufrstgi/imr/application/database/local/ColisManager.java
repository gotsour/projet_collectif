package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Camion;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Niveau;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Personne;
import com.ufrstgi.imr.application.object.Tournee;

import java.util.ArrayList;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ColisManager {

    public static final String TABLE_NAME = "colis";
    public static final String KEY_ID_COLIS = "id_colis";
    public static final String KEY_ADRESSE_MAC = "adresse_mac";
    public static final String KEY_POIDS_COLIS = "poids_colis";
    public static final String KEY_VOLUME_COLIS = "volume_colis";
    public static final String KEY_NIVEAU_BATTERIE_COLIS = "niveau_batterie_colis";
    public static final String KEY_TEMPERATURE_COLIS= "temperature_colis";
    public static final String KEY_CAPACITE_CHOC_COLIS= "capacite_choc_colis";
    public static final String KEY_ID_NIVEAU= "id_niveau";
    public static final String KEY_ID_LIVRAISON= "id_livraison";
    public static final String KEY_ID_RECEPTION= "id_reception";
    public static final String KEY_ID_TOURNEE= "id_tournee";
    public static final String KEY_ID_CLIENT= "id_client";

    public static final String CREATE_TABLE_COLIS =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_COLIS+" INTEGER primary key," +
                    " "+KEY_ADRESSE_MAC+" TEXT," +
                    " "+KEY_POIDS_COLIS+" REAL," +
                    " "+KEY_VOLUME_COLIS+" REAL," +
                    " "+KEY_NIVEAU_BATTERIE_COLIS+" REAL," +
                    " "+KEY_TEMPERATURE_COLIS+" REAL," +
                    " "+KEY_CAPACITE_CHOC_COLIS+" REAL," +
                    " "+KEY_ID_NIVEAU+" INTEGER," +
                    " "+KEY_ID_LIVRAISON+" INTEGER," +
                    " "+KEY_ID_RECEPTION+" INTEGER," +
                    " "+KEY_ID_TOURNEE+" INTEGER," +
                    " "+KEY_ID_CLIENT+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_NIVEAU+") REFERENCES niveau("+KEY_ID_NIVEAU+")," +
                    " FOREIGN KEY("+KEY_ID_LIVRAISON+") REFERENCES operation("+KEY_ID_LIVRAISON+")," +
                    " FOREIGN KEY("+KEY_ID_RECEPTION+") REFERENCES operation("+KEY_ID_RECEPTION+")," +
                    " FOREIGN KEY("+KEY_ID_TOURNEE+") REFERENCES tournee("+KEY_ID_TOURNEE+")," +
                    " FOREIGN KEY("+KEY_ID_CLIENT+") REFERENCES client("+KEY_ID_CLIENT+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public ColisManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
        this.context = context;
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addAllOfColis(Colis colis) {
        // Ajout d'un enregistrement dans la table
        this.close();
        NiveauManager niveauManager = new NiveauManager(context);
        niveauManager.open();
        niveauManager.addNiveau(colis.getNiveau());
        niveauManager.close();

        OperationManager operationManager= new OperationManager(context);
        operationManager.open();
        operationManager.addAllOfOperation(colis.getLivraison());
        operationManager.addAllOfOperation(colis.getReception());
        operationManager.close();

        /*TourneeManager tourneeManager = new TourneeManager(context);
        tourneeManager.open();
        long idTournee = tourneeManager.addAllOfTournee(colis.getTournee());
        tourneeManager.close();*/
        long idTournee=colis.getTournee().getId_tournee();

        ClientManager clientManager = new ClientManager(context);
        clientManager.open();
        clientManager.addAllOfClient(colis.getClient());
        clientManager.close();


        ContentValues values = new ContentValues();
        values.put(KEY_ID_COLIS, colis.getId_colis());
        values.put(KEY_ADRESSE_MAC, colis.getAdresse_mac());
        Log.d("adresseMAC","insert adresse mac : "+colis.getAdresse_mac());
        values.put(KEY_POIDS_COLIS, colis.getPoids_colis());
        values.put(KEY_VOLUME_COLIS, colis.getVolume_colis());
        values.put(KEY_NIVEAU_BATTERIE_COLIS, colis.getNiveau_batterie_colis());
        values.put(KEY_TEMPERATURE_COLIS, colis.getTemperature_colis());
        values.put(KEY_CAPACITE_CHOC_COLIS, colis.getCapacite_choc_colis());
        values.put(KEY_ID_NIVEAU, colis.getNiveau().getId_niveau());
        values.put(KEY_ID_LIVRAISON, colis.getLivraison().getId_operation());
        values.put(KEY_ID_RECEPTION, colis.getReception().getId_operation());
        values.put(KEY_ID_TOURNEE, idTournee);
        values.put(KEY_ID_CLIENT, colis.getClient().getId_client());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        this.open();
        return db.insert(TABLE_NAME,null,values);
    }

    public long addColis(Colis colis) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_COLIS, colis.getId_colis());
        values.put(KEY_ADRESSE_MAC, colis.getAdresse_mac());
        values.put(KEY_POIDS_COLIS, colis.getPoids_colis());
        values.put(KEY_VOLUME_COLIS, colis.getVolume_colis());
        values.put(KEY_NIVEAU_BATTERIE_COLIS, colis.getNiveau_batterie_colis());
        values.put(KEY_TEMPERATURE_COLIS, colis.getTemperature_colis());
        values.put(KEY_CAPACITE_CHOC_COLIS, colis.getCapacite_choc_colis());
        values.put(KEY_ID_NIVEAU, colis.getNiveau().getId_niveau());
        values.put(KEY_ID_LIVRAISON, colis.getLivraison().getId_operation());
        values.put(KEY_ID_RECEPTION, colis.getReception().getId_operation());
        values.put(KEY_ID_TOURNEE, colis.getTournee().getId_tournee());
        values.put(KEY_ID_CLIENT, colis.getClient().getId_client());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateColis(Colis colis) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_COLIS, colis.getId_colis());
        values.put(KEY_ADRESSE_MAC, colis.getAdresse_mac());
        values.put(KEY_POIDS_COLIS, colis.getPoids_colis());
        values.put(KEY_VOLUME_COLIS, colis.getVolume_colis());
        values.put(KEY_NIVEAU_BATTERIE_COLIS, colis.getNiveau_batterie_colis());
        values.put(KEY_TEMPERATURE_COLIS, colis.getTemperature_colis());
        values.put(KEY_CAPACITE_CHOC_COLIS, colis.getCapacite_choc_colis());
        values.put(KEY_ID_NIVEAU, colis.getNiveau().getId_niveau());
        values.put(KEY_ID_LIVRAISON, colis.getLivraison().getId_operation());
        values.put(KEY_ID_RECEPTION, colis.getReception().getId_operation());
        values.put(KEY_ID_TOURNEE, colis.getTournee().getId_tournee());
        values.put(KEY_ID_CLIENT, colis.getClient().getId_client());

        String where = KEY_ID_COLIS+" = ?";
        String[] whereArgs = {colis.getId_colis()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteColis(Colis colis) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_COLIS+" = ?";
        String[] whereArgs = {colis.getId_colis()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Colis getColis(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Niveau niveau = new Niveau(0,"",0);
        Latlng latlng = new Latlng(0,0,0);
        Adresse adresse = new Adresse(0,"",0,"","",latlng);
        Personne personne = new Personne(0,"","","");
        Client client = new Client(0,"","",adresse,personne);
        Operation livraison = null;
        Operation reception = null;
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Camion camion = new Camion("","",0,0,0);
        Tournee tournee = new Tournee(0,null,chauffeur,camion);
        Colis co = new Colis(0,"",0,0,0,0,0,niveau,livraison,reception,tournee,client);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_COLIS+"="+id, null);
        if (c.moveToFirst()) {
            co.setId_colis(c.getInt(c.getColumnIndex(KEY_ID_COLIS)));
            co.setAdresse_mac(c.getString(c.getColumnIndex(KEY_ADRESSE_MAC)));
            co.setPoids_colis(c.getFloat(c.getColumnIndex(KEY_POIDS_COLIS)));
            co.setVolume_colis(c.getFloat(c.getColumnIndex(KEY_VOLUME_COLIS)));
            co.setNiveau_batterie_colis(c.getFloat(c.getColumnIndex(KEY_NIVEAU_BATTERIE_COLIS)));
            co.setTemperature_colis(c.getFloat(c.getColumnIndex(KEY_TEMPERATURE_COLIS)));
            co.setCapacite_choc_colis(c.getFloat(c.getColumnIndex(KEY_CAPACITE_CHOC_COLIS)));

            int id_niveau = c.getInt(c.getColumnIndex(KEY_ID_NIVEAU));
            NiveauManager niveauManager = new NiveauManager(context);
            niveauManager.open();
            niveau = niveauManager.getNiveau(id_niveau);
            niveauManager.close();
            co.setNiveau(niveau);

            int id_livraison = c.getInt(c.getColumnIndex(KEY_ID_LIVRAISON));
            OperationManager operationManager = new OperationManager(context);
            operationManager.open();
            livraison = operationManager.getOperation(id_livraison);
            operationManager.close();
            co.setLivraison(livraison);

            int id_reception = c.getInt(c.getColumnIndex(KEY_ID_RECEPTION));
            operationManager.open();
            reception = operationManager.getOperation(id_reception);
            operationManager.close();
            co.setReception(reception);

            int id_tournee = c.getInt(c.getColumnIndex(KEY_ID_TOURNEE));
            TourneeManager tourneeManager = new TourneeManager(context);
            tourneeManager.open();
            tournee = tourneeManager.getTournee(id_tournee);
            tourneeManager.close();
            co.setTournee(tournee);

            int id_client = c.getInt(c.getColumnIndex(KEY_ID_CLIENT));
            ClientManager clientManager = new ClientManager(context);
            clientManager.open();
            client = clientManager.getClient(id_client);
            clientManager.close();
            co.setClient(client);

            c.close();
        }

        return co;
    }

    public ArrayList<Colis> getNextColis(int id) {
        ArrayList<Colis> mesColis = new ArrayList<Colis>();
        String date = "";

        Cursor c = db.rawQuery("SELECT o.date_theorique  FROM colis c, operation o WHERE c.id_tournee=" + id + " and (c.id_reception=o.id_operation " +
                "or c.id_livraison=o.id_operation) and o.heure_relle_operation =''" +
                 " ORDER BY Date(substr(date_theorique, 7, 4) || '-' || substr(date_theorique, 1, 2) || '-' || substr(date_theorique, 4, 2) || substr(date_theorique, 11, 9)) ASC Limit 1", null);
        if (c.moveToFirst()) {
            date = c.getString(c.getColumnIndex("date_theorique"));
        }
        Log.d("resultat","date obetenu pour la 1ere requete colis : "+date + " taille reponse : "+c.getCount());



        c = db.rawQuery("SELECT * FROM colis c, operation o WHERE c.id_tournee=" + id + " and (c.id_reception=o.id_operation " +
                        "or c.id_livraison=o.id_operation)" +
                " and o.date_theorique='" + date+"' and o.heure_relle_operation='' group by c.id_colis "
                , null);

        Log.d("resultat","date obetenu pour la 2eme requete colis : "+date + " taille reponse : "+c.getCount());
        mesColis = instancieColis(c);
        c.close();
        return mesColis;
    }

    public ArrayList<Colis> getColisFromTournee(int idTournee) {
        ArrayList<Colis> mesColis;
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id_tournee="+idTournee+"", null);
        mesColis = instancieColis(c);
        c.close();
        return mesColis;
    }

    public ArrayList<String> getMacAdressesFromTournee(int idTournee) {
        ArrayList<Colis> mesColis;
        ArrayList<String> mesAdresseMac = new ArrayList<>();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE id_tournee="+idTournee+"", null);
        mesColis = instancieColis(c);
        c.close();
        for (int i = 0 ; i < mesColis.size() ; i++) {
            mesAdresseMac.add(mesColis.get(i).getAdresse_mac());
        }
        return mesAdresseMac;
    }

    public ArrayList<Colis> getAllColis() {
        ArrayList<Colis> mesColis;
        Cursor c = db.rawQuery("SELECT * FROM colis ", null);
        Log.d("result","all my colis cursor size : "+c.getCount());
        mesColis = instancieColis(c);
        c.close();
        return mesColis;
    }

    public ArrayList<Colis> instancieColis(Cursor c) {
        ArrayList<Colis> mesColis = new ArrayList<>();

        Niveau niveau;
        Latlng latlng;
        Adresse adresse;
        Personne personne;
        Client client;
        Operation livraison;
        Operation reception;
        Chauffeur chauffeur;
        Camion camion;
        Tournee tournee;
        Colis co;

        if (c.moveToFirst()) {
            do {
                niveau = new Niveau(0,"",0);
                latlng = new Latlng(0,0,0);
                adresse = new Adresse(0,"",0,"","",latlng);
                personne = new Personne(0,"","","");
                client = new Client(0,"","",adresse,personne);
                livraison = null;
                reception = null;
                chauffeur = new Chauffeur("","",0,personne);
                camion = new Camion("","",0,0,0);
                tournee = new Tournee(0,null,chauffeur,camion);
                co = new Colis(0,"",0,0,0,0,0,niveau,livraison, reception,tournee,client);

                co.setId_colis(c.getInt(c.getColumnIndex(KEY_ID_COLIS)));
                co.setAdresse_mac(c.getString(c.getColumnIndex(KEY_ADRESSE_MAC)));
                co.setPoids_colis(c.getFloat(c.getColumnIndex(KEY_POIDS_COLIS)));
                co.setVolume_colis(c.getFloat(c.getColumnIndex(KEY_VOLUME_COLIS)));
                co.setNiveau_batterie_colis(c.getFloat(c.getColumnIndex(KEY_NIVEAU_BATTERIE_COLIS)));
                co.setTemperature_colis(c.getFloat(c.getColumnIndex(KEY_TEMPERATURE_COLIS)));
                co.setCapacite_choc_colis(c.getFloat(c.getColumnIndex(KEY_CAPACITE_CHOC_COLIS)));

                int id_niveau = c.getInt(c.getColumnIndex(KEY_ID_NIVEAU));
                NiveauManager niveauManager = new NiveauManager(context);
                niveauManager.open();
                niveau = niveauManager.getNiveau(id_niveau);
                niveauManager.close();
                co.setNiveau(niveau);

                int id_livraison = c.getInt(c.getColumnIndex(KEY_ID_LIVRAISON));
                OperationManager operationManager = new OperationManager(context);
                operationManager.open();
                livraison = operationManager.getOperation(id_livraison);
                operationManager.close();
                co.setLivraison(livraison);

                int id_reception = c.getInt(c.getColumnIndex(KEY_ID_RECEPTION));
                operationManager.open();
                reception = operationManager.getOperation(id_reception);
                operationManager.close();
                co.setReception(reception);

                int id_tournee = c.getInt(c.getColumnIndex(KEY_ID_TOURNEE));
                TourneeManager tourneeManager = new TourneeManager(context);
                tourneeManager.open();
                tournee = tourneeManager.getTournee(id_tournee);
                tourneeManager.close();
                co.setTournee(tournee);

                int id_client = c.getInt(c.getColumnIndex(KEY_ID_CLIENT));
                ClientManager clientManager = new ClientManager(context);
                clientManager.open();
                client = clientManager.getClient(id_client);
                clientManager.close();
                co.setClient(client);

                mesColis.add(co);
            } while (c.moveToNext());
        }

        c.close();

        return mesColis;
    }


}
