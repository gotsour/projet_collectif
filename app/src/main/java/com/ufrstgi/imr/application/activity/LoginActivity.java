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

        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                Log.d("loginChauffeur", "user put extra : " + etLogin.getText().toString());
                returnIntent.putExtra("user", etLogin.getText().toString());
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle("Erreur")
                        .setMessage("Mot de passe incorect ")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        }
        else{

            new AlertDialog.Builder(this)
                    .setTitle("Erreur")
                    .setMessage("Login inconnu")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    public class VerificationServer extends AsyncTask<Void, Void, Chauffeur> {
        Chauffeur chauffeurRes;
        public VerificationServer() {
        }

        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage("Connexion en cours");
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

}
