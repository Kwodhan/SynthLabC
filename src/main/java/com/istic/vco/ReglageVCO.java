package com.istic.vco;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class ReglageVCO extends UnitGenerator {

    private UnitInputPort f0;

    private UnitInputPort octave;

    private UnitInputPort fin;

    private UnitInputPort fm;

    private UnitOutputPort out;

    public ReglageVCO() {


        addPort(this.f0 = new UnitInputPort("fo"));
        addPort(this.octave = new UnitInputPort("octave"));
        addPort(this.fin = new UnitInputPort("fin"));
        addPort(this.fm = new UnitInputPort("fm"));
        addPort(this.out = new UnitOutputPort("com/istic/out"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] f0s = f0.getValues();
        double[] octaves = octave.getValues();
        double[] fins = fin.getValues();
        double[] fms = fm.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = f0s[i] * Math.pow(2,octaves[i]) * Math.pow(1.05946f,fins[i])  * Math.pow(2,fms[i]);

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

    public double getFrequence(){
        return this.f0.get() * Math.pow(2,this.octave.get()) * Math.pow(1.05946f,this.fin.get())  * Math.pow(2,this.getFm().get());
    }
}
