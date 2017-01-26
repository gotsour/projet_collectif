package com.ufrstgi.imr.application.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.ufrstgi.imr.application.MainActivity;

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
    Timer timer;
    Timer timer2;
    boolean aClique;
    String login;

    public Background(Context context, String login) {
        this.context = context;
        this.login=login;
        setRepeatingAsyncTask();
    }

    public void setRepeatingAsyncTask() {
        final Handler handler = new Handler();

        //check colis
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            BackgroundTasks routine = new BackgroundTasks(context);
                            String result = routine.execute().get();
                            Log.d("Test", result);
                            showDialog(result);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        timer.schedule(task, 0, 30*1000);

        /// reload bdd
        timer2 = new Timer();
        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        SynchronizeFromServerTask sync = new SynchronizeFromServerTask(1,login,context);
                        sync.execute();

                    }
                });
            }
        };
        timer.schedule(task2, 0, 60*1000);
    }

    private void showDialog(String result) {
        if (result.contains("Missing") || result.contains("Too Much")) {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText(result + " Colis!")
                    .setContentText("Number of Colis does not match roadmap !")
                    .setConfirmText("I will see to it")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        } else if (result == "AP") {
            new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Access Point!")
                    .setContentText("Please turn your Access Point!")
                    .setConfirmText("I will see to it")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }
    }

}
