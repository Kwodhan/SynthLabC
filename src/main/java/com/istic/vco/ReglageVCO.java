package com.istic.vco;

import com.istic.util.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Réglage des entrées du VCO
 */
public class ReglageVCO extends UnitGenerator {

    /**
     * Fréquence de base
     */
    private UnitInputPort f0;

    /**
     * Réglage manuel du changement d'octave
     */
    private UnitInputPort octave;

    /**
     * Réglage manuel du changement de note
     */
    private UnitInputPort fin;

    /**
     * Entrée de modulation de fréquenceO
     */
    private UnitInputPort fm;

    /**
     * Sortie de signal
     */
    private UnitOutputPort out;

    public ReglageVCO() {


        addPort(this.f0 = new UnitInputPort("fo"));
        addPort(this.octave = new UnitInputPort("octave"));
        addPort(this.fin = new UnitInputPort("fin"));
        addPort(this.fm = new UnitInputPort("fm"));
        addPort(this.out = new UnitOutputPort("out"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] f0s = f0.getValues();
        double[] octaves = octave.getValues();
        double[] fins = fin.getValues();
        double[] fms = fm.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = f0s[i] * Math.pow(2,octaves[i] + Constraints.verifModFreq(fms[i]*Constraints.VOLT)) * Math.pow(1.05946f,fins[i]);

        }
    }

    /**
     * Fréquence de base avec réglage sur le module
     * @return fréquence de base
     */
    public double getFrequence(){
        return this.f0.get() * Math.pow(2,this.octave.get()) * Math.pow(1.05946f,this.fin.get());
    }

    //Setters & Getters
    public UnitInputPort getF0() {
        return f0;
    }

    public UnitInputPort getOctave() {
        return octave;
    }

    public UnitInputPort getFin() {
        return fin;
    }

    public UnitInputPort getFm() {
        return fm;
    }

    public UnitOutputPort getOut() {
        return out;
    }
}
