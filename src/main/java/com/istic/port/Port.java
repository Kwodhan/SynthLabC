package com.istic.port;

/**
 * Correspond à un port d'un module
 */
public abstract class Port {


    /**
     * Si le port est cable à un cable
     */
    boolean connected = false;
    /**
     * Comportement à definir quand il est cablé avec un autre port
     */
    protected VisitorConnectPort visitorConnectPort;

    public Port() {



    }

    public VisitorConnectPort getVisitorConnectPort() {
        return visitorConnectPort;
    }

    public boolean isConnected() {
        return connected;
    }



    public void setConnected(boolean connected) {

        this.connected = connected;
    }

    abstract public boolean accept(VisitorConnectPort visitor);

    abstract public void disconnect();
}
