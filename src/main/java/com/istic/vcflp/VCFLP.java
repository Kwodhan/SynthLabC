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

    private FilterLowPass filterLowPass1;

    private FilterLowPass filterLowPass2;

    private PortOutput portOutput;

    private PortInput portInput;

    private PortFm portFm;

    /**
     * reglage fm, f0, resonance
     */
    private ReglageVCFLP vcflp;

    public VCFLP() {

        add(filterLowPass1 = new FilterLowPass());
        add(filterLowPass2 = new FilterLowPass());
        add(vcflp = new ReglageVCFLP());

        addPortAlias(out = filterLowPass2.getOutput(), "out");
        addPortAlias(in = filterLowPass1.getInput(), "in");

        filterLowPass1.output.connect(filterLowPass2.input);

        filterLowPass1.frequency.connect(vcflp.getOut());
        filterLowPass2.frequency.connect(vcflp.getOut());


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
        this.filterLowPass2.Q.set(resonnance);


    }

    public void setF0(double fo){
        this.vcflp.getF0().set(fo);

    }

    public double getFrequence(){
        return this.vcflp.getFrequence();
    }


}
