package com.istic.vca;

import com.istic.port.PortAm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;

/**
 * un module Voltage Control Amplifier
 */
public class VCA extends Circuit {
    /**
     * Port de sortie du VCA
     */
    private final PortOutput portOutput;

    /**
     * Port d'entrée du VCA
     */
    private final PortInput portInput;

    /**
     * Port AM du VCA
     */
    private final PortAm portAm;
    /**
     * Reglage ao, am
     */
    private final ReglageVCA reglageVCA;

    public VCA() {

        add(reglageVCA = new ReglageVCA());

        addPortAlias(reglageVCA.getOut(), "out");
        addPortAlias(reglageVCA.getInput(), "in");

        reglageVCA.getA0().set(0);

        portOutput = new PortOutput(this.reglageVCA.getOut());
        portInput = new PortInput(this.reglageVCA.getInput());
        portAm = new PortAm(this.reglageVCA.getAm());

    }

    public void changeA0(double a0){
        this.reglageVCA.getA0().set(a0);
    }

    public PortOutput getOutput() {

        return portOutput;
    }
    public PortInput getInput() {

        return portInput;
    }
    public PortAm getAm(){
        return portAm;
    }



}
