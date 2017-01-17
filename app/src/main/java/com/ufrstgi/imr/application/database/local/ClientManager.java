package com.ufrstgi.imr.application.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Personne;

import java.util.ArrayList;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ClientManager {

    public static final String TABLE_NAME = "client";
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
    Context context;

    public ClientManager(Context context) {
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

    public long addClient(Client client) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_ID_CLIENT, client.getId_client());
        values.put(KEY_NOM_CLIENT, client.getNom_client());
        values.put(KEY_TELEPHONE_CLIENT, client.getTelephone_client());
        values.put(KEY_ID_ADRESSE, client.getAdresse().getId_adresse());
        values.put(KEY_ID_PERSONNE, client.getPersonne().getId_personne());


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
        values.put(KEY_ID_ADRESSE, client.getAdresse().getId_adresse());
        values.put(KEY_ID_PERSONNE, client.getPersonne().getId_personne());

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

    public Client getClient(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Latlng latlng = new Latlng(0,0,0);
        Adresse adresse = new Adresse(0,"",0,"","",latlng);
        Personne personne = new Personne(0,"","","");
        Client cl = new Client(0,"","",adresse,personne);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_CLIENT+"="+id, null);
        if (c.moveToFirst()) {
            cl.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));
            cl.setNom_client(c.getString(c.getColumnIndex(KEY_NOM_CLIENT)));
            cl.setTelephone_client(c.getString(c.getColumnIndex(KEY_TELEPHONE_CLIENT)));

            int id_adresse = c.getInt(c.getColumnIndex(KEY_ID_ADRESSE));
            AdresseManager adresseManager= new AdresseManager(context);
            adresseManager.open();
            adresse = adresseManager.getAdresse(id_adresse);
            adresseManager.close();
            cl.setAdresse(adresse);

            int id_personne = c.getInt(c.getColumnIndex(KEY_ID_PERSONNE));
            PersonneManager personneManager= new PersonneManager(context);
            personneManager.open();
            personne = personneManager.getPersonne(id_personne);
            personneManager.close();
            cl.setPersonne(personne);

            c.close();
        }

        return cl;
    }

    public ArrayList<Client> getAllClient() {
        ArrayList<Client> mesClient = new ArrayList<>();
        // sélection de tous les enregistrements de la table
        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        Latlng latlng;
        Adresse adresse;
        Personne personne;
        Client cl;

        if (c.moveToFirst()) {
            do {
                latlng = new Latlng(0,0,0);
                adresse = new Adresse(0,"",0,"","",latlng);
                personne = new Personne(0,"","","");
                cl = new Client(0,"","",adresse,personne);

                cl.setId_client(c.getInt(c.getColumnIndex(KEY_ID_CLIENT)));
                cl.setNom_client(c.getString(c.getColumnIndex(KEY_NOM_CLIENT)));
                cl.setTelephone_client(c.getString(c.getColumnIndex(KEY_TELEPHONE_CLIENT)));

                int id_adresse = c.getInt(c.getColumnIndex(KEY_ID_ADRESSE));
                AdresseManager adresseManager= new AdresseManager(context);
                adresseManager.open();
                adresse = adresseManager.getAdresse(id_adresse);
                adresseManager.close();
                cl.setAdresse(adresse);

                int id_personne = c.getInt(c.getColumnIndex(KEY_ID_PERSONNE));
                PersonneManager personneManager= new PersonneManager(context);
                personneManager.open();
                personne = personneManager.getPersonne(id_personne);
                personneManager.close();
                cl.setPersonne(personne);

                mesClient.add(cl);
            }while (c.moveToNext());
        }
        c.close();
        return mesClient;
    }
}
