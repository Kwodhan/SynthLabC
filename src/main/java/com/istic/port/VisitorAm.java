package com.istic.port;

public class VisitorAm implements VisitorPort {

    private PortAm portAm;

    public VisitorAm(PortAm portAm) {
        this.portAm = portAm;
    }


    @Override
    public boolean visit(PortInput portInput) {

        return false;

    }

    @Override
    public boolean visit(PortOutput portOutput) {
        portOutput.getUnitOutputPort().connect(0,portAm.getUnitAmPort(),0);
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
