package com.ufrstgi.imr.application.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ufrstgi.imr.application.R;

/**
 * Created by Duduf on 23/01/2017.
 */

public class LoginActivity extends Activity{
    Button btLogin;
    Button btCancel;
    EditText etLogin;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        btLogin = (Button)findViewById(R.id.btLogin);
        btCancel = (Button)findViewById(R.id.btCancel);
        etLogin = (EditText)findViewById(R.id.etLogin);
        etPassword = (EditText)findViewById(R.id.etPassword);


        btLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("user",etLogin.getText());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
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


}
