package com.ufrstgi.imr.application.object;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Duduf on 25/01/2017.
 */

public class Exist {
    @SerializedName("exist")
    boolean exit;

    public Exist(boolean exit) {
        this.exit = exit;
    }

    @Override
    public String toString() {
        return "Exist{" +
                "exit=" + exit +
                '}';
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }
}
