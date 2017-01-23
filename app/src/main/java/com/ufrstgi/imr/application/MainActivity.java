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
            Log.d("resultat", "reload bdd ");
            deleteDatabase("db.sqlite");

            /*Communication sync=new Communication(this,idUSer);
            sync.synchronizeFromServer();*/

            SynchronizeFromServer sync = new SynchronizeFromServer(this);
            sync.execute();

            //sync.synchronizeFromServerSynchrone();
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




    public void initBDDTest() {
        /* Test BDD */
        Niveau niveau = new Niveau(1,"Niveau 1",150);
        Personne conducteur_camion = new Personne(1,"Westermann","Thomas","0621065701");
        Personne contact_client = new Personne(2,"Spies","François","0654865701");
        Camion camion = new Camion("BK-589-KD","Peugeot 308",25,2,1500);
        Latlng latlng = new Latlng(1,47.481991f, 6.356643f);
        Latlng latlng2 = new Latlng(2,47.483514f, 6.922867f);
        Chauffeur chauffeur = new Chauffeur("TW272D8N","password",52,conducteur_camion);
        Date today = Calendar.getInstance().getTime();
        Horaire horaire = new Horaire(1,today,today,chauffeur);
        Adresse adresse_client = new Adresse(1,"6 rue Maurice Ravel",70400,"Hericourt","France",latlng);
        Adresse adresse_operation = new Adresse(2,"9 rue des Alouettes",90782,"Belfort","France",latlng2);
        PositionChauffeur positionChauffeur = new PositionChauffeur(0,today,chauffeur,latlng);
        Tournee tournee = new Tournee(1,today,chauffeur,camion);

        Client client = new Client(1,"Dufay Cyril","0621065807",adresse_client,contact_client);
        Livraison livraison = new Livraison(1, today,null,today,"9B","Nodier",adresse_operation, client);
        Livraison livraison2 = new Livraison(3, today,null,today,"9B","Nodier",adresse_client, client);
        Reception reception = new Reception(2,today,null,today,"9B","Nodier",adresse_client, client);
        Reception reception2 = new Reception(4,today,null,today,"9B","Nodier",adresse_client, client);

        Colis colis0 = new Colis(1,"Mac",1782,0.25f,47,22,80,niveau,livraison,reception,tournee,client);
        Colis colis1 = new Colis(2,"Mac",156462,0.25f,47,22,80,niveau,livraison2,reception,tournee,client);
        Colis colis2 = new Colis(3,"Mac",162,0.25f,47,22,80,niveau,livraison2,reception2,tournee,client);
        Colis colis3 = new Colis(4,"Mac",11435,0.25f,47,22,80,niveau,livraison,reception2,tournee,client);
        PositionColis positionColis = new PositionColis(1,today,colis0,latlng);

        NiveauManager niveauManager = new NiveauManager(this);
        niveauManager.open();
        niveauManager.addNiveau(niveau);
        niveauManager.close();

        PersonneManager personneManager = new PersonneManager(this);
        personneManager.open();
        personneManager.addPersonne(conducteur_camion);
        personneManager.addPersonne(contact_client);
        personneManager.close();

        CamionManager camionManager = new CamionManager(this);
        camionManager.open();
        camionManager.addCamion(camion);
        camionManager.close();

        LatlngManager latlngManager = new LatlngManager(this);
        latlngManager.open();
        latlngManager.addLatlng(latlng);
        latlngManager.close();

        ChauffeurManager chauffeurManager = new ChauffeurManager(this);
        chauffeurManager.open();
        chauffeurManager.addChauffeur(chauffeur);
        chauffeurManager.close();

        HoraireManager horaireManager = new HoraireManager(this);
        horaireManager.open();
        horaireManager.addHoraire(horaire);
        horaireManager.close();

        AdresseManager adresseManager = new AdresseManager(this);
        adresseManager.open();
        adresseManager.addAdresse(adresse_client);
        adresseManager.addAdresse(adresse_operation);
        adresseManager.close();

        PositionChauffeurManager positionChauffeurManager = new PositionChauffeurManager(this);
        positionChauffeurManager.open();
        positionChauffeurManager.addPositionChauffeur(positionChauffeur);
        positionChauffeurManager.close();

        TourneeManager tourneeManager = new TourneeManager(this);
        tourneeManager.open();
        tourneeManager.addTournee(tournee);
        tourneeManager.close();

        ClientManager clientManager = new ClientManager(this);
        clientManager.open();
        clientManager.addClient(client);
        clientManager.close();

        OperationManager operationManager = new OperationManager(this);
        operationManager.open();
        operationManager.addOperation(livraison);
        operationManager.addOperation(reception);
        operationManager.addOperation(reception2);
        operationManager.addOperation(livraison2);
        operationManager.close();

        ColisManager colisManager = new ColisManager(this);
        colisManager.open();
        colisManager.addColis(colis0);
        colisManager.addColis(colis1);
        colisManager.addColis(colis2);
        colisManager.addColis(colis3);
        colisManager.close();

        PositionColisManager positionColisManager = new PositionColisManager(this);
        positionColisManager.open();
        positionColisManager.addPositionColis(positionColis);
        positionColisManager.close();
    }

    public class SynchronizeFromServer extends AsyncTask<Void, Void, Void> {

        Context context;

        public SynchronizeFromServer(Context context) {
            this.context = context;
        }

        //private final ProgressDialog dialog = new ProgressDialog(context);

        protected void onPreExecute() {
            super.onPreExecute();
          /*  dialog.setMessage("chargement des données ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);*/
        }

        @Override
        protected Void doInBackground(Void[] params) {
            Communication sync=new Communication(context,idUSer);
            sync.synchronizeFromServerSynchrone();
            return null;
        }

        @Override
        protected void onPostExecute(Void message) {
            super.onPostExecute(message);
            TourneeManager tourneeManager = new TourneeManager(context);
            tourneeManager.open();
            tournee=tourneeManager.getTournee();
            tourneeManager.close();
            Log.d("retour", " retour requete : "+tournee.toString());
            idUSer = tournee.getChauffeur().getId_chauffeur();
            tvIdChauffeur.setText(tournee.getChauffeur().getId_chauffeur());
            tvIdCamion.setText(tournee.getCamion().getNom_camion() + " " + tournee.getCamion().getId_camion());

           // this.dialog.dismiss();
        }
    }



    }
