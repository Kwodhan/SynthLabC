package com.istic.cable;

import com.istic.port.Port;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;

public class Cable {

    Port portOne;
    Port portTwo;

    public Cable(Port portOne, Port portTwo) {
        this.portOne = portOne;
        this.portTwo = portTwo;

    }

    public Port getPortOne() {
        return portOne;
    }

    public void setPortOne(Port portOne) {
        this.portOne = portOne;
    }

    public Port getPortTwo() {
        return portTwo;
    }

    public void setPortTwo(Port portTwo) {
        this.portTwo = portTwo;
    }

    public void connect() {
        ((UnitOutputPort)portOne.getUnitPort()).connect(0, (UnitInputPort) portTwo.getUnitPort(),0);
        portOne.setConnected(true);
        portTwo.setConnected(true);
    }
    public void disconnect() {
        ((UnitOutputPort)portOne.getUnitPort()).disconnect(0, (UnitInputPort) portTwo.getUnitPort(),0);
        portOne.setConnected(false);
        portTwo.setConnected(false);
    }
}
