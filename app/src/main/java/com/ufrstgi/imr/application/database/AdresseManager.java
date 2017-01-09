package com.ufrstgi.imr.application.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrstgi.imr.application.objet.Adresse;
import com.ufrstgi.imr.application.objet.Latlng;

/**
 * Created by Thomas Westermann on 08/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class AdresseManager {

    private static final String TABLE_NAME = "adresse";
    public static final String KEY_ID_ADRESSE = "id_adresse";
    public static final String KEY_RUE = "rue";
    public static final String KEY_CODE_POSTAL= "code_postal";
    public static final String KEY_VILLE= "ville";
    public static final String KEY_PAYS= "pays";
    public static final String KEY_ID_LATLNG= "id_latlng";

    public static final String CREATE_TABLE_ADRESSE =
            "CREATE TABLE "+TABLE_NAME+ " (" +
                    " "+KEY_ID_ADRESSE+" INTEGER primary key," +
                    " "+KEY_RUE+" TEXT," +
                    " "+KEY_CODE_POSTAL+" INTEGER," +
                    " "+KEY_VILLE+" TEXT," +
                    " "+KEY_PAYS+" TEXT," +
                    " "+KEY_ID_LATLNG+" INTEGER," +
                    " FOREIGN KEY("+KEY_ID_LATLNG+") REFERENCES latlng("+KEY_ID_LATLNG+") " +
                    ");";
    private MySQLite maBaseSQLite;
    private SQLiteDatabase db;
    Context context;

    public AdresseManager(Context context) {
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

    public long addAdresse(Adresse adresse) {
        // Ajout d'un enregistrement dans la table

        ContentValues values = new ContentValues();
        values.put(KEY_RUE, adresse.getRue());
        values.put(KEY_CODE_POSTAL, adresse.getCode_postal());
        values.put(KEY_VILLE, adresse.getVille());
        values.put(KEY_PAYS, adresse.getPays());
        values.put(KEY_ID_LATLNG, adresse.getLatlng().getId_latlng());

        // insert() retourne l'id du nouvel enregistrement inséré, ou -1 en cas d'erreur
        return db.insert(TABLE_NAME,null,values);
    }

    public int updateAdresse(Adresse adresse) {
        // modification d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la requête

        ContentValues values = new ContentValues();
        values.put(KEY_RUE, adresse.getRue());
        values.put(KEY_CODE_POSTAL, adresse.getCode_postal());
        values.put(KEY_VILLE, adresse.getVille());
        values.put(KEY_PAYS, adresse.getPays());
        values.put(KEY_ID_LATLNG, adresse.getLatlng().getId_latlng());

        String where = KEY_ID_ADRESSE+" = ?";
        String[] whereArgs = {adresse.getId_adresse()+""};

        return db.update(TABLE_NAME, values, where, whereArgs);
    }

    public int deleteAdresse(Adresse adresse) {
        // suppression d'un enregistrement
        // valeur de retour : (int) nombre de lignes affectées par la clause WHERE, 0 sinon

        String where = KEY_ID_ADRESSE+" = ?";
        String[] whereArgs = {adresse.getId_adresse()+""};

        return db.delete(TABLE_NAME, where, whereArgs);
    }

    public Adresse getAdresse(int id) {
        // Retourne le niveau dont l'id est passé en paramètre

        Latlng latlng = new Latlng(0,0,0);
        Adresse a=new Adresse(0,"",0,"","",latlng);

        Cursor c = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+KEY_ID_ADRESSE+"="+id, null);
        if (c.moveToFirst()) {
            a.setId_adresse(c.getInt(c.getColumnIndex(KEY_ID_ADRESSE)));
            a.setRue(c.getString(c.getColumnIndex(KEY_RUE)));
            a.setCode_postal(c.getInt(c.getColumnIndex(KEY_CODE_POSTAL)));
            a.setVille(c.getString(c.getColumnIndex(KEY_VILLE)));
            a.setPays(c.getString(c.getColumnIndex(KEY_PAYS)));

            int id_latlng = c.getInt(c.getColumnIndex(KEY_ID_LATLNG));
            LatlngManager latlngManager = new LatlngManager(context);
            latlngManager.open();
            latlng = latlngManager.getLatlng(id_latlng);
            latlngManager.close();
            a.setLatlng(latlng);

            c.close();
        }

        return a;
    }

    public Cursor getAllAdresse() {
        // sélection de tous les enregistrements de la table
        return db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
    }
}
