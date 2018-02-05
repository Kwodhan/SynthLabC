package com.istic.port;

import com.jsyn.ports.UnitInputPort;

public class PortFm extends Port {

    private UnitInputPort unitFmPort;

    private Integer Volt = 0;

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

    public Integer getVolt() {
        return Volt;
    }

    public void setVolt(Integer volt) {
        Volt = volt;
    }
}
