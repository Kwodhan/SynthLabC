package com.istic.port;

import com.istic.Module;

public abstract class Port {

    private Module module;

    boolean connected = false;

    protected VisitorConnectPort visitorConnectPort;

    public Port(Module module) {
        this.module = module;


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
