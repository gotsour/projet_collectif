package com.ufrstgi.imr.application;

import android.app.Activity;
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
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ufrstgi.imr.application.Fragment.FragmentColis;
import com.ufrstgi.imr.application.Fragment.FragmentFeuilleRoute;
import com.ufrstgi.imr.application.Fragment.FragmentNavigation;
import com.ufrstgi.imr.application.activity.Background;
import com.ufrstgi.imr.application.activity.LoginActivity;
import com.ufrstgi.imr.application.activity.ServerHTTP;
import com.ufrstgi.imr.application.activity.SettingsActivity;
import com.ufrstgi.imr.application.activity.Updateable;
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
    private TabLayout tabLayout;
    private ViewPager viewPager;
    String idUSer;
    TextView tvIdChauffeur;
    TextView tvIdCamion;
    Tournee tournee;
    ProgressDialog dialog;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
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
        tvIdChauffeur = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvIdChauffeur);
        tvIdCamion = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvIdCamion);
        dialog = new ProgressDialog(MainActivity.this);

        TourneeManager tourneeManager = new TourneeManager(this);
        tourneeManager.open();
        tournee=tourneeManager.getTournee();
        tourneeManager.close();
        idUSer = tournee.getChauffeur().getId_chauffeur();

        //update bdd
       /* SynchronizeFromServer sync = new SynchronizeFromServer(1);
        sync.execute();*/

        tvIdChauffeur.setText(tournee.getChauffeur().getId_chauffeur());
        tvIdCamion.setText(tournee.getCamion().getNom_camion() + " " + tournee.getCamion().getId_camion());

        serverHTTP = new ServerHTTP(PORT, getApplicationContext());
        // Lancement du background toute les x intervalles de temps
        Background background = new Background(this,idUSer,serverHTTP);

       // use own OnPageChangeListener
        viewPager.addOnPageChangeListener(new MyPageScrollListener(tabLayout));

        // use own OnTabSelectedListener
        tabLayout.setOnTabSelectedListener(new MyOnTabSelectedListener());

    }

    private class MyPageScrollListener implements ViewPager.OnPageChangeListener {
        private TabLayout mTabLayout;

        public MyPageScrollListener(TabLayout tabLayout) {
            this.mTabLayout = tabLayout;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if(mTabLayout != null) {
                mTabLayout.getTabAt(position).select();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyOnTabSelectedListener implements TabLayout.OnTabSelectedListener {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            int position = tab.getPosition();
            if (viewPager.getCurrentItem() != position) {
                viewPager.setCurrentItem(position, true);
                Updateable fragment = (Updateable)adapter.getItem(position);
                if (fragment != null) {
                    fragment.update();
                }

            }
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentNavigation(), "Navigation");
        adapter.addFragment(new FragmentColis(), "Colis");
        adapter.addFragment(new FragmentFeuilleRoute(), "roadMap");
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
    }

    @Override
    public void onPause() {
        super.onPause();
        dialog.dismiss();
    }


    public class SynchronizeFromServer extends AsyncTask<Void, Void, Void> {

        int type; // 0-> create 1->update
        public SynchronizeFromServer(int type) {
            this.type=type;
            Log.d("loginChauffeur", "lancement thread sync from server type :"+type + "utilisateur : "+idUSer);

        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("chargement des données ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void[] params) {
            Communication sync=new Communication(MainActivity.this);
            sync.synchronizeFromServerSynchrone(idUSer,type);
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
