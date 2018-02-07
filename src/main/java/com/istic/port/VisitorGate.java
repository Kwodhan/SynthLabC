package com.istic.port;

public class VisitorGate  implements VisitorConnectPort{

    PortGate portGate;

    public VisitorGate(PortGate portGate) {
        this.portGate = portGate;
    }

    @Override
    public boolean visit(PortInput portInput) {
        return false;
    }

    @Override
    public boolean visit(PortOutput portOutput) {
        portOutput.getUnitOutputPort().connect(portGate.getUnitGatePort());
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
