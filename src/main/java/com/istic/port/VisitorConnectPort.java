package com.istic.port;

/**
 * Contrainte pour chaque port
 */
public interface VisitorConnectPort {
    boolean visit(PortInput portInput);
    boolean visit(PortOutput portOutput);
    boolean visit(PortFm portFm);
    boolean visit(PortAm portAm);
    boolean visit(PortGate portGate);



}
