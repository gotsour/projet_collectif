package com.ufrstgi.imr.application.database.server;

import android.content.Context;
import android.util.Log;

import com.ufrstgi.imr.application.database.local.CamionManager;
import com.ufrstgi.imr.application.database.local.ChauffeurManager;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.database.local.PositionColisManager;
import com.ufrstgi.imr.application.database.local.TourneeManager;

import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Colis;

import com.ufrstgi.imr.application.object.Exist;
import com.ufrstgi.imr.application.object.PositionColis;
import com.ufrstgi.imr.application.object.Tournee;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Duduf on 21/01/2017.
 */

public class Communication {
    Tournee tournee;
    List<Colis> mesColis;
    Context context;
    MyApiEndpointInterface apiService;
    Chauffeur chauffeur;
    Exist res;


    public Communication(final Context context){
        this.context=context;
        apiService= ServiceGenerator.init();
    }

    public void synchronizeFromServer(String idUser){
        Call<Tournee> call0 = apiService.getTournee(idUser);
        call0.enqueue(new Callback<Tournee>() {
            @Override
            public void onResponse(Call<Tournee> call, Response<Tournee> response) {
                int statusCode = response.code();
                tournee = response.body();
                TourneeManager tourneeManager= new TourneeManager(context);
                tourneeManager.open();
                tourneeManager.addAllOfTournee(tournee);
                tourneeManager.close();


                Call<List<Colis>> call1 = apiService.getAllColis(Integer.toString(tournee.getId_tournee()));

                call1.enqueue(new Callback<List<Colis>>() {
                    @Override
                    public void onResponse(Call<List<Colis>> call, Response<List<Colis>> response) {
                        int statusCode = response.code();
                        mesColis = response.body();
                        for(int i=0; i<mesColis.size();i++) {
                            Colis monColis = mesColis.get(i);

                            monColis.setTournee(tournee);
                            ColisManager colisManager = new ColisManager(context);
                            colisManager.open();
                            colisManager.updateAllOfColis(monColis);
                            colisManager.close();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Colis>> call, Throwable t) {
                    }
                });
            }

            @Override
            public void onFailure(Call<Tournee> call, Throwable t) {

            }
        });
    }

    public void synchronizeFromServerSynchrone(String idUser, int type){

        Call<Tournee> call0 = apiService.getTournee(idUser);
        try {
            tournee =call0.execute().body();
            TourneeManager tourneeManager= new TourneeManager(context);
            tourneeManager.open();
            if(type==0)tourneeManager.addAllOfTournee(tournee);
            else tourneeManager.updateAllOfTournee(tournee);
            tourneeManager.close();

            Call<List<Colis>> call1 = apiService.getAllColis(Integer.toString(tournee.getId_tournee()));
            mesColis = call1.execute().body();
            for(int i=0; i<mesColis.size();i++) {
                Colis monColis = mesColis.get(i);
                monColis.setTournee(tournee);
                ColisManager colisManager = new ColisManager(context);
                colisManager.open();
                if(type==0)colisManager.addAllOfColis(monColis);
                else colisManager.updateAllOfColis(monColis);
                colisManager.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //verifie que le chauffeur existe en bdd
    public Exist chauffeurExist(String login){
        Call<Exist> call0 = apiService.chauffeurExist(login);
        try {
            res =call0.execute().body();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return res;

    }

    public Chauffeur getChauffeurFromLogin(String login){
        Call<Chauffeur> call0 = apiService.getChauffeur(login);
         try {
             chauffeur =call0.execute().body();

            } catch (IOException e1) {
            e1.printStackTrace();
        }
        return chauffeur;

    }

    //pas utilisé
    public void synchronizeToServer(){

        PositionColisManager positionColisManager=new PositionColisManager(context);
        positionColisManager.open();
        PositionColis pos = positionColisManager.getPositionColis(1);
        positionColisManager.close();

        Call<PositionColis> call0 = apiService.createPositionColis(pos);

        call0.enqueue(new Callback<PositionColis>() {
            @Override
            public void onResponse(Call<PositionColis> call, Response<PositionColis> response) {
                int statusCode = response.code();

            }

            @Override
            public void onFailure(Call<PositionColis> call, Throwable t) {

            }
        });
    }
}
