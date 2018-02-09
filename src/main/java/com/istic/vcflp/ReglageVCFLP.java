package com.istic.vcflp;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.VariableRateMonoReader;

public class ReglageVCFLP extends VariableRateMonoReader {
    /**
     * Réglage manuel en façade de la fréquence
     */
    private UnitInputPort f0;
    /**
     * Signal d'entrée
     */
    private UnitInputPort input;
    /**
     *  Entrée de modulation de fréquence
     */
    private UnitInputPort fm;
    /**
     *  Réglage de résonance du filtre
     */
    private UnitInputPort filter;

    /**
     * une sortie de signal
     */
    private UnitOutputPort out;


    public ReglageVCFLP() {

        addPort(this.f0 = new UnitInputPort("f0"));
        addPort(this.input = new UnitInputPort("input"));
        addPort(this.fm = new UnitInputPort("fm"));
        addPort(this.filter= new UnitInputPort("filter"));
        addPort(this.out = new UnitOutputPort("out"));

    }

    @Override
    public void generate(int start, int limit) {
        double[] f0s = f0.getValues();
        double[] inputs = input.getValues();
        double[] fms = fm.getValues();
        double[] filters = filter.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit ; i++) {
//
//            //Fonctionnement du VCF
//            - lorsque que l’entrée `fm` est déconnectée ou nulle, l’oscillateur doit produire un signal à la fréquence `f0`
//
//            - lorsque le filtre est totalement ouvert (fréquence de coupure infinie) le signal de sortie est identique au signal d’entrée
//
//                    - lorsque le filtre est totalement fermé (fréquence de coupure nulle), le signal de sortie est nul
//
//                    - lorsque la tension d’entrée sur `fm` augmente d’1 V, la fréquence de coupure double
//
//            - lorsque la tension d’entrée sur `fm` diminue d’1 V, la fréquence de coupure divisée par deux
        }







    }

    public UnitInputPort getF0() {
        return f0;
    }


    public UnitInputPort getInput() {
        return input;
    }

    public UnitInputPort getFm() {
        return fm;
    }

    public UnitInputPort getFilter() {
        return filter;
    }

    public UnitOutputPort getOut() {
        return out;
    }

}
