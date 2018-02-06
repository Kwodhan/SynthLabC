package com.istic.rep;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class ReglageREP extends UnitGenerator {
 
	    private UnitInputPort in;
	    private UnitOutputPort out1;
	    private UnitOutputPort out2;
	    private UnitOutputPort out3;


	    
	    public ReglageREP() {


 	        addPort(this.in  = new UnitInputPort("in"));
 	        addPort(this.out1 = new UnitOutputPort("com/istic/out1"));
 	        addPort(this.out2 = new UnitOutputPort("com/istic/out2"));
 	        addPort(this.out3 = new UnitOutputPort("com/istic/out3"));


	    }

	    @Override
	    public void generate(int start, int limit) {
	        double[] inputs = in.getValues();
	        double[] outputs1= out1.getValues();
	        double[] outputs2= out2.getValues();
	        double[] outputs3= out3.getValues();

	        for (int i = start; i < limit; i++) {
	            outputs1[i] =inputs[i];
	            outputs2[i] =inputs[i];
	            outputs3[i] =inputs[i];

	        }
	    }

 
	    public UnitInputPort getInput() {
	        return in;
	    }

	    public UnitOutputPort getOut1() {
	        return out1;
	    }
	    public UnitOutputPort getOut2() {
	        return out2;
	    }
	    public UnitOutputPort getOut3() {
	        return out3;
	    }
 
}
