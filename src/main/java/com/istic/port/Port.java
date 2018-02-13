package com.istic.port;

import java.io.Serializable;

/**
 * Correspond à un port d'un module
 */
public abstract class Port implements Serializable {


    /**
     * Si le port est cablé à un cable
     */

    boolean connected = false;

    /**
     * Définie les contraintes avec un autre port
     */
    protected VisitorPort visitorPort;


    public VisitorPort getVisitorPort() {
        return visitorPort;
    }

    public boolean isConnected() {
        return connected;
    }



    public void setConnected(boolean connected) {

        this.connected = connected;
    }

    /**
     *
     * @param visitor le comportement à adopter avec un autre port
     * @return true si la cable est branché | false si le cable n'est pas branché
     */
    abstract public boolean accept(VisitorPort visitor);

    abstract public void disconnect();
}
