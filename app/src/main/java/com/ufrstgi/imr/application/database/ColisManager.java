package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Adresse;
import com.ufrstgi.imr.application.objet.Camion;
import com.ufrstgi.imr.application.objet.Chauffeur;
import com.ufrstgi.imr.application.objet.Client;
import com.ufrstgi.imr.application.objet.Colis;
import com.ufrstgi.imr.application.objet.Latlng;
import com.ufrstgi.imr.application.objet.Niveau;
import com.ufrstgi.imr.application.objet.Operation;
import com.ufrstgi.imr.application.objet.Personne;
import com.ufrstgi.imr.application.objet.Tournee;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ColisManager {

    private static final String TABLE_NAME = "colis";
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

    public Cursor getAllColis() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }

}
