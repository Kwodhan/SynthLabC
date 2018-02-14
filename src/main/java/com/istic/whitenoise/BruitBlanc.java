package com.istic.whitenoise;

import com.istic.port.PortOutput;
import com.jsyn.unitgen.WhiteNoise;

/**
 *  Classe pour la creation du bruit blanc
 */
public class BruitBlanc extends WhiteNoise {
	
	private PortOutput out;
	
	/**
	 * Constructeur de la class BruitBlanc et son port de sortie
	 */
	public BruitBlanc(){
		this.out = new PortOutput(this.output);
	}

	/**
	 * getter pour le port de sortie
	 * @return
	 */
	public PortOutput getOutputPort(){
		return  out;
	}
}
