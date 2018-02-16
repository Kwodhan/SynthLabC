package com.istic.mixer;

import com.istic.Constraints;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

import java.util.ArrayList;
import java.util.List;

public class ReglageMIXER extends UnitGenerator {

    private List<UnitInputPort> inputs;
    private List<UnitInputPort> inAtts;

    private UnitOutputPort out;

    public ReglageMIXER() {
        this.inAtts = new ArrayList<>();
        this.inputs = new ArrayList<>();
        this.inputs.add(new UnitInputPort("in1"));
        this.inputs.add(new UnitInputPort("in2"));
        this.inputs.add(new UnitInputPort("in3"));
        this.inputs.add(new UnitInputPort("in4"));
        this.inAtts.add(new UnitInputPort("in1Att"));
        this.inAtts.add(new UnitInputPort("in2Att"));
        this.inAtts.add(new UnitInputPort("in3Att"));
        this.inAtts.add(new UnitInputPort("in3Att"));


        for(UnitInputPort unitInputPort : this.inputs){
            addPort(unitInputPort);
        }
        for(UnitInputPort unitInputPort : this.inAtts){
            addPort(unitInputPort);
        }
        addPort(this.out = new UnitOutputPort("out"),"out");
    }



    @Override
    public void generate(int start, int limit) {


        double[] outputs= out.getValues();


        for(int i = start; i < limit; ++i) {
            double deb = 0.0D;

            for(int j = 0; j < 4; ++j) {
                double[] in = this.inputs.get(j).getValues();
                double[] att = this.inAtts.get(j).getValues();

                deb += in[i] * att[i];
            }
            outputs[i] = Constraints.verifAmp(deb);
        }
    }

    //Setters & Getters

    public UnitInputPort getIn1() {
        return this.inputs.get(0);
    }

    public UnitInputPort getIn2() {
        return this.inputs.get(1);
    }

    public UnitInputPort getIn3() {
        return this.inputs.get(2);
    }

    public UnitInputPort getIn4() {
        return this.inputs.get(3);
    }

    public UnitOutputPort getOut() {
        return out;
    }

    public UnitInputPort getIn1Att() {
        return this.inAtts.get(0);
    }


    public UnitInputPort getIn2Att() {
        return this.inAtts.get(1);
    }



    public UnitInputPort getIn3Att() {
        return this.inAtts.get(2);
    }


    public UnitInputPort getIn4Att() {
        return this.inAtts.get(3);
    }


}
