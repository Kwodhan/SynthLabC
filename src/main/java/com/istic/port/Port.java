package com.istic.port;

public abstract class Port {



    boolean connected = false;

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
        System.out.println("je suis con"+connected);
        this.connected = connected;
    }

    abstract public boolean accept(VisitorConnectPort visitor);

    abstract public void disconnect();
}
