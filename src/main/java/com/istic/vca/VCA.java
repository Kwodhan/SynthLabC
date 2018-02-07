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

    /**
     * reglage ao, am
     */
    private ReglageVCA reglageVCA;
    /**
     * reglage wave
     */
    private PassThrough passThrough;

    public VCA() {

        add(reglageVCA = new ReglageVCA());
        add(passThrough = new PassThrough());
        addPortAlias(out = passThrough.getOutput(), "out");
        addPortAlias(in = reglageVCA.getInput(), "in");
        reglageVCA.getOut().connect(passThrough.getInput());
        reglageVCA.getInput();

        reglageVCA.getA0().set(0);
        //reglageVCA.getInput().connect();
        //reglageVCA.getOut().connect(passThrough.amp);

        portOutput = new PortOutput(out);
        portInput = new PortInput(in);

    }
    public PortOutput getOutput() {

        return portOutput;
    }
    public PortInput getInput() {

        return portInput;
    }
    public PortAm getAm(){
        return new PortAm(this.reglageVCA.getAm());
    }


    public double getAmplitude() {
        return reglageVCA.getAmplitude();
    }
    public void changeA0(double a0){
        this.reglageVCA.getA0().set(a0);
    }

    public void changeAm(double am){
        this.reglageVCA.getAm().set(am);
    }

}
