package com.ufrstgi.imr.application.database.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ufrstgi.imr.application.object.Operation;

import org.apache.http.util.TextUtils;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Duduf on 21/01/2017.
 */

public class ServiceGenerator {

    public static MyApiEndpointInterface init(){
        final String authToken = Credentials.basic("*****", "******");

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Operation.class, new OperationSerializer())
                .create();

        // Define the interceptor, add authentication headers
        Interceptor interceptor = new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder().addHeader("Authorization", authToken).build();
                return chain.proceed(newRequest);
            }
        };

        // Add the interceptor to OkHttpClient
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.interceptors().add(interceptor);
        OkHttpClient client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                // .baseUrl("http://10.0.2.2/API/example/")
                .baseUrl(
                        "http://ceram.pu-pm.univ-fcomte.fr:5022/API/example/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(MyApiEndpointInterface.class);
    }
}
