package com.istic.rep;

import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.istic.vca.ReglageVCA;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitOscillator;

public class REP  extends Circuit{
	   /**
     * Port de sortie du VCO
     */
    private UnitInputPort in;
	private UnitOutputPort out1;
	private UnitOutputPort out2;
	private UnitOutputPort out3;
 

    /**
     * reglage ao, am
     */
    private ReglageREP reglageREP;
    /**
     * reglage wave
     */
     public REP() {

        add(reglageREP = new ReglageREP());
         addPortAlias(out1 = reglageREP.getOut1(), "com/istic/out1");
        addPortAlias(out2 = reglageREP.getOut2(), "com/istic/out2");
        addPortAlias(out3 = reglageREP.getOut3(), "com/istic/out3");
        addPortAlias(in =reglageREP.getInput(),"in");
 
         //addPort(oscillator.getOutput());
        //reglageVCA.getOut().connect(oscillator.amplitude);


    }
     public PortOutput getOutput1() {

         return new PortOutput(out1);
     }
     public PortOutput getOutput2() {

         return new PortOutput(out2);
     }
     public PortOutput getOutput3() {

         return new PortOutput(out3);
     }
 
	 
}
