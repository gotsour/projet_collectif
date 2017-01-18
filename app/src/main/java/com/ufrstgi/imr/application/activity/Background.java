package com.ufrstgi.imr.application.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Thomas Westermann on 17/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class Background {

    Context context;

    public Background(Context context) {
        this.context = context;
        setRepeatingAsyncTask();
    }

    public void setRepeatingAsyncTask() {
        final Handler handler = new Handler();
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            BackgroundTasks routine = new BackgroundTasks(context);
                            String result = routine.execute().get();
                            Log.d("Test", result);
                            if (result == "Missing" || result == "Too Much") {
                                new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                        .setTitleText(result+" Colis!")
                                        .setContentText("Number of Colis does not match roadmap !")
                                        .setConfirmText("I will see to it")
                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                            @Override
                                            public void onClick(SweetAlertDialog sDialog) {
                                                // On arrete de lancer le background
                                                timer.cancel();
                                                timer.purge();
                                                sDialog.dismissWithAnimation();
                                                // On attend 10 secondes avant de relancer le background
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        new Background(context);
                                                    }
                                                }, 10000);
                                            }
                                        })
                                        .show();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 10*1000);  // interval of 5 seconds
    }

}
