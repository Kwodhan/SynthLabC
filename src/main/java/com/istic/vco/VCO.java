package com.istic.vco;

import com.istic.Constant;
import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

import java.util.ArrayList;

public class VCO extends Circuit {


    public static final int SQUAREWAVE = 0;
    public static final int TRIANGLEWAVE = 1;
    public static final int SAWWAVE = 2;

    private ArrayList<UnitOscillator> oscillators ;

    /**
     * Port de sortie du VCO
     */
    private UnitOutputPort out;


    /**
     * reglage fo, octave, fm, fin
     */
    private ReglageVCO reglageVCO;

    /**
     * reglage wave
     */
    private PassThrough passThrough;

    PortFm portFm;

    PortOutput portOutput;
    public VCO() {

        this.oscillators = new ArrayList<>();
        this.oscillators.add(new SquareOscillator());
        this.oscillators.add(new TriangleOscillator());
        this.oscillators.add(new SawtoothOscillator());

        add(reglageVCO = new ReglageVCO());
        add(passThrough = new PassThrough());
        addPortAlias(out = passThrough.getOutput(), "out");
        addPortAlias(passThrough.getInput(),"in");

        for(UnitOscillator oscillator : this.oscillators){
            add(oscillator);
            addPort(oscillator.getOutput());
            oscillator.amplitude.setup(0,(double)1/Constant.Volt,10);

            reglageVCO.getOut().connect(oscillator.frequency);
        }

        this.oscillators.get(SQUAREWAVE).getOutput().connect(passThrough.getInput());

        reglageVCO.getF0().set(440);

        portFm =  new PortFm(this.reglageVCO.getFm());
        portOutput = new PortOutput(out);

    }

    public void changeShapeWave(int typeWave) {
        this.passThrough.getInput().disconnectAll();

        this.oscillators.get(typeWave).getOutput().connect(0,passThrough.getInput(),0);
    }


    public void changeFin(double fin){
        this.reglageVCO.getFin().set(fin);
    }

    public void changeOctave(double octave){
        this.reglageVCO.getOctave().set(octave);
    }


    public PortOutput getOutput() {

        return portOutput;
    }

    public PortFm getFm(){
        return portFm;
    }


    public double getFrequence() {
        return reglageVCO.getFrequence();
    }
}
