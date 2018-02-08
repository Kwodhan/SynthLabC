package com.istic.vca;

import com.istic.port.PortAm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;

public class VCA extends Circuit {
    /**
     * Port de sortie du VCA
     */
    private UnitOutputPort out;

    private UnitInputPort in;

    private PortOutput portOutput;

    private PortInput portInput;

    private PortAm portAm;
    /**
     * reglage ao, am
     */
    private ReglageVCA reglageVCA;

    public VCA() {

        add(reglageVCA = new ReglageVCA());

        addPortAlias(out = reglageVCA.getOut(), "out");
        addPortAlias(in = reglageVCA.getInput(), "in");


        reglageVCA.getA0().set(0);


        portOutput = new PortOutput(out);
        portInput = new PortInput(in);
        portAm = new PortAm(this.reglageVCA.getAm());

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


    public void changeA0(double a0){
        this.reglageVCA.getA0().set(a0);
    }


}
