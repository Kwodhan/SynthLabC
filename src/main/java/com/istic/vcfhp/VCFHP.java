package com.istic.vcflp;

import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.FilterLowPass;

import java.util.ArrayList;
import java.util.List;

public class VCFLP extends Circuit {
    /**
     * Port de sortie du VCF LP
     */
    private UnitOutputPort out;

    private UnitInputPort in;

    private FilterLowPass filterLowPass;

    private PortOutput portOutput;

    private PortInput portInput;

    private PortFm portFm;
    /**
     * reglage fm, f0, filter
     */
    private ReglageVCFLP vcflp;

    public VCFLP() {

        add(filterLowPass = new FilterLowPass());
        add(vcflp = new ReglageVCFLP());

        addPortAlias(out = filterLowPass.getOutput(), "out");
        addPortAlias(in = filterLowPass.getInput(), "in");

        vcflp.getF0().set(440);

        vcflp.getOut().connect(filterLowPass.frequency);

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
        this.filterLowPass.Q.set(resonnance);
    }

    public void setF0(double fo){
        this.vcflp.getF0().set(fo);

    }


}
