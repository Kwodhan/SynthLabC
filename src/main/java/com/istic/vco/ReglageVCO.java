package com.istic.vco;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class ReglageVCO extends UnitGenerator {

    /**
     * Fréquence de base
     */
    private UnitInputPort f0;

    /**
     * Changement d'octave
     */
    private UnitInputPort octave;

    /**
     * Changement de note
     */
    private UnitInputPort fin;

    /**
     * Signal de Modulation de fréquence Fm varie entre -5 et 5
     */
    private UnitInputPort fm;

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

    /**
     * Fréquence de base
     * @return fréquence finale
     */
    public double getFrequence(){
        return this.f0.get() * Math.pow(2,this.octave.get()) * Math.pow(1.05946f,this.fin.get());
    }
}
