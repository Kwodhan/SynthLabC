package com.istic.vcfhp;

import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterHighPass;

/**
 *  module Voltage Control Filter type Hig Pass
 */
public class VCFHP extends Circuit {


    /**
     * Filtre HP JSyn
     */
    private final FilterHighPass filterHighPass;

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
     * reglage fm, f0
     */
    private final ReglageVCFHP vcfhp;

    public VCFHP() {

        // ajout des composants
        add(filterHighPass = new FilterHighPass());
        add(vcfhp = new ReglageVCFHP());

        addPortAlias( filterHighPass.getOutput(), "out");
        addPortAlias(filterHighPass.getInput(), "in");

        // connexion entre les composants
        vcfhp.getOut().connect(filterHighPass.frequency);

        // Port du module
        portOutput = new PortOutput(filterHighPass.getOutput());
        portInput = new PortInput(filterHighPass.getInput());
        portFm = new PortFm(this.vcfhp.getFm());

    }

    public void setF0(double fo){
        this.vcfhp.getF0().set(fo);
    }

    /**
     *
     * @return Fréquence de base du VCF HP
     */
    public double getFrequence(){
        return this.vcfhp.getFrequence();
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

    public void setResonance(double resonance){
        this.filterHighPass.Q.set(resonance);
    }

}
