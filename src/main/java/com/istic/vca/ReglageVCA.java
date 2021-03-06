package com.istic.vca;

import com.istic.util.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Réglage des entrées du VCA
 */
public class ReglageVCA  extends UnitGenerator {

    /**
     * Attenuation de 0 à -inf
     */
    private UnitInputPort a0;

    /**
     * Signal d'entrée
     */
    private UnitInputPort input;

    /**
     * Signal de modulation d'amplitude.
     */
    private UnitInputPort am;

    /**
     * Sortie du signal
     */
    private UnitOutputPort out;


    public ReglageVCA() {

        addPort(this.a0 = new UnitInputPort("a0"));
        addPort(this.input = new UnitInputPort("input"));
        addPort(this.am = new UnitInputPort("am"));
        addPort(this.out = new UnitOutputPort("out"));

    }

    @Override
    public void generate(int start, int limit) {
        double[] a0s = a0.getValues();
        double[] inputs = input.getValues();
        double[] ams = am.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit ; i++) {

           if(ams[i] != 0 ) {
                outputs[i] = inputs[i] * Math.pow(2, 2 * (Constraints.verifModAmp(Math.abs(ams[i]) * Constraints.VOLT) - 5 ) + a0s[i]);
            } else{
                outputs[i] = 0;
            }
        }

    }

    public UnitInputPort getA0() {
        return a0;
    }

    public UnitInputPort getInput() {
        return input;
    }

    public UnitInputPort getAm() {
        return am;
    }

    public UnitOutputPort getOut() {
        return out;
    }

}
