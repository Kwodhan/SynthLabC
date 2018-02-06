package com.istic.vca;

import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.istic.vco.ReglageVCO;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;
import com.jsyn.unitgen.UnitOscillator;

import java.util.ArrayList;

public class VCA extends Circuit {
    /**
     * Port de sortie du VCO
     */
    private UnitOutputPort out;
    private UnitOscillator oscillator ;


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
        addPortAlias(out = passThrough.getOutput(), "com/istic/out");
        addPortAlias(passThrough.getInput(),"in");
        reglageVCA.getA0().set(440);

        add(oscillator);
        addPort(oscillator.getOutput());
        reglageVCA.getOut().connect(oscillator.amplitude);


    }
    public PortOutput getOutput() {

        return new PortOutput(out);
    }
    public PortFm getAm(){
        return new PortFm(this.reglageVCA.getAm());
    }


    public double getAmplitude() {
        return reglageVCA.getAmplitude();
    }}
