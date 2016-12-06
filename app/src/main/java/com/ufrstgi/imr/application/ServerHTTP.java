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


    @Override
    public Response serve(String uri, Method method, Map<String, String> headers, Map<String, String> parms, Map<String, String> files) {
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


        return new NanoHTTPD.Response(answer);
    }
}
