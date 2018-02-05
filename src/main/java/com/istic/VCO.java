package com.istic;

import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

import java.util.ArrayList;

public class VCO extends Circuit implements Module{


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
    private WaveReglage waveReglage;

    public VCO() {

        this.oscillators = new ArrayList<>();
        this.oscillators.add(new SquareOscillator());
        this.oscillators.add(new TriangleOscillator());
        this.oscillators.add(new SawtoothOscillator());

        add(reglageVCO = new ReglageVCO());
        add(waveReglage = new WaveReglage());
        addPortAlias(out = waveReglage.getOutputPort(),"out");
        addPortAlias(waveReglage.getInputPort(),"in");

        for(UnitOscillator oscillator : this.oscillators){
            add(oscillator);
            addPort(oscillator.getOutput());
            reglageVCO.getOut().connect(oscillator.frequency);
        }

        this.oscillators.get(SQUAREWAVE).getOutput().connect(waveReglage.getInputPort());



        reglageVCO.getF0().set(440);

    }

    public void changeShapeWave(int typeWave) {
        this.waveReglage.getInputPort().disconnectAll();
        this.oscillators.get(typeWave).getOutput().connect(0,waveReglage.getInputPort(),0);
    }

    public void changeF0(double f0){
        this.reglageVCO.getF0().set(f0);
    }

    public void changeFin(double fin){
        this.reglageVCO.getFin().set(fin);
    }

    public void changeOctave(double octave){
        this.reglageVCO.getOctave().set(octave);
    }


    public PortOutput getOutput() {
        return new PortOutput(this,out);
    }

    public PortFm getFm(){
        return new PortFm(this,this.reglageVCO.getFm());
    }


    public double getFrequence() {
        return reglageVCO.getFrequence();
    }
}
