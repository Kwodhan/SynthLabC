package com.istic.whitenoise;

import com.istic.port.PortOutput;
import com.jsyn.unitgen.WhiteNoise;

/**
 *  module bruit blanc
 */
public class BruitBlanc extends WhiteNoise {

	/**
	 * Port de sortie
	 */
	private final PortOutput out;

	public BruitBlanc(){
		this.out = new PortOutput(this.output);
	}

	public PortOutput getOutputPort(){
		return  out;
	}
}
