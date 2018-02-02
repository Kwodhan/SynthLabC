package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitGatePort;

public class PortGate extends Port {
    private UnitGatePort unitGatePort;

    public PortGate(Module module,UnitGatePort unitGatePort) {
        super(module);
        this.unitGatePort = unitGatePort;
        this.visitorConnectPort =  new VisitorGate(this);
    }


    @Override
    public boolean accept(VisitorConnectPort visitor) {
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
