package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitInputPort;

public class PortFm extends Port {

    private UnitInputPort unitFmPort;

    public PortFm(Module module) {
        super(module);
        this.unitFmPort = unitFmPort;
        this.visitorConnectPort =  new VisitorFm(this);
    }


    @Override
    public boolean accept(VisitorConnectPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitFmPort.disconnectAll();
    }

}
