package com.ufrstgi.imr.application.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.activity.Updateable;
import com.ufrstgi.imr.application.database.local.OperationManager;
import com.ufrstgi.imr.application.listAdapter.ExpandableListAdapter;
import com.ufrstgi.imr.application.object.Livraison;
import com.ufrstgi.imr.application.object.Operation;
import com.ufrstgi.imr.application.object.Reception;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Duduf on 11/01/2017.
 */

public class FragmentFeuilleRoute extends Fragment implements Updateable {

    ExpandableListView expandableListView;
    android.widget.ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    ArrayList<Operation> mesOperations;


    private String title;
    private int page;

    public FragmentFeuilleRoute () {

    }


    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_feuille_route, container, false);
        expandableListView = (ExpandableListView) v.findViewById(R.id.expandableListView);
        loadData();

        return v;
    }

    public void loadData(){
        Log.d("logMessage", "loaddata launch feuille de route");
        OperationManager operationManager = new OperationManager(getActivity());
        operationManager.open();
        mesOperations = operationManager.getAllOperation();
        operationManager.close();

        SimpleDateFormat formater = formater = new SimpleDateFormat("dd/MM/yy à H:m:s");
        String dateReellle="";
        expandableListDetail = new HashMap<String, List<String>>();
        for (int i = 0 ; i < mesOperations.size() ; i++) {
            List<String> list = new ArrayList<String>();
            list.add("Date théorique : "+formater.format(mesOperations.get(i).getDate_theorique()));

            //verification date
            if(mesOperations.get(i).getDate_reelle()!=null)dateReellle=formater.format(mesOperations.get(i).getDate_reelle());
            else dateReellle="";

            list.add("Date réelle : " + dateReellle);
            list.add("Date limite : "+formater.format(mesOperations.get(i).getDate_limite()));

            list.add("Batiment : "+mesOperations.get(i).getBatiment());
            list.add("Quai : "+mesOperations.get(i).getQuai());

            list.add("Adresse : "+mesOperations.get(i).getAdresse().getRue() +
                    ", "+mesOperations.get(i).getAdresse().getCode_postal() +
                    ", "+mesOperations.get(i).getAdresse().getVille()
            );
            list.add("Client : "+mesOperations.get(i).getClient().getPersonne().getNom_personne() +
                    " "+mesOperations.get(i).getClient().getPersonne().getPrenom_personne() +
                    ", Tél : "+mesOperations.get(i).getClient().getPersonne().getTelephone_personne()
            );

            if (mesOperations.get(i) instanceof Livraison) {
                expandableListDetail.put("Livraison : étape num "+mesOperations.get(i).getId_operation(), list);
            } else if (mesOperations.get(i) instanceof Reception) {
                expandableListDetail.put("Réception : étape num "+mesOperations.get(i).getId_operation(), list);
            }
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    @Override
    public void update() {
        Log.d("logMessage", "loaddata launch feuille de route");
        OperationManager operationManager = new OperationManager(getActivity());
        operationManager.open();
        mesOperations = operationManager.getAllOperation();
        operationManager.close();

        SimpleDateFormat formater = formater = new SimpleDateFormat("dd/MM/yy à H:m:s");
        String dateReellle="";
        expandableListDetail = new HashMap<String, List<String>>();
        for (int i = 0 ; i < mesOperations.size() ; i++) {
            List<String> list = new ArrayList<String>();
            list.add("Date théorique : "+formater.format(mesOperations.get(i).getDate_theorique()));

            //verification date
            if(mesOperations.get(i).getDate_reelle()!=null)dateReellle=formater.format(mesOperations.get(i).getDate_reelle());
            else dateReellle="";

            list.add("Date réelle : " + dateReellle);
            list.add("Date limite : "+formater.format(mesOperations.get(i).getDate_limite()));

            list.add("Batiment : "+mesOperations.get(i).getBatiment());
            list.add("Quai : "+mesOperations.get(i).getQuai());

            list.add("Adresse : "+mesOperations.get(i).getAdresse().getRue() +
                    ", "+mesOperations.get(i).getAdresse().getCode_postal() +
                    ", "+mesOperations.get(i).getAdresse().getVille()
            );
            list.add("Client : "+mesOperations.get(i).getClient().getPersonne().getNom_personne() +
                    " "+mesOperations.get(i).getClient().getPersonne().getPrenom_personne() +
                    ", Tél : "+mesOperations.get(i).getClient().getPersonne().getTelephone_personne()
            );

            if (mesOperations.get(i) instanceof Livraison) {
                expandableListDetail.put("Livraison : étape num "+mesOperations.get(i).getId_operation(), list);
            } else if (mesOperations.get(i) instanceof Reception) {
                expandableListDetail.put("Réception : étape num "+mesOperations.get(i).getId_operation(), list);
            }
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }
}