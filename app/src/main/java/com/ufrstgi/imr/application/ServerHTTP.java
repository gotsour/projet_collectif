package com.ufrstgi.imr.application;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by westi on 06/12/2016.
 */

public class ServerHTTP extends NanoHTTPD {

    private Context context;

    public ServerHTTP(int port, Context context) {
        super(port);
        this.context = context;
        try {
            start();
        } catch(IOException ioe) {
            Log.w("Httpd", "The server could not start.");
        }
        Log.w("Httpd", "Web server initialized.");
    }


    // Methode appelée à chaque fois qu'un client demande la page index
    @Override
    public Response serve(IHTTPSession session) {
        String answer = "";
        try {
            // Open file from SD Card
            //File root = Environment.getExternalStorageDirectory();
            //FileReader index = new FileReader(root.getAbsolutePath() + "/www/index.html");

            InputStream index = context.getResources().openRawResource(R.raw.index);
            BufferedReader reader = new BufferedReader(new InputStreamReader(index));
            String line = "";
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            reader.close();

        } catch(IOException ioe) {
            Log.w("Httpd", ioe.toString());
        }


        Map<String, String> files = new HashMap<String, String>();
        Method method = session.getMethod();
        if (Method.PUT.equals(method) || Method.POST.equals(method)) {
            try {
                session.parseBody(files);
            } catch (IOException ioe) {
                return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (ResponseException re) {
                return new Response(re.getStatus(), MIME_PLAINTEXT, re.getMessage());
            }
        }
        // get the POST body
        String postBody = session.getQueryParameterString();
        Log.d("POST", postBody);
        // or you can access the POST request's parameters
        String postParameter = session.getParms().get("parameter");


        return new NanoHTTPD.Response(answer);
    }


}
