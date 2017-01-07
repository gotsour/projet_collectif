package com.ufrstgi.imr.application.storage;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Duduf on 23/12/2016.
 */

@Database(name = DatabaseColis.NAME, version = DatabaseColis.VERSION)
public class DatabaseColis {
    public static final String NAME = "Colis_db";

    public static final int VERSION = 1;
}