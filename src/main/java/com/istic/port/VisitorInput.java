package com.istic.port;

public class VisitorInput  implements VisitorConnectPort {
    PortInput portInput;

    public VisitorInput(PortInput portInput) {
        this.portInput = portInput;
    }
    @Override
    public boolean visit(PortInput portInput) {

        return false;

    }

    @Override
    public boolean visit(PortOutput portOutput) {
        portOutput.getUnitOutputPort().connect(0,portInput.getUnitInputPort(),0);

        return true;

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
