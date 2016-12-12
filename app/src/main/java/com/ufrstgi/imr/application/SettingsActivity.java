package com.ufrstgi.imr.application;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Thomas Westermann on 12/12/2016.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class SettingsActivity extends PreferenceActivity {

    static WifiManager wifiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        wifiManager = (WifiManager)getBaseContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static class MyPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        public static final String KEY_PREF_EXERCISES = "switch_AP";

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            //IT NEVER GETS IN HERE!
            if (key.equals(KEY_PREF_EXERCISES))
            {
                PreferenceManager preferenceManager = getPreferenceManager();
                if (preferenceManager.getSharedPreferences().getBoolean(key, true)){
                    // Your switch is on
                    Log.d("Switch", "ON");
                    createWifiAccessPoint();
                } else {
                    Log.d("Switch", "OFF");
                    createWifiAccessPoint();
                }
            }
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }
    }

    /**
     * Créér le point d'accès WIFI
     */
    private static void createWifiAccessPoint() {
        if(wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(false);
        } else {
            wifiManager.setWifiEnabled(true);
        }
        Method[] wmMethods = wifiManager.getClass().getDeclaredMethods();
        boolean methodFound=false;
        for(Method method: wmMethods){
            if(method.getName().equals("setWifiApEnabled")){
                methodFound=true;
                WifiConfiguration wifiConfiguration = new WifiConfiguration();
                wifiConfiguration.SSID = "Projet";
                wifiConfiguration.hiddenSSID = false;
                try {
                    boolean apstatus = (Boolean) method.invoke(wifiManager, wifiConfiguration, true);
                    for (Method isWifiApEnabledmethod: wmMethods)
                    {
                        if(isWifiApEnabledmethod.getName().equals("isWifiApEnabled")){
                            while (!(Boolean) isWifiApEnabledmethod.invoke(wifiManager)) {};
                            for(Method method1: wmMethods){
                                if(method1.getName().equals("getWifiApState")){
                                    int apstate;
                                    apstate=(Integer) method1.invoke(wifiManager);
                                }
                            }
                        }
                    }
                    if(apstatus) {
                        Log.d("AccesPoint", "Access Point Success !");
                    } else {
                        Log.d("AccesPoint", "Access Point Failed");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if(!methodFound){
            Log.d("AccesPoint", "cannot configure an access point");
        }
    }
}
