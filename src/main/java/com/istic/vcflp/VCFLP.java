package com.istic.vcflp;

import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterLowPass;
/**
 * module Voltage Control Filter type Low Pass
 */
public class VCFLP extends Circuit {
    /**
     * Filtre LP 1 JSyn
     */
    private final FilterLowPass filterLowPass1;

    /**
     * Filtre LP 2 JSyn
     */
    private final FilterLowPass filterLowPass2;

    /**
     * Port de sortie
     */
    private final PortOutput portOutput;

    /**
     * Port d'entrée
     */
    private final PortInput portInput;

    /**
     * Port FM
     */
    private final PortFm portFm;

    /**
     * reglage fm, f0, resonance
     */
    private final ReglageVCFLP vcflp;

    public VCFLP() {

        add(filterLowPass1 = new FilterLowPass());
        add(filterLowPass2 = new FilterLowPass());
        add(vcflp = new ReglageVCFLP());

        addPortAlias(filterLowPass2.getOutput(), "out");
        addPortAlias(filterLowPass1.getInput(), "in");

        filterLowPass1.output.connect(filterLowPass2.input);

        filterLowPass1.frequency.connect(vcflp.getOut());
        filterLowPass2.frequency.connect(vcflp.getOut());


        portOutput = new PortOutput(filterLowPass2.getOutput());
        portInput = new PortInput(filterLowPass1.getInput());
        portFm = new PortFm(this.vcflp.getFm());

    }


    public void setResonance(double resonance){
        this.filterLowPass2.Q.set(resonance);
    }

    public void setF0(double fo){
        this.vcflp.getF0().set(fo);
    }

    /**
     *
     * @return Fréquence de base du VCF LP
     */
    public double getFrequence(){
        return this.vcflp.getFrequence();
    }

    //Setters & Getters

    public PortOutput getOutput() {
        return portOutput;
    }

    public PortInput getInput() {
        return portInput;
    }

    public PortFm getFm(){
        return portFm;
    }
}
