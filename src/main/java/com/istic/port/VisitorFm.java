package com.istic.port;

public class VisitorFm implements VisitorConnectPort {
    PortFm portFm;

    public VisitorFm(PortFm portFm) {

        this.portFm = portFm;
    }


    @Override
    public boolean visit(PortInput portInput) {
        return false;
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
