package com.istic.port;

/**
 * Définition des contraintes pour chaque port
 */
public interface VisitorPort {
    boolean visit(PortInput portInput);
    boolean visit(PortOutput portOutput);
    boolean visit(PortFm portFm);
    boolean visit(PortAm portAm);
    boolean visit(PortGate portGate);



}
