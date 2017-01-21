package com.ufrstgi.imr.application;

import android.*;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrstgi.imr.application.Fragment.FragmentColis;
import com.ufrstgi.imr.application.Fragment.FragmentFeuilleRoute;
import com.ufrstgi.imr.application.Fragment.FragmentNavigation;
import com.ufrstgi.imr.application.activity.Background;
import com.ufrstgi.imr.application.activity.BackgroundTasks;
import com.ufrstgi.imr.application.activity.ServerHTTP;
import com.ufrstgi.imr.application.activity.SettingsActivity;
import com.ufrstgi.imr.application.database.local.*;
import com.ufrstgi.imr.application.database.server.MyApiEndpointInterface;
import com.ufrstgi.imr.application.database.server.OperationSerializer;
import com.ufrstgi.imr.application.object.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ServerHTTP serverHTTP;
    private final static int PORT = 8080;
    List<Colis> mesColis;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        deleteDatabase("db.sqlite");
        initBDDTest();

        // Lancement du background toute les x intervalles de temps
        Background background = new Background(this);
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
            viewPager.setCurrentItem(1);
        } else if (id == R.id.nav_slideshow) {
            viewPager.setCurrentItem(2);

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Operation.class, new OperationSerializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                   // .baseUrl("http://10.0.2.2/API/example/")
                    .baseUrl("http://ceram.pu-pm.univ-fcomte.fr:5022/API/example/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();


            MyApiEndpointInterface apiService = retrofit.create(MyApiEndpointInterface.class);

            Call<List<Colis>> call0 = apiService.getAllColis("2");

            call0.enqueue(new Callback<List<Colis>>() {
                @Override
                public void onResponse(Call<List<Colis>> call, Response<List<Colis>> response) {
                    int statusCode = response.code();
                    mesColis = response.body();
                    Log.d("resultats", "colis recu : "+mesColis.toString());

                }

                @Override
                public void onFailure(Call<List<Colis>> call, Throwable t) {
                    Log.d("resultats", "failed onfailure "+ t.getMessage());
                }
            });

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

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentNavigation(), "Navigation");
        adapter.addFragment(new FragmentColis(), "Colis");
        adapter.addFragment(new FragmentFeuilleRoute(), "Feuille de route");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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
        Tournee tournee = new Tournee(1,chauffeur,camion);

        Client client = new Client(1,"Dufay Cyril","0621065807",adresse_client,contact_client);
        Livraison livraison = new Livraison(1, today,null,today,"9B","Nodier",adresse_operation, client);
        Livraison livraison2 = new Livraison(3, today,null,today,"9B","Nodier",adresse_client, client);
        Reception reception = new Reception(2,today,null,today,"9B","Nodier",adresse_client, client);
        Reception reception2 = new Reception(4,today,null,today,"9B","Nodier",adresse_client, client);

        Colis colis0 = new Colis(1,"Mac",1782,0.25f,47,22,80,niveau,livraison,tournee,client);
        Colis colis1 = new Colis(2,"Mac",156462,0.25f,47,22,80,niveau,reception,tournee,client);
        Colis colis2 = new Colis(3,"Mac",162,0.25f,47,22,80,niveau,livraison2,tournee,client);
        Colis colis3 = new Colis(4,"Mac",11435,0.25f,47,22,80,niveau,reception2,tournee,client);
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


}
