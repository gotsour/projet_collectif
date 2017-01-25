package com.ufrstgi.imr.application.database.server;


import com.ufrstgi.imr.application.object.ArrayPosition;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Exist;
import com.ufrstgi.imr.application.object.Niveau;
import com.ufrstgi.imr.application.object.PositionColis;
import com.ufrstgi.imr.application.object.Tournee;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Duduf on 12/12/2016.
 */
public interface MyApiEndpointInterface {


    //recupere le tournee du chauffeur
    @GET("tournee/{idChauffeur}")
    Call<Tournee> getTournee(@Path("idChauffeur") String idChauffeur);

    //verifie que l'user exist en bdd
    @GET("userExist/{loginChauffeur}")
    Call<Exist> chauffeurExist(@Path("loginChauffeur") String loginChauffeur);

    //récuperation du chauffeur avec le login
    @GET("user/{loginChauffeur}")
    Call<Chauffeur> getChauffeur(@Path("loginChauffeur") String loginChauffeur);

    //recupere tous les colis de la tournee idcolis
    @GET("allColis/{colis}")
    Call<List<Colis>> getAllColis(@Path("colis") String idTournee);



    @POST("positionColis")
    Call<PositionColis> createPositionColis(@Body PositionColis position);
}
