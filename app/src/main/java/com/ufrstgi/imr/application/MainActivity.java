package com.ufrstgi.imr.application;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.ufrstgi.imr.application.database.HoraireManager;
import com.ufrstgi.imr.application.database.MySQLite;
import com.ufrstgi.imr.application.database.PersonneManager;
import com.ufrstgi.imr.application.objet.Horaire;
import com.ufrstgi.imr.application.objet.Personne;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ServerHTTP serverHTTP;
    private final static int PORT = 8080;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        /* Test BDD */
        PersonneManager p = new PersonneManager(this);
        p.open();
        p.addPersonne(new Personne(0,"Westermann","Thomas","0621065701"));

        // Listing des enregistrements de la table
        Cursor c = p.getAllPersonne();
        if (c.moveToFirst()) {
            do {
                Log.d("test",
                        c.getInt(c.getColumnIndex(PersonneManager.KEY_ID_PERSONNE)) + "," +
                                c.getString(c.getColumnIndex(PersonneManager.KEY_NOM_PERSONNE)) + "," +
                                c.getString(c.getColumnIndex(PersonneManager.KEY_PRENOM_PERSONNE)) + "," +
                                c.getString(c.getColumnIndex(PersonneManager.KEY_TELEPHONE_PERSONNE))
                );
            } while (c.moveToNext());
        }
        c.close(); // fermeture du curseur

        p.close();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        serverHTTP = new ServerHTTP(PORT, getApplicationContext());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
