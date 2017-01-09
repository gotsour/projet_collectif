package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Client;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ClientManager {

    private static final String TABLE_NAME = "client";
    public static final String KEY_ID_CLIENT = "id_client";
    public static final String KEY_NOM_CLIENT = "nom_client";
    public static final String KEY_TELEPHONE_CLIENT = "telephone_client";
    public static final String KEY_ID_ADRESSE= "id_adresse";
    public static final String KEY_ID_PERSONNE= "id_personne";

    public static final String CREATE_TABLE_CLIENT =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_CLIENT+" INTEGER primary key," +
                    " "+KEY_NOM_CLIENT+" TEXT," +
                    " "+KEY_TELEPHONE_CLIENT+" TEXT," +
                    " "+KEY_ID_ADRESSE+" INTEGER," +
                    " "+KEY_ID_PERSONNE+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_ADRESSE+") REFERENCES adresse("+KEY_ID_ADRESSE+")," +
                    " FOREIGN KEY("+KEY_ID_PERSONNE+") REFERENCES personne("+KEY_ID_PERSONNE+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;

    public ClientManager(Context context) {
        maBaseSQLite = MySQLite.getInstance(context);
    }

    public void open() {
        //on ouvre la table en lecture/écriture
        db = maBaseSQLite.getWritableDatabase();
    }

    public void close() {
        //on ferme l'accès à la BDD
        db.close();
    }

    public long addClient(Client client) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CLIENT, client.getId_client());
        values.put(KEY_NOM_CLIENT, client.getNom_client());
        values.put(KEY_TELEPHONE_CLIENT, client.getTelephone_client());
        values.put(KEY_ID_ADRESSE, client.getId_adresse());
        values.put(KEY_ID_PERSONNE, client.getId_personne());


        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateClient(Client client) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CLIENT, client.getId_client());
        values.put(KEY_NOM_CLIENT, client.getNom_client());
        values.put(KEY_TELEPHONE_CLIENT, client.getTelephone_client());
        values.put(KEY_ID_ADRESSE, client.getId_adresse());
        values.put(KEY_ID_PERSONNE, client.getId_personne());

        String where = KEY_ID_CLIENT+" = ?";
        String[] whereArgs = {client.getId_client()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteClient(Client client) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_CLIENT+" = ?";
        String[] whereArgs = {client.getId_client()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Client getClient(Long id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Client cl = new Client(0,"","",0,0);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CLIENT+"="+id, null);
        if (c.moveToFirst()) {
            cl.setId_client(c.getLong(c.getColumnIndex(KEY_ID_CLIENT)));
            cl.setNom_client(c.getString(c.getColumnIndex(KEY_NOM_CLIENT)));
            cl.setTelephone_client(c.getString(c.getColumnIndex(KEY_TELEPHONE_CLIENT)));
            cl.setId_adresse(c.getLong(c.getColumnIndex(KEY_ID_ADRESSE)));
            cl.setId_personne(c.getLong(c.getColumnIndex(KEY_ID_PERSONNE)));

            c.close();
        }

        return cl;
    }

    public Cursor getAllClient() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
