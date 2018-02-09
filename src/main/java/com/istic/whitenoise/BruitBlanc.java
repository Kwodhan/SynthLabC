package com.istic.whitenoise;

import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.WhiteNoise;

public class BruitBlanc extends WhiteNoise {

	public PortOutput getOutputPort(){
		return  new PortOutput(this.output);
	}
}
