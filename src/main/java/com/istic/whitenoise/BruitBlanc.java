package com.istic.whitenoise;

import com.istic.port.PortOutput;
import com.jsyn.unitgen.WhiteNoise;

public class BruitBlanc extends WhiteNoise {
	
	private PortOutput out;
	public BruitBlanc(){
		this.out = new PortOutput(this.output);
	}

	public PortOutput getOutputPort(){
		return  out;
	}
}
