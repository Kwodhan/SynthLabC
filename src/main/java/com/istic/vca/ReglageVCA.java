package com.istic.vca;

import com.istic.Constant;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.softsynth.math.AudioMath;

public class ReglageVCA  extends VariableRateMonoReader{

    private UnitInputPort a0;

    private UnitInputPort input;

    private UnitInputPort am;

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
           if(ams[i] > 0 ) {
                outputs[i] = inputs[i] * Math.pow(2, 2 * ((Math.abs(ams[i]) * Constant.Volt) - 5 / Constant.Volt));

            }else if (ams[i] < 0){
                outputs[i] = inputs[i] * Math.pow(2, 2 * ((Math.abs(ams[i]) * Constant.Volt) - 5 / Constant.Volt));
            }else{
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

    public double getAmplitude(){
        /*if(getAm().get()==0){
            return 0;
        }else{

            return this.a0.get() *this.input.get() * this.getAm().get()*12;

        }
        */
        return this.a0.get() +this.getInput().get() + this.getAm().get()*12;

    }

}
