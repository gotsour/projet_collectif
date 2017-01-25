package com.ufrstgi.imr.application.activity;

import com.ufrstgi.imr.application.object.Chauffeur;

/**
 * Created by Duduf on 25/01/2017.
 */

public interface AsyncResponse {
    void processFinish(Chauffeur output);
}