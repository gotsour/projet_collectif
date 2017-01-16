package com.ufrstgi.imr.application.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.ufrstgi.imr.application.R;
import com.ufrstgi.imr.application.database.local.ColisManager;
import com.ufrstgi.imr.application.object.Colis;

/**
 * Created by Thomas Westermann on 16/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ColisDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_colis_details);

        Intent intent = getIntent();
        String id_colis_string = intent.getStringExtra("id_colis");
        int id_colis = Integer.parseInt(id_colis_string);

        Colis colis;

        ColisManager colisManager = new ColisManager(this);
        colisManager.open();
        colis = colisManager.getColis(id_colis);
        colisManager.close();

        TextView textViewDetail = (TextView) findViewById(R.id.textview_detail);
        textViewDetail.setText(colis.toString());

    }
}
