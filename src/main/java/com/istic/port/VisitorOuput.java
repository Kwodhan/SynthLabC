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
        portOutput.getUnitOutputPort().connect(0,portFm.getUnitInputPort(),0);
        return true;
    }

    @Override
    public boolean visit(PortAm portAm) {
        portOutput.getUnitOutputPort().connect(0,portAm.getUnitAmPort(),0);
        return true;
    }

    @Override
    public boolean visit(PortGate portGate) {
        portOutput.getUnitOutputPort().connect(portGate.getUnitGatePort());
        return true;
    }
}
