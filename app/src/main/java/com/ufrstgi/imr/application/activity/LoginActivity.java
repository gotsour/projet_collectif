package com.ufrstgi.imr.application.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrstgi.imr.application.MainActivity;
import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.database.local.TourneeManager;
import com.ufrstgi.imr.application.database.server.Communication;
import com.ufrstgi.imr.application.object.Chauffeur;

/**
 * Created by Duduf on 23/01/2017.
 */

public class LoginActivity extends Activity{
    Button btLogin;
    Button btCancel;
    EditText etLogin;
    EditText etPassword;
    ProgressDialog dialog;
    String login;
    Chauffeur chauffeur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btLogin = (Button)findViewById(R.id.btLogin);
        btCancel = (Button)findViewById(R.id.btCancel);
        etLogin = (EditText)findViewById(R.id.etLogin);
        etPassword = (EditText)findViewById(R.id.etPassword);
        dialog = new ProgressDialog(LoginActivity.this);

        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                login=etLogin.getText().toString();

                //verification mot de passe existe dans bdd sur serveur
               /* Log.d("loginChauffeur", "login envoyé : "+login);
                VerificationServer verif= new VerificationServer();
                verif.execute();
                Log.d("loginChauffeur", "chauffeur recu : "+chauffeur.toString());
                if(chauffeur.getMot_de_passe()== etPassword.getText().toString()){*/
                Log.d("loginChauffeur", "user put extra : "+etLogin.getText().toString());
                returnIntent.putExtra("user",etLogin.getText().toString());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                //}
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

    public class VerificationServer extends AsyncTask<Void, Void, Void> {

        public VerificationServer() {
        }

        protected void onPreExecute() {

            super.onPreExecute();
            dialog.setMessage("chargement des données ...");
            dialog.show();
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected Void doInBackground(Void[] params) {
            Communication sync=new Communication(LoginActivity.this);
            Log.d("loginChauffeur","lancement thread ");
            chauffeur =sync.getChauffeurFromLogin(login);
            return null;
        }

        @Override
        protected void onPostExecute(Void message) {
            super.onPostExecute(message);
            dialog.dismiss();
        }
    }

}
