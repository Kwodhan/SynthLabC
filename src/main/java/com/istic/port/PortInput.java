package com.istic.port;

import com.jsyn.ports.UnitInputPort;

/**
 * Port input d'un module
 */
public class PortInput extends Port {

    private UnitInputPort unitInputPort;

    public PortInput(UnitInputPort unitInputPort) {
        super();
        this.unitInputPort = unitInputPort;
        this.visitorPort =  new VisitorInput(this);
    }


    @Override
    public boolean accept(VisitorPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitInputPort.disconnectAll();
    }

    public UnitInputPort getUnitInputPort() {
        return unitInputPort;
    }
}
