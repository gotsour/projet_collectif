package com.ufrstgi.imr.application.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ufrstgi.imr.application.database.server.Communication;

/**
 * Created by Duduf on 25/01/2017.
 */

public class SynchronizeFromServerTask extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog dialog;
    String login;

    int type; // 0-> create 1->update
    public SynchronizeFromServerTask(int type, String login, Context context) {
        this.login=login;
        this.context=context;
        this.type=type;
        dialog = new ProgressDialog(context);
    }

    protected void onPreExecute() {
        Log.d("loginChauffeur", "On preexc thread async task");
        super.onPreExecute();
        dialog.setMessage("chargement des données ...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    protected Void doInBackground(Void[] params) {
        Log.d("loginChauffeur", "debut thread async task do in background");
        Communication sync=new Communication(context);
        sync.synchronizeFromServerSynchrone(login,type);
        return null;
    }

    @Override
    protected void onPostExecute(Void message) {
        super.onPostExecute(message);
        Log.d("loginChauffeur", "on post thread async task");
        dialog.dismiss();
    }
}
