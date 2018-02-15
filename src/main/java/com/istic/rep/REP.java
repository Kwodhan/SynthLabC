package com.istic.rep;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;

/*
 *
 +--------------------------------------+
 |                                      |
 |        +--------------------+        |
 |        |                    |        |
 |        |     Reglage        +--------0
 +--------O       REP          |        |
 |        |                    +--------0
 |        |                    |        |
 |        |                    +--------0
 |        +--------------------+        |
 |                                      |
 +--------------------------------------+

 */

/**
 * module réplicateur
 */
public class REP extends Circuit{

    /**
     * Port de sortie 1
     */
	private final PortOutput portOutput1;

    /**
     * Port de sortie 2
     */
    private final PortOutput portOutput2;

    /**
     * Port de sortie 3
     */
    private final PortOutput portOutput3;

    /**
     * Port d'entrée
     */
    private final PortInput portInput;

    /**
     * Réplicateur du signal
     */
    private final REPPassThrough REPPassThrough;

    public REP() {

        add(REPPassThrough = new REPPassThrough());
        addPortAlias(REPPassThrough.getOut1(), "out1");
        addPortAlias(REPPassThrough.getOut2(), "out2");
        addPortAlias(REPPassThrough.getOut3(), "out3");
        addPortAlias(REPPassThrough.getInput(),"in");

        portInput = new PortInput(REPPassThrough.getInput());
        portOutput1 = new PortOutput(REPPassThrough.getOut1());
        portOutput2 = new PortOutput(REPPassThrough.getOut2());
        portOutput3 = new PortOutput(REPPassThrough.getOut3());



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
     public PortInput getInput(){
         return portInput;
     }
}
