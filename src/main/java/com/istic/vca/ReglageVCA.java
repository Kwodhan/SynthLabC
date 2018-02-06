package com.istic.vca;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.VariableRateMonoReader;

public class ReglageVCA  extends VariableRateMonoReader{

    private UnitInputPort a0;

    private UnitInputPort input;

    private UnitInputPort am;

    private UnitOutputPort out;


    public ReglageVCA() {


        addPort(this.a0 = new UnitInputPort("a0"));
        addPort(this.input = new UnitInputPort("fin"));
        addPort(this.am = new UnitInputPort("am"));
        addPort(this.out = new UnitOutputPort("com/istic/out"));

        /* Connect envelope to oscillator amplitude. */

    }

    @Override
    public void generate(int start, int limit) {
        double[] a0s = a0.getValues();
        double[] inputs = input.getValues();
        double[] ams = am.getValues();
        double[] outputs = out.getValues();

        for (int i = start; i < limit; i++) {
            if(ams[i]!=0){
                outputs[i] =inputs[i]+a0s[i] + (ams[i]*12);

            }else{
                outputs[i] =0;

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

    public double getAmplitude(){
        return this.a0.get() *this.input.get() * this.getAm().get()*12;

    }

}
