package com.istic.vcfhp;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.VariableRateMonoReader;

public class ReglageVCFHP extends VariableRateMonoReader {
    /**
     * Réglage manuel en façade de la fréquence
     */
    private UnitInputPort f0;
    /**
     *  Entrée de modulation de fréquence
     */
    private UnitInputPort fm;

    /**
     * une sortie de signal
     */
    private UnitOutputPort out;


    public ReglageVCFHP() {

        addPort(this.f0 = new UnitInputPort("f0"));
        addPort(this.fm = new UnitInputPort("fm"));
        addPort(this.out = new UnitOutputPort("out"));

        this.f0.setMaximum(21000);

    }

    @Override
    public void generate(int start, int limit) {
        double[] f0s = f0.getValues();
        double[] fms = fm.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit ; i++) {
            outputs[i] = Constraints.verifFrequenceMax(f0s[i] * Math.pow(2,Constraints.verifModFreq(Math.abs(fms[i])*Constraints.VOLT)));

        }




    }

    public UnitInputPort getF0() {
        return f0;
    }


    public UnitInputPort getFm() {
        return fm;
    }


    public UnitOutputPort getOut() {
        return out;
    }

}
