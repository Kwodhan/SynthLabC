package com.istic.mixer;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Multiply;
import com.jsyn.unitgen.UnitGenerator;
import com.jsyn.unitgen.UnitVoice;

public class ReglageMIXER extends UnitGenerator {

    private Multiply multiply;
    private UnitInputPort in1,in2,in3,in4;
    private UnitInputPort in1Att,in2Att,in3Att,in4Att;
    private UnitOutputPort out;

    public ReglageMIXER() {


        addPort(this.in1  = new UnitInputPort("in1"));
        addPort(this.in2  = new UnitInputPort("in2"));
        addPort(this.in3  = new UnitInputPort("in3"));
        addPort(this.in4  = new UnitInputPort("in4"));
        addPort(this.in1Att  = new UnitInputPort("in1Att"));
        addPort(this.in2Att  = new UnitInputPort("in2Att"));
        addPort(this.in3Att  = new UnitInputPort("in3Att"));
        addPort(this.in4Att  = new UnitInputPort("in4Att"));
        addPort(this.out = new UnitOutputPort("out1"));


    }



    @Override
    public void generate(int start, int limit) {
        double[] inputs1 = in1.getValues();
        double[] inputs2 = in2.getValues();
        double[] inputs3 = in3.getValues();
        double[] inputs4 = in4.getValues();
        double[] inputs1Att = in1Att.getValues();
        double[] inputs2Att = in2Att.getValues();
        double[] inputs3Att = in3Att.getValues();
        double[] inputs4Att = in4Att.getValues();
        double[] outputs= out.getValues();
        double in1,in2,in3,in4;

        for (int i = start; i < limit; i++) {


                in1=inputs1[i]+inputs1Att[i];


                in2=inputs2[i]+inputs2Att[i];


                in3=inputs3[i]+inputs3Att[i];


                in4=inputs4[i]+inputs4Att[i];


           if(in1<=0) in1=0;
           if(in1<=0) in2=0;
           if(in1<=0) in3=0;
           if(in1<=0) in4=0;
            outputs[i] = Constraints.verifAmp(in1*in2*in3*in4);
        }
    }

    public UnitInputPort getIn1() {
        return in1;
    }

    public UnitInputPort getIn2() {
        return in2;
    }

    public UnitInputPort getIn3() {
        return in3;
    }

    public UnitInputPort getIn4() {
        return in4;
    }

    public UnitOutputPort getOut() {
        return out;
    }

    public UnitInputPort getIn1Att() {
        return in1Att;
    }


    public UnitInputPort getIn2Att() {
        return in2Att;
    }



    public UnitInputPort getIn3Att() {
        return in3Att;
    }


    public UnitInputPort getIn4Att() {
        return in4Att;
    }


}
