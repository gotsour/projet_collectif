package com.ufrstgi.imr.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.listAdapter.ExpandableListAdapterColis;
import com.ufrstgi.imr.application.object.Colis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Thomas Westermann on 11/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ColisActivity extends AppCompatActivity {

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    ArrayList<Colis> mesColis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colis_main);

        ColisManager colisManager = new ColisManager(this);
        colisManager.open();
        mesColis = colisManager.getAllColis();
        colisManager.close();

        Log.d("Test", mesColis.toString());


        expandableListDetail = new HashMap<String, List<String>>();
        for (int i = 0 ; i < mesColis.size() ; i++) {
            List<String> list = new ArrayList<String>();
            list.add(mesColis.get(i).getPoids_colis()+" kg");
            list.add(mesColis.get(i).getOperation().getAdresse().getRue() +
                    ", "+mesColis.get(i).getOperation().getAdresse().getCode_postal() +
                    ", "+mesColis.get(i).getOperation().getAdresse().getVille()
            );
            list.add(mesColis.get(i).getOperation().getClient().getPersonne().getNom_personne() +
                    " "+mesColis.get(i).getOperation().getClient().getPersonne().getPrenom_personne()
            );
            list.add(mesColis.get(i).getOperation().getClient().getPersonne().getTelephone_personne());

            expandableListDetail.put("Colis num "+mesColis.get(i).getId_colis(), list);
        }

        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new ExpandableListAdapterColis(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        /*expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });

        */

    }
}
