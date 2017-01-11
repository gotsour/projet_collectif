package com.ufrstgi.imr.application;

import android.content.Intent;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrstgi.imr.application.Fragment.FragmentColis;
import com.ufrstgi.imr.application.Fragment.FragmentFeuilleRoute;
import com.ufrstgi.imr.application.Fragment.FragmentNavigation;
import com.ufrstgi.imr.application.database.local.AdresseManager;
import com.ufrstgi.imr.application.database.local.CamionManager;
import com.ufrstgi.imr.application.database.local.ChauffeurManager;
import com.ufrstgi.imr.application.database.local.ClientManager;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.database.local.HoraireManager;
import com.ufrstgi.imr.application.database.local.LatlngManager;
import com.ufrstgi.imr.application.database.local.NiveauManager;
import com.ufrstgi.imr.application.database.local.OperationManager;
import com.ufrstgi.imr.application.database.local.PersonneManager;
import com.ufrstgi.imr.application.database.local.PositionChauffeurManager;
import com.ufrstgi.imr.application.database.local.PositionColisManager;
import com.ufrstgi.imr.application.database.local.TourneeManager;
import com.ufrstgi.imr.application.database.server.MyApiEndpointInterface;
import com.ufrstgi.imr.application.database.server.OperationSerializer;
import com.ufrstgi.imr.application.object.Adresse;
import com.ufrstgi.imr.application.object.Camion;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Client;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Horaire;
import com.ufrstgi.imr.application.object.Latlng;
import com.ufrstgi.imr.application.object.Livraison;
import com.ufrstgi.imr.application.object.Niveau;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Personne;
import com.ufrstgi.imr.application.object.PositionChauffeur;
import com.ufrstgi.imr.application.object.PositionColis;
import com.ufrstgi.imr.application.object.Tournee;

import java.util.ArrayList;
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i = new Intent(this, ColisActivity.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Operation.class, new OperationSerializer())
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2/API/example/")
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
        adapter.addFragment(new FragmentFeuilleRoute(), "Fuille de route");
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


}
