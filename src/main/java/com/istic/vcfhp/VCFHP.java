package com.istic.vcfhp;

import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterHighPass;
import com.jsyn.unitgen.FilterLowPass;

public class VCFHP extends Circuit {

    private UnitOutputPort out;

    private UnitInputPort in;

    private FilterHighPass filterHighPass;

    private PortOutput portOutput;

    private PortInput portInput;

    private PortFm portFm;

    /**
     * reglage fm, f0, resonance
     */
    private ReglageVCFHP vcflp;

    public VCFHP() {

        add(filterHighPass = new FilterHighPass());
        add(vcflp = new ReglageVCFHP());

        addPortAlias(out = filterHighPass.getOutput(), "out");
        addPortAlias(in = filterHighPass.getInput(), "in");

        vcflp.getF0().set(22);

        vcflp.getOut().connect(filterHighPass.frequency);

        portOutput = new PortOutput(out);
        portInput = new PortInput(in);
        portFm = new PortFm(this.vcflp.getFm());

    }
    public PortOutput getOutput() {
        return portOutput;
    }
    public PortInput getInput() {
        return portInput;
    }
    public PortFm getFm(){
        return portFm;
    }

    public void setResonance(double resonnance){
        this.filterHighPass.Q.set(resonnance);
    }

    public void setF0(double fo){
        this.vcflp.getF0().set(fo);

    }


}
