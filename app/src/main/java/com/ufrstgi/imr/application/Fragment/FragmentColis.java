package com.ufrstgi.imr.application.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.listAdapter.ExpandableListAdapterColis;
import com.ufrstgi.imr.application.object.Colis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Duduf on 11/01/2017.
 */

public class FragmentColis extends Fragment{

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    ArrayList<Colis> mesColis;

    public FragmentColis() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_colis, container, false);

        ColisManager colisManager = new ColisManager(getActivity());
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


        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());


        expandableListAdapter = new ExpandableListAdapterColis(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        // Inflate the layout for this fragment
        return v;
    }

}