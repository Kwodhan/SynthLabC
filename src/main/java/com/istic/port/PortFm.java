package com.istic.port;

import com.jsyn.ports.UnitInputPort;

/**
 * Port FM d'un module
 */
public class PortFm extends Port {

    private UnitInputPort unitFmPort;

    public PortFm(UnitInputPort unitFmPort) {
        super();
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

    public UnitInputPort getUnitInputPort() {
        return unitFmPort;
    }

}
