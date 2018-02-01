package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitInputPort;

public class PortInput extends Port {

    private UnitInputPort unitInputPort;


    public PortInput(Module module, UnitInputPort unitInputPort) {
        super(module);
        this.unitInputPort = unitInputPort;
        this.visitorConnectPort =  new VisitorInput(this);
    }


    @Override
    public boolean accept(VisitorConnectPort visitor) {
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
