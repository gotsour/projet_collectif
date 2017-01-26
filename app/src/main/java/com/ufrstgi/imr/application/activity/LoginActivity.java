package com.ufrstgi.imr.application.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrstgi.imr.application.MainActivity;
import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.database.local.TourneeManager;
import com.ufrstgi.imr.application.database.server.Communication;
import com.ufrstgi.imr.application.object.Chauffeur;
import com.ufrstgi.imr.application.object.Exist;
import com.ufrstgi.imr.application.object.Tournee;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Duduf on 23/01/2017.
 */

public class LoginActivity extends Activity implements AsyncResponse{
    Button btLogin;
    Button btCancel;
    EditText etLogin;
    EditText etPassword;
    ProgressDialog dialog;
    String login;
    public AsyncResponse delegate=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        delegate = this;
        btLogin = (Button)findViewById(R.id.btLogin);
        btCancel = (Button)findViewById(R.id.btCancel);
        etLogin = (EditText)findViewById(R.id.etLogin);
        etPassword = (EditText)findViewById(R.id.etPassword);
        dialog = new ProgressDialog(LoginActivity.this);


        Log.d("loginChauffeur", "oncreate : chargement tournée");
        //verification tournee
        TourneeManager tourneeManager = new TourneeManager(this);
        tourneeManager.open();
        Tournee tournee=tourneeManager.getTournee();
        tourneeManager.close();

        if(tournee!=null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            finish();
            startActivity(i);

        }


        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("loginChauffeur", "Clique bouton verification serveur login : " +login);
                login=etLogin.getText().toString();
                VerificationServer verif= new VerificationServer();
                verif.execute();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }

    @Override
    public void processFinish(Chauffeur output) {
        Intent returnIntent = new Intent();

        if(output!=null) {
            if (output.getMot_de_passe().equals(etPassword.getText().toString())) {
                //insertion des données
                Log.d("loginChauffeur", "lancement insertion depuis serveur ");
                SynchronizeFromServer sync = new SynchronizeFromServer(0);
                sync.execute();
                Log.d("loginChauffeur", "fin telechargement données du serveur ");

            } else {
                new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Wrong password")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
        else{
            new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Error")
                    .setContentText("Unknown login")
                    .setConfirmText("Ok")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }

    @Override
    public void processFinish() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        finish();
        startActivity(i);
    }

    public class VerificationServer extends AsyncTask<Void, Void, Chauffeur> {
        Chauffeur chauffeurRes;
        public VerificationServer() {
        }

        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage("Connexion in progress ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Chauffeur doInBackground(Void[] params) {
            Communication sync=new Communication(LoginActivity.this);
            Exist exist = sync.chauffeurExist(login);

            if (exist.isExit()) chauffeurRes =sync.getChauffeurFromLogin(login);
            else chauffeurRes=null;

            return chauffeurRes;
        }

        @Override
        protected void onPostExecute(Chauffeur Res) {
            super.onPostExecute(Res);
            delegate.processFinish(Res);
            dialog.dismiss();
        }
    }

    public class SynchronizeFromServer extends AsyncTask<Void, Void, Void> {

        int type; // 0-> create 1->update
        public SynchronizeFromServer(int type) {
            this.type=type;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void[] params) {
            Communication sync=new Communication(LoginActivity.this);
            sync.synchronizeFromServerSynchrone(login,type);
            return null;
        }

        @Override
        protected void onPostExecute(Void message) {
            super.onPostExecute(message);
            delegate.processFinish();
            dialog.dismiss();
        }
    }

}
