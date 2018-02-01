package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitBlockPort;

public abstract class Port {

    private Module module;
    private UnitBlockPort unitPort;
    boolean connected = false;

    public Port(Module module, UnitBlockPort unitPort) {
        this.module = module;
        this.unitPort = unitPort;

    }

    public abstract boolean isInput();

    public abstract boolean isOutput();



    public boolean isConnected() {
        return connected;
    }

    public UnitBlockPort getUnitPort() {
        return unitPort;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }
}
