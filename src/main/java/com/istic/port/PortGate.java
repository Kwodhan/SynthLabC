package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitInputPort;

public class PortGate extends Port {
    private UnitInputPort unitGatePort;

    public PortGate(Module module) {
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

    public UnitInputPort getUnitGatePort() {
        return unitGatePort;
    }

    public void setUnitGatePort(UnitInputPort unitGatePort) {
        this.unitGatePort = unitGatePort;
    }
}
