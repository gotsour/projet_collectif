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

import com.ufrstgi.imr.application.database.AdresseManager;
import com.ufrstgi.imr.application.database.CamionManager;
import com.ufrstgi.imr.application.database.ChauffeurManager;
import com.ufrstgi.imr.application.database.ClientManager;
import com.ufrstgi.imr.application.database.ColisManager;
import com.ufrstgi.imr.application.database.HoraireManager;
import com.ufrstgi.imr.application.database.LatlngManager;
import com.ufrstgi.imr.application.database.MySQLite;
import com.ufrstgi.imr.application.database.NiveauManager;
import com.ufrstgi.imr.application.database.OperationManager;
import com.ufrstgi.imr.application.database.PersonneManager;
import com.ufrstgi.imr.application.database.PositionChauffeurManager;
import com.ufrstgi.imr.application.database.PositionColisManager;
import com.ufrstgi.imr.application.database.TourneeManager;
import com.ufrstgi.imr.application.objet.Adresse;
import com.ufrstgi.imr.application.objet.Camion;
import com.ufrstgi.imr.application.objet.Chauffeur;
import com.ufrstgi.imr.application.objet.Client;
import com.ufrstgi.imr.application.objet.Colis;
import com.ufrstgi.imr.application.objet.Horaire;
import com.ufrstgi.imr.application.objet.Latlng;
import com.ufrstgi.imr.application.objet.Livraison;
import com.ufrstgi.imr.application.objet.Niveau;
import com.ufrstgi.imr.application.objet.Operation;
import com.ufrstgi.imr.application.objet.Personne;
import com.ufrstgi.imr.application.objet.PositionChauffeur;
import com.ufrstgi.imr.application.objet.PositionColis;
import com.ufrstgi.imr.application.objet.Tournee;

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
        Niveau niveau = new Niveau(1,"Niveau 1",150);
        Personne conducteur_camion = new Personne(1,"Westermann","Thomas","0621065701");
        Personne contact_client = new Personne(2,"Spies","François","0654865701");
        Camion camion = new Camion("BK-589-KD","Peugeot 308",25,2,1500);
        Latlng latlng = new Latlng(1,40,7);
        Chauffeur chauffeur = new Chauffeur("TW272D8N","password",52,conducteur_camion);
        Horaire horaire = new Horaire(1,"8","20",chauffeur);
        Adresse adresse_client = new Adresse(1,"6 rue Maurice Ravel",70400,"Hericourt","France",latlng);
        Adresse adresse_operation = new Adresse(2,"9 rue des Alouettes",90782,"Belfort","France",latlng);
        PositionChauffeur positionChauffeur = new PositionChauffeur(0,"12",chauffeur,latlng);
        Tournee tournee = new Tournee(1,chauffeur,camion);
        Client client = new Client(1,"Dufay Cyril","0621065807",adresse_client,contact_client);
        Livraison livraison = new Livraison(1,"14","15","19",1,"9B","Nodier",adresse_operation, client);
        Colis colis = new Colis(1,12,0.25f,47,22,80,niveau,livraison,tournee,client);
        PositionColis positionColis = new PositionColis(1,"14",colis,latlng);

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
        operationManager.close();

        ColisManager colisManager = new ColisManager(this);
        colisManager.open();
        colisManager.addColis(colis);
        colisManager.close();

        PositionColisManager positionColisManager = new PositionColisManager(this);
        positionColisManager.open();
        positionColisManager.addPositionColis(positionColis);
        positionColisManager.close();

        // On recupère le colis avec id=1 créé précédemment
        Colis co;
        colisManager.open();
        co = colisManager.getColis(1);

        String str_colis = co.toString();
        Log.d("Colis", str_colis);
        colisManager.close();
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
