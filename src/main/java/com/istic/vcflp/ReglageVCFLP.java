package com.istic.vcflp;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.VariableRateMonoReader;

/**
 * Reglage des entrées du VCFLP
 */
public class ReglageVCFLP extends UnitGenerator {

    /**
     * Réglage manuel de la fréquence de base
     */
    private UnitInputPort f0;
    /**
     *  Entrée de modulation de fréquence
     */
    private UnitInputPort fm;

    /**
     * Sortie de signal
     */
    private UnitOutputPort out;


    public ReglageVCFLP() {

        addPort(this.f0 = new UnitInputPort("f0"));
        addPort(this.fm = new UnitInputPort("fm"));
        addPort(this.out = new UnitOutputPort("out"));
        this.fm.setMaximum(2);
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

    public double getFrequence(){
        return this.f0.get();
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
