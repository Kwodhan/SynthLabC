package com.istic.port;

import com.jsyn.ports.UnitGatePort;

/**
 * Port Gate d'un module
 */
public class PortGate extends Port {

    private UnitGatePort unitGatePort;

    public PortGate(UnitGatePort unitGatePort) {
        super();
        this.unitGatePort = unitGatePort;
        this.visitorPort =  new VisitorGate(this);
    }


    @Override
    public boolean accept(VisitorPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitGatePort.disconnectAll();
    }

    public UnitGatePort getUnitGatePort() {
        return unitGatePort;
    }



}
