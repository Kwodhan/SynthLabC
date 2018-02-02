package com.istic;

import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;

public class VCO extends Circuit implements Module{

    private TriangleOscillator triangleOscillator;

    private SawtoothOscillator sawtoothOscillator;

    private SquareOscillator squareOscillator;

    /**
     * Port de sortie du VCO
     */
    private UnitOutputPort out;

    /**
     * reglage fo, octave, fm, fin
     */
    private ReglageVCO reglageVCO;


    public VCO() {

        add(squareOscillator = new SquareOscillator());

        addPortAlias(squareOscillator.getOutput(),"squareOut");

        add(reglageVCO = new ReglageVCO());

        addPortAlias(out = squareOscillator.getOutput(),"out");

        reglageVCO.getOut().connect(squareOscillator.frequency);

        reglageVCO.getF0().set(440);


    }

    public void changeShapeWave(ShapeWave shapeWave) {



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
