package com.istic.rep;

import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.istic.vca.ReglageVCA;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitOscillator;

public class REP  extends Circuit{
	   /**
     * Port de sortie du VCO
     */
    private UnitOutputPort out;
    private UnitOscillator oscillator ;


    /**
     * reglage ao, am
     */
    private ReglageREP reglageREP;
    /**
     * reglage wave
     */
     public REP() {

        add(reglageREP = new ReglageREP());
        //add(oscillator);
        //addPort(oscillator.getOutput());
        //reglageREP.getOut().connect(oscillator.amplitude);


    }
    public PortOutput getOutput() {

        return new PortOutput(out);
    }
 
	 
}
