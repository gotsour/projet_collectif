package com.ufrstgi.imr.application.object;

import android.util.Log;

/**
 * Created by Thomas Westermann on 17/01/2017.
 * Université de Franche-Comté
 * thomas.westermann@orange.fr
 * Application Projet_collectif
 */

public class ClientScanResult {

    private String IpAddr;

    private String HWAddr;

    private String Device;

    private boolean isReachable;

    public ClientScanResult(String ipAddr, String hWAddr, String device, boolean isReachable) {
        super();
        IpAddr = ipAddr;
        HWAddr = hWAddr;
        Device = device;
        this.setReachable(isReachable);
    }


    @Override
    public boolean  equals(Object obj) {
        Colis colis= (Colis)obj;
        boolean result = false;
        if (colis == null ) {
            result = false;
        } else {
            if (colis.getAdresse_mac()== this.getHWAddr()) {
                Log.d("mac adresse","colis :"+colis.getAdresse_mac()+" carte :"+this.getHWAddr());
                result = true;
            }
        }
        return result;
    }

    public String getIpAddr() {
        return IpAddr;
    }

    public void setIpAddr(String ipAddr) {
        IpAddr = ipAddr;
    }

    public String getHWAddr() {
        return HWAddr;
    }

    public void setHWAddr(String hWAddr) {
        HWAddr = hWAddr;
    }

    public String getDevice() {
        return Device;
    }

    public void setDevice(String device) {
        Device = device;
    }

    public void setReachable(boolean isReachable) {
        this.isReachable = isReachable;
    }

    public boolean isReachable() {
        return isReachable;
    }

}
