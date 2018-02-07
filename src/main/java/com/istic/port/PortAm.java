package com.istic.port;

import com.jsyn.ports.UnitInputPort;

public class PortAm extends Port {


    private UnitInputPort unitAmPort;


    public PortAm(UnitInputPort unitAmPort) {
        super();
        this.unitAmPort = unitAmPort;
        this.visitorConnectPort = new VisitorAm(this);
    }


    @Override
    public boolean accept(VisitorConnectPort visitor) {
        return visitor.visit(this);
    }

    @Override
    public void disconnect() {
        this.unitAmPort.disconnectAll();
    }

    public UnitInputPort getUnitAmPort() {
        return unitAmPort;
    }
}

