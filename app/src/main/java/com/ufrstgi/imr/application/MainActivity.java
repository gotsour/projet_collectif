package com.ufrstgi.imr.application;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.ufrstgi.imr.application.Fragment.FragmentColis;
import com.ufrstgi.imr.application.Fragment.FragmentFeuilleRoute;
import com.ufrstgi.imr.application.Fragment.FragmentNavigation;
import com.ufrstgi.imr.application.activity.ServerHTTP;
import com.ufrstgi.imr.application.activity.SettingsActivity;
import com.ufrstgi.imr.application.database.local.*;
import com.ufrstgi.imr.application.database.server.Communication;
import com.ufrstgi.imr.application.object.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ServerHTTP serverHTTP;
    private final static int PORT = 8080;
    List<Colis> mesColis;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String idUSer;
    TextView tvIdChauffeur;
    TextView tvIdCamion;
    Tournee tournee;
    FragmentPagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        tvIdChauffeur = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvIdChauffeur);
        tvIdCamion = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvIdCamion);

        idUSer="chauffeur0001";

        SynchronizeFromServer sync = new SynchronizeFromServer();
        sync.execute();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("lancement", "avant lancement thread");
                Communication com = new Communication(getApplicationContext(), idUSer);
                com.synchronizeFromServerSynchrone();
                Log.d("lancement", "apres lancement thread");
            }
        }).start();*/

        //todo à mettre en place, pour cas ou pas encore app installé ->sans bdd
        /*TourneeManager tourneeManager = new TourneeManager(this);
        tourneeManager.open();
        Tournee tournee=tourneeManager.getTournee();
        tourneeManager.close();


         if(tournee==null){
             //si premier chargement lancer avec id user saisie
             idUSer="chauffeur0001";
            // tvIdChauffeur.setText(idUSer);
            // tvIdCamion.setText("Mon camion");
             SynchronizeFromServer sync = new SynchronizeFromServer(this);
             sync.execute();

         }else{
             idUSer = tournee.getChauffeur().getId_chauffeur();
             tvIdChauffeur.setText(tournee.getChauffeur().getId_chauffeur());
             tvIdCamion.setText(tournee.getCamion().getNom_camion() + " " + tournee.getCamion().getId_camion());
         }*/




        // Lancement du background toute les x intervalles de temps
       // Background background = new Background(this);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // This method will be invoked when a new page becomes selected.
            @Override
            public void onPageSelected(int position) {
                Log.d("pageSelect", "page select : "+position);
                switch (position){
                    case 0 : ((FragmentNavigation)adapterViewPager.getItem(position)).setVisible(true);
                    default: ((FragmentNavigation)adapterViewPager.getItem(0)).setVisible(false);
                }
            }

            // This method will be invoked when the current page is scrolled
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Code goes here
            }

            // Called when the scroll state changes:
            // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
            @Override
            public void onPageScrollStateChanged(int state) {
                // Code goes here
            }
        });
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
        // Handle fragment_navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_gallery) {
            Log.d("resultat", "sync.synchronizeToServer ");


            Colis colis0 = new Colis(3,"Mac",1782,0.25f,47,22,80,null,null,null,null,null);
            Latlng latlng = new Latlng(1,47.481991f, 6.356643f);
            PositionColis positionColis = new PositionColis(1,Calendar.getInstance().getTime(),colis0,latlng);

            PositionColisManager positionColisManager = new PositionColisManager(this);
            positionColisManager.open();
            positionColisManager.addPositionColis(positionColis);
            positionColisManager.close();

            Communication sync=new Communication(this,idUSer);
            sync.synchronizeToServer();
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_slideshow) {
            //deleteDatabase("db.sqlite");

            SynchronizeFromServer sync = new SynchronizeFromServer();
            sync.execute();

            viewPager.setCurrentItem(2);

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

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return FragmentNavigation.newInstance(0, "NAVIGATION");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return FragmentColis.newInstance(1, "COLIS");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return FragmentFeuilleRoute.newInstance(2, "ROADMAP");
                default:
                    return null;
            }
        }



        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NAVIGATION";
                case 1:
                    return "COLIS";
                case 2:
                    return "ROADMAP";
                default:
                    return null;
            }
        }

    }

    public class SynchronizeFromServer extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog = new ProgressDialog(MainActivity.this);

        public SynchronizeFromServer() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("chargement des données ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void[] params) {
            Communication sync=new Communication(MainActivity.this,idUSer);
            sync.synchronizeFromServerSynchrone();
            return null;
        }

        @Override
        protected void onPostExecute(Void message) {
            super.onPostExecute(message);
            TourneeManager tourneeManager = new TourneeManager(MainActivity.this);
            tourneeManager.open();
            tournee=tourneeManager.getTournee();
            tourneeManager.close();
            Log.d("retour", " retour requete : "+tournee.toString());
            idUSer = tournee.getChauffeur().getId_chauffeur();
            tvIdChauffeur.setText(tournee.getChauffeur().getId_chauffeur());
            tvIdCamion.setText(tournee.getCamion().getNom_camion() + " " + tournee.getCamion().getId_camion());

            dialog.dismiss();
        }
    }



    }
