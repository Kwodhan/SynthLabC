package com.istic.port;

import com.jsyn.ports.UnitOutputPort;

/**
 * Port OutPut d'un module
 */
public class PortOutput extends Port {

    private UnitOutputPort unitOutputPort;

    public PortOutput( UnitOutputPort unitOutputPort) {
        super();
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
