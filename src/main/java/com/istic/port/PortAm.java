package com.istic.port;

import com.jsyn.ports.UnitInputPort;

/**
 * Port AM d'un module
 */
public class PortAm extends Port {

    private UnitInputPort unitAmPort;

    public PortAm(UnitInputPort unitAmPort) {
        super();
        this.unitAmPort = unitAmPort;
        this.visitorPort = new VisitorAm(this);
    }

    @Override
    public boolean accept(VisitorPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitAmPort.disconnectAll();
    }

    //Setters & Getters
    public UnitInputPort getUnitAmPort() {
        return unitAmPort;
    }
}

