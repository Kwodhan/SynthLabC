package com.istic.whitenoise;

import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.WhiteNoise;

/**
 *  module bruit blanc
 */
public class BruitBlanc extends Circuit {
	/**
	 * Object JSyn
	 */
	private WhiteNoise whiteNoise;
	/**
	 * Port de sortie
	 */
	private final PortOutput out;

	public BruitBlanc(){
		add(whiteNoise = new WhiteNoise());

		this.out = new PortOutput(whiteNoise.output);
	}

	public PortOutput getOutput(){
		return  out;
	}
}
