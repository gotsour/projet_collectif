package com.ufrstgi.imr.application.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrstgi.imr.application.objet.PositionChauffeur;

/**
 * Created by Thomas Westermann on 07/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class MySQLite extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "db.sqlite";
    private static final int DATABASE_VERSION = 1;
    private static MySQLite sInstance;

    public static synchronized MySQLite getInstance(Context context) {
        if (sInstance == null) { sInstance = new MySQLite(context); }
        return sInstance;
    }

    private MySQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Création de la base de données
        // on exécute ici les requêtes de création des tables

        // Tables primaires = 0 clefs étrangères
        sqLiteDatabase.execSQL(NiveauManager.CREATE_TABLE_NIVEAU);
        sqLiteDatabase.execSQL(AdresseManager.CREATE_TABLE_ADRESSE);
        sqLiteDatabase.execSQL(PersonneManager.CREATE_TABLE_PERSONNE);
        sqLiteDatabase.execSQL(CamionManager.CREATE_TABLE_CAMION);
        sqLiteDatabase.execSQL(LatlngManager.CREATE_TABLE_LATLNG);

        // Tables secondaires = 1 clef etrangère
        sqLiteDatabase.execSQL(ChauffeurManager.CREATE_TABLE_CHAUFFEUR);
        sqLiteDatabase.execSQL(HoraireManager.CREATE_TABLE_HORAIRE);
        sqLiteDatabase.execSQL(AdresseManager.CREATE_TABLE_ADRESSE);

        // Tables tertiaires = 2 clefs etrangeres
        sqLiteDatabase.execSQL(PositionChauffeurManager.CREATE_TABLE_POSITION_CHAUFFEUR);
        sqLiteDatabase.execSQL(TourneeManager.CREATE_TABLE_TOURNEE);
        sqLiteDatabase.execSQL(ClientManager.CREATE_TABLE_CLIENT);
        sqLiteDatabase.execSQL(OperationManager.CREATE_TABLE_OPERATION);

        // Table Colis
        sqLiteDatabase.execSQL(ColisManager.CREATE_TABLE_COLIS);

        // Table Position Colis
        sqLiteDatabase.execSQL(PositionColisManager.CREATE_TABLE_POSITION_COLIS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Mise à jour de la base de données
        // méthode appelée sur incrémentation de DATABASE_VERSION
        // on peut faire ce qu'on veut ici, comme recréer la base :
        onCreate(sqLiteDatabase);
    }

}
