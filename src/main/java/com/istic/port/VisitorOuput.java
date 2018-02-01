package com.istic.port;

public class VisitorOuput implements VisitorConnectPort {
    PortOutput portOutput;

    public VisitorOuput(PortOutput portOutput) {
        this.portOutput = portOutput;
    }

    @Override
    public boolean visit(PortInput portInput) {
        portOutput.getUnitOutputPort().connect(0,portInput.getUnitInputPort(),0);

        return true;

    }

    @Override
    public boolean visit(PortOutput portOutput) {

        return false;

    }

    @Override
    public boolean visit(PortFm portFm) {
        return false;
    }

    @Override
    public boolean visit(PortAm portAm) {
        return false;
    }

    @Override
    public boolean visit(PortGate portGate) {
        return false;
    }
}
