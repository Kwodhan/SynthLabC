package com.istic.vco;

import com.istic.Constant;
import com.istic.port.PortFm;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.*;

import java.util.ArrayList;

/**
 * +--------------------------------------------+
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
        addPort(out = passThrough.getOutput());
        addPort(passThrough.getInput());


        for(UnitOscillator oscillator : this.oscillators){
            add(oscillator);
            addPort(oscillator.getOutput());
            // 0.2 Amp => 1 V
            oscillator.amplitude.setup(0,(double)1/Constant.Volt,10);
            // connexion des 3 oscillators au reglageVCO
            reglageVCO.getOut().connect(oscillator.frequency);
        }
        // Oscillator Carré par defaut
        this.oscillators.get(SQUAREWAVE).getOutput().connect(passThrough.getInput());

        reglageVCO.getF0().set(440);

        portFm =  new PortFm(this.reglageVCO.getFm());
        portOutput = new PortOutput(out);

    }

    /**
     * Choix de la forme du signal
     * @param typeWave
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
