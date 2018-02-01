package com.istic.port;

public class VisitorAm implements VisitorConnectPort {
    PortAm portInput;

    public VisitorAm(PortAm portGate) {
        this.portInput = portGate;
    }


    @Override
    public boolean visit(PortInput portInput) {

        return false;

    }

    @Override
    public boolean visit(PortOutput portOutput) {

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
