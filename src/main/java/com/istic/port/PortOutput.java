package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitOutputPort;

public class PortOutput extends Port {

    private UnitOutputPort unitOutputPort;

    public PortOutput(Module module, UnitOutputPort unitOutputPort) {
        super(module);
        this.unitOutputPort = unitOutputPort;
        this.visitorConnectPort =  new VisitorOuput(this);
    }


    @Override
    public boolean accept(VisitorConnectPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitOutputPort.disconnectAll();
    }

    public UnitOutputPort getUnitOutputPort() {
        return unitOutputPort;
    }


}
