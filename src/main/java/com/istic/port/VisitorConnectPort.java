package com.istic.port;

/**
 * Contrainte pour chaque port
 */
public interface VisitorConnectPort {
    boolean visit(PortInput portInput);
    boolean visit(PortOutput portOutput);
    public boolean visit(PortFm portFm);
    public boolean visit(PortAm portAm);
    public boolean visit(PortGate portGate);



}
