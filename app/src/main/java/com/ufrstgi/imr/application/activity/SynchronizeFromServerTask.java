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
        super.onPreExecute();
        dialog.setMessage("Loading data from server ...");
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);

    }

    @Override
    protected Void doInBackground(Void[] params) {
        Communication sync=new Communication(context);
        sync.synchronizeFromServerSynchrone(login,type);
        return null;
    }

    @Override
    protected void onPostExecute(Void message) {
        super.onPostExecute(message);
        dialog.dismiss();
    }
}
