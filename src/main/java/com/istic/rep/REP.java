package com.istic.rep;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;

public class REP  extends Circuit{


    /**
     * Port de sortie du VCO
     */
    private UnitInputPort in;
	private UnitOutputPort out1;
	private UnitOutputPort out2;
	private UnitOutputPort out3;

	PortOutput portOutput1;

	PortOutput portOutput2;

	PortOutput portOutput3;

	PortInput portInput;
 

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
        addPortAlias(in = reglageREP.getInput(),"in");

         portInput = new PortInput(in);
         portOutput1 = new PortOutput(out1);
         portOutput2 = new PortOutput(out2);
         portOutput3 = new PortOutput(out3);
         //addPort(oscillator.getOutput());
        //reglageVCA.getOut().connect(oscillator.amplitude);


    }
     public PortOutput getOutput1() {

         return portOutput1;
     }
     public PortOutput getOutput2() {

         return portOutput2;
     }
     public PortOutput getOutput3() {

         return portOutput3;
     }

     public PortInput getPortInput(){
         return portInput;
     }

    public PortInput getInput() {
        return new PortInput(in);
    }
 
	 
}
