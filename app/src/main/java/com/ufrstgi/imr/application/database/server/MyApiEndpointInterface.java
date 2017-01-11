package com.ufrstgi.imr.application.database.server;


import com.ufrstgi.imr.application.object.Colis;
import com.ufrstgi.imr.application.object.Niveau;

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

    //niveaux
    @GET("niveau/{niveau}")
    Call<Niveau> getNiveau(@Path("niveau") int idNiveau);

    @GET("niveaux")
    Call<List<Niveau>> getAllNiveaux();

    //colis

    @GET("colis/{colis}")
    Call<Colis> getColis(@Path("colis") String idColis);

    @GET("allColis/{colis}")
    Call<List<Colis>> getAllColis(@Path("colis") String idColis);

    /*@GET("niveaux")
    Call<List<Niveau>> getAllNiveaux(@Path("niveau") String nomNiveau);*/

    /*@GET("group/{id}/users")
    Call<List<Niveau>> groupList(@Path("id") int groupId, @Query("sort") String sort);*/

    @POST("niveau")
    Call<Niveau> createUser(@Body Niveau niveau);
}
