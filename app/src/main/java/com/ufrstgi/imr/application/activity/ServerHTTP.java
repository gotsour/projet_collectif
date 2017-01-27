package com.ufrstgi.imr.application.activity;

import android.content.Context;
import android.util.Log;

import com.ufrstgi.imr.application.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


/**
 * Created by Thomas Westermann on 06/12/2016.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ServerHTTP extends NanoHTTPD {

    private Context context;
    public String temperature;
    public String niveauBatterie;
    public String latitude;
    public String longitude;

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
            String line;
            while ((line = reader.readLine()) != null) {
                answer += line;
            }
            reader.close();

        } catch(IOException ioe) {
            Log.w("Httpd", ioe.toString());
        }


        Map<String, String> files = new HashMap<>();
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
        handleRequest(postBody);

        return new NanoHTTPD.Response(answer);
    }


    /**
     * Traite les requêtes en provenance de l'ESP8266
     * @param postBody URL que l'on souhaite traiter
     */
    private void handleRequest(String postBody) {
        try {
            Float temp = Float.parseFloat(getRequest("temperature", postBody));
            if (temp > 40) {
                temperature = String.valueOf(temp);
            } else {
                temperature = null;
            }
            niveauBatterie = getRequest("niveauBatterie", postBody);
            latitude = getRequest("latitude", postBody);
            longitude = getRequest("longitude", postBody);
        } catch (UnsupportedEncodingException e) {
            Log.d("ERROR", "L'URL a mal été décodée");
            e.printStackTrace();
        }
    }

    private static Map<String, List<String>> splitQuery(String url) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = new LinkedHashMap<>();
        final String[] pairs = url.split("&");
        for (String pair : pairs) {
            final int idx = pair.indexOf("=");
            final String key = idx > 0 ? URLDecoder.decode(pair.substring(0, idx), "UTF-8") : pair;
            if (!query_pairs.containsKey(key)) {
                query_pairs.put(key, new LinkedList<String>());
            }
            final String value = idx > 0 && pair.length() > idx + 1 ? URLDecoder.decode(pair.substring(idx + 1), "UTF-8") : null;
            query_pairs.get(key).add(value);
        }
        return query_pairs;
    }

    /**
     * Retourne une String suivant l'URL et le paramètre demandé
     * @param argument Argument que l'on sohaite décoder dans URL
     * @param postBody URL que l'on souhaite décoder
     * @return String correspondant à l'argument souhaité dans l'URL
     * @throws UnsupportedEncodingException
     */
    public String getRequest(String argument, String postBody) throws UnsupportedEncodingException {
        final Map<String, List<String>> query_pairs = splitQuery(postBody);
        String decoded = query_pairs.get(argument).toString().replaceAll("\\[", "").replaceAll("\\]","");
        Log.d(argument, decoded);
        return decoded;
    }

    public String getTemperature() {
        return temperature;
    }
}
