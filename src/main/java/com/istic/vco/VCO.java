package com.istic.vco;


import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.*;

import java.util.ArrayList;
/*
 +--------------------------------------------+
 |                                              |
 |                                              |
 |                                              |
 +--------+        +------+                     |
 | Reglage|   |----OTriang0         +-----------+
 |   VCO  |   |    +------+         |passThrougs|
 O        0---+----OSaw   0     |---O           0
 |        |   |    +------+     |   +-----------+
 |        |   |----OSquare0-----                |
 +-+-+-+--+        +------+                     |
 | | | |                                        |
 | | | |                                        |
 +-O-O-O----------------------------------------+
 */

/**
 * module Voltage Controlled Oscillator
 */
public class VCO extends Circuit {


    public static final int SQUAREWAVE = 0;
    public static final int TRIANGLEWAVE = 1;
    public static final int SAWWAVE = 2;

    /**
     * Liste d'oscillators possible
     */
    private final ArrayList<UnitOscillator> oscillators ;

    /**
     * reglage fo, octave, fm, fin
     */
    private final ReglageVCO reglageVCO;

    /**
     * Permet le changement d'oscillators
     */
    private final PassThrough passThrough;

    /**
     * Port FM
     */
    private final PortFm portFm;

    /**
     * Port de sortie
     */
    private final PortOutput portOutput;

    public VCO() {


        this.oscillators = new ArrayList<>();
        this.oscillators.add(new SquareOscillator());
        this.oscillators.add(new TriangleOscillator());
        this.oscillators.add(new SawtoothOscillator());

        add(reglageVCO = new ReglageVCO());
        add(passThrough = new PassThrough());
        addPort(passThrough.getOutput());
        addPort(passThrough.getInput());


        for(UnitOscillator oscillator : this.oscillators) {
            add(oscillator);
            addPort(oscillator.getOutput());
            // 0.2 Amp => 1 V
            oscillator.amplitude.setup(0,1,10);
            // connexion des 3 oscillators au reglageVCO
            reglageVCO.getOut().connect(oscillator.frequency);
        }
        // Oscillator Carré par defaut
        this.oscillators.get(SQUAREWAVE).getOutput().connect(passThrough.getInput());

        // Note LA par défaut
        reglageVCO.getF0().set(440);

        portFm =  new PortFm(this.reglageVCO.getFm());
        portOutput = new PortOutput(this.passThrough.getOutput());

    }

    /**
     * Choix de la forme du signal
     * @param typeWave type de l'onde
     */
    public void changeShapeWave(int typeWave) {
        // on déconnecte
        this.passThrough.getInput().disconnectAll();

        this.oscillators.get(typeWave).getOutput().connect(0,passThrough.getInput(),0);
    }


    public void changeFin(double fin){
        this.reglageVCO.getFin().set(fin);
    }

    public void changeOctave(double octave){
        this.reglageVCO.getOctave().set(octave);
    }

    /**
     *
     * @return Fréquence de base du VCO
     */
    public double getFrequence() {
        return reglageVCO.getFrequence();
    }


    // Setters & Getters
    public PortOutput getOutput() {

        return portOutput;
    }

    public PortFm getFm(){
        return portFm;
    }
}
