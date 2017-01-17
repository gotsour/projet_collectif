package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    public static final String KEY_POIDS_COLIS = "poids_colis";
    public static final String KEY_VOLUME_COLIS = "volume_colis";
    public static final String KEY_NIVEAU_BATTERIE_COLIS = "niveau_batterie_colis";
    public static final String KEY_TEMPERATURE_COLIS= "temperature_colis";
    public static final String KEY_CAPACITE_CHOC_COLIS= "capacite_choc_colis";
    public static final String KEY_ID_NIVEAU= "id_niveau";
    public static final String KEY_ID_OPERATION= "id_operation";
    public static final String KEY_ID_TOURNEE= "id_tournee";
    public static final String KEY_ID_CLIENT= "id_client";

    public static final String CREATE_TABLE_COLIS =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_COLIS+" INTEGER primary key," +
                    " "+KEY_POIDS_COLIS+" REAL," +
                    " "+KEY_VOLUME_COLIS+" REAL," +
                    " "+KEY_NIVEAU_BATTERIE_COLIS+" REAL," +
                    " "+KEY_TEMPERATURE_COLIS+" REAL," +
                    " "+KEY_CAPACITE_CHOC_COLIS+" REAL," +
                    " "+KEY_ID_NIVEAU+" INTEGER," +
                    " "+KEY_ID_OPERATION+" INTEGER," +
                    " "+KEY_ID_TOURNEE+" INTEGER," +
                    " "+KEY_ID_CLIENT+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_NIVEAU+") REFERENCES niveau("+KEY_ID_NIVEAU+")," +
                    " FOREIGN KEY("+KEY_ID_OPERATION+") REFERENCES operation("+KEY_ID_OPERATION+")," +
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

    public long addColis(Colis colis) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_COLIS, colis.getId_colis());
        values.put(KEY_POIDS_COLIS, colis.getPoids_colis());
        values.put(KEY_VOLUME_COLIS, colis.getVolume_colis());
        values.put(KEY_NIVEAU_BATTERIE_COLIS, colis.getNiveau_batterie_colis());
        values.put(KEY_TEMPERATURE_COLIS, colis.getTemperature_colis());
        values.put(KEY_CAPACITE_CHOC_COLIS, colis.getCapacite_choc_colis());
        values.put(KEY_ID_NIVEAU, colis.getNiveau().getId_niveau());
        values.put(KEY_ID_OPERATION, colis.getOperation().getId_operation());
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
        values.put(KEY_POIDS_COLIS, colis.getPoids_colis());
        values.put(KEY_VOLUME_COLIS, colis.getVolume_colis());
        values.put(KEY_NIVEAU_BATTERIE_COLIS, colis.getNiveau_batterie_colis());
        values.put(KEY_TEMPERATURE_COLIS, colis.getTemperature_colis());
        values.put(KEY_CAPACITE_CHOC_COLIS, colis.getCapacite_choc_colis());
        values.put(KEY_ID_NIVEAU, colis.getNiveau().getId_niveau());
        values.put(KEY_ID_OPERATION, colis.getOperation().getId_operation());
        values.put(KEY_ID_TOURNEE, colis.getTournee().getId_tournee());
        values.put(KEY_ID_CLIENT, colis.getClient().getId_client());

        String where = KEY_ID_OPERATION+" = ?";
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
        Operation operation = null;
        Chauffeur chauffeur = new Chauffeur("","",0,personne);
        Camion camion = new Camion("","",0,0,0);
        Tournee tournee = new Tournee(0,chauffeur,camion);
        Colis co = new Colis(0,0,0,0,0,0,niveau,operation,tournee,client);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_COLIS+"="+id, null);
        if (c.moveToFirst()) {
            co.setId_colis(c.getInt(c.getColumnIndex(KEY_ID_COLIS)));
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

            int id_operation = c.getInt(c.getColumnIndex(KEY_ID_OPERATION));
            OperationManager operationManager = new OperationManager(context);
            operationManager.open();
            operation = operationManager.getOperation(id_operation);
            operationManager.close();
            co.setOperation(operation);

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
            Cursor c = db.rawQuery("SELECT o.date_theorique  FROM colis c, operation o WHERE c.id_tournee=" + id + " and c.id_operation=o.id_operation " +
                    " ORDER BY date(o.date_theorique) DESC Limit 1", null);
            if (c.moveToFirst()) {
                date = c.getString(c.getColumnIndex("date_theorique"));
            }
            Log.d("result", "date : " + date);


            c = db.rawQuery("SELECT * FROM colis c, operation o WHERE c.id_tournee=" + id + " and c.id_operation=o.id_operation " +
                    " and o.date_theorique='" + date + "'", null);

            Niveau niveau;
            Latlng latlng;
            Adresse adresse;
            Personne personne;
            Client client;
            Operation operation;
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
                    operation = null;
                    chauffeur = new Chauffeur("","",0,personne);
                    camion = new Camion("","",0,0,0);
                    tournee = new Tournee(0,chauffeur,camion);
                    co = new Colis(0,0,0,0,0,0,niveau,operation,tournee,client);

                    co.setId_colis(c.getInt(c.getColumnIndex(KEY_ID_COLIS)));
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

                    int id_operation = c.getInt(c.getColumnIndex(KEY_ID_OPERATION));
                    OperationManager operationManager = new OperationManager(context);
                    operationManager.open();
                    operation = operationManager.getOperation(id_operation);
                    operationManager.close();
                    co.setOperation(operation);

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

    public ArrayList<Colis> getAllColis() {

        ArrayList<Colis> mesColis = new ArrayList<Colis>();
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);

        Niveau niveau;
        Latlng latlng;
        Adresse adresse;
        Personne personne;
        Client client;
        Operation operation;
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
                operation = null;
                chauffeur = new Chauffeur("","",0,personne);
                camion = new Camion("","",0,0,0);
                tournee = new Tournee(0,chauffeur,camion);
                co = new Colis(0,0,0,0,0,0,niveau,operation,tournee,client);

                co.setId_colis(c.getInt(c.getColumnIndex(KEY_ID_COLIS)));
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

                int id_operation = c.getInt(c.getColumnIndex(KEY_ID_OPERATION));
                OperationManager operationManager = new OperationManager(context);
                operationManager.open();
                operation = operationManager.getOperation(id_operation);
                operationManager.close();
                co.setOperation(operation);

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
