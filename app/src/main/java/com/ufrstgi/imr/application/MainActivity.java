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
        Operation operation = new Operation(1,"14","15","19",1,"9B","Nodier",adresse_operation, client);
        Colis colis = new Colis(1,12,0.25f,47,22,80,niveau,operation,tournee,client);
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
        operationManager.addOperation(operation);
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


        String str_colis = "colis {\n"
                + "\tid_colis : "+co.getId_colis()+"\n"
                + "\tpoids_colis : "+co.getPoids_colis()+"\n"
                + "\tvolume_colis : "+co.getVolume_colis()+"\n"
                + "\tniveau_batterie_colis : "+co.getNiveau_batterie_colis()+"\n"
                + "\ttemperature_colis : "+co.getTemperature_colis()+"\n"
                + "\tcapacite_chox : "+co.getCapacite_choc_colis()+"\n"
                + "\tniveau {\n"
                + "\t\tid_niveau : "+co.getNiveau().getId_niveau()+"\n"
                + "\t\tlibelle_niveau : "+co.getNiveau().getLibelle_niveau()+"\n"
                + "\t\tprix_niveau : "+co.getNiveau().getPrix()+"\n"
                + "\t}\n"
                + "\toperation {\n"
                + "\t\tid_operation : "+co.getOperation().getId_operation()+"\n"
                + "\t\tdate_theorique : "+co.getOperation().getDate_theorique()+"\n"
                + "\t\tdate_reelle : "+co.getOperation().getDate_reelle()+"\n"
                + "\t\tdate_limite : "+co.getOperation().getDate_limite()+"\n"
                + "\t\test_livraison : "+co.getOperation().getEstLivraison()+"\n"
                + "\t\tquai : "+co.getOperation().getQuai()+"\n"
                + "\t\tbatiment : "+co.getOperation().getBatiment()+"\n"
                + "\t\tadresse {\n"
                + "\t\t\tid_adresse : "+co.getOperation().getAdresse().getId_adresse()+"\n"
                + "\t\t\true : "+co.getOperation().getAdresse().getRue()+"\n"
                + "\t\t\tcode_postal : "+co.getOperation().getAdresse().getCode_postal()+"\n"
                + "\t\t\tville : "+co.getOperation().getAdresse().getVille()+"\n"
                + "\t\t\tpays : "+co.getOperation().getAdresse().getPays()+"\n"
                + "\t\t\tlatlng {\n"
                + "\t\t\t\tid_latlng : "+co.getOperation().getAdresse().getLatlng().getId_latlng()+"\n"
                + "\t\t\t\tlatitude : "+co.getOperation().getAdresse().getLatlng().getLatitude()+"\n"
                + "\t\t\t\tlongitude : "+co.getOperation().getAdresse().getLatlng().getLongitude()+"\n"
                + "\t\t\t}\n"
                + "\t\t}\n"
                +"\t}\n"
                + "}"
        ;
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
