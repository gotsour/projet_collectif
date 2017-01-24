package com.ufrstgi.imr.application.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.activity.ColisDetails;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.listAdapter.ExpandableListAdapter;
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

    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FragmentColis newInstance(int page, String title) {
        FragmentColis fragmentColis = new FragmentColis();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentColis.setArguments(args);
        return fragmentColis;
    }


    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    public void loadData(){
        ColisManager colisManager = new ColisManager(getActivity());
        colisManager.open();
        mesColis = colisManager.getAllColis();
        colisManager.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_colis, container, false);
        loadData();
        expandableListDetail = new HashMap<String, List<String>>();
        for (int i = 0 ; i < mesColis.size() ; i++) {
            List<String> list = new ArrayList<String>();
            list.add(mesColis.get(i).getPoids_colis()+" kg");
            list.add(mesColis.get(i).getAdresse_mac());
            //livraison
            list.add("livraison : "+mesColis.get(i).getLivraison().getAdresse().getRue() +
                    ", "+mesColis.get(i).getLivraison().getAdresse().getCode_postal() +
                    ", "+mesColis.get(i).getLivraison().getAdresse().getVille()
            );
            list.add(mesColis.get(i).getLivraison().getClient().getPersonne().getNom_personne() +
                    " "+mesColis.get(i).getLivraison().getClient().getPersonne().getPrenom_personne()
            );
            list.add(mesColis.get(i).getLivraison().getClient().getPersonne().getTelephone_personne());

            //reception
            list.add("reception : "+mesColis.get(i).getReception().getAdresse().getRue() +
                    ", "+mesColis.get(i).getReception().getAdresse().getCode_postal() +
                    ", "+mesColis.get(i).getReception().getAdresse().getVille()
            );
            list.add(mesColis.get(i).getReception().getClient().getPersonne().getNom_personne() +
                    " "+mesColis.get(i).getReception().getClient().getPersonne().getPrenom_personne()
            );
            list.add(mesColis.get(i).getReception().getClient().getPersonne().getTelephone_personne());

            expandableListDetail.put("Colis num "+mesColis.get(i).getId_colis(), list);
        }

        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());

        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                int itemType = ExpandableListView.getPackedPositionType(id);

                if(itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                    int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                    String title = (String) expandableListAdapter.getGroup(groupPosition);
                    String id_colis = title.replaceAll("[^0-9]", "");

                    Intent intent = new Intent(getActivity(), ColisDetails.class );
                    intent.putExtra("id_colis", id_colis);
                    startActivityForResult(intent, 0);

                    return true; //true if we consumed the click, false if not
                } else {
                    // null item; we don't consume the click
                    return false;
                }
            }
        });

        // Inflate the layout for this fragment
        return v;
    }

}