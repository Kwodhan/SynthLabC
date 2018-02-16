package com.istic.sequencer;

import com.istic.Constraints;
import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.UnitGate;

import java.util.Arrays;

/**
 * Classe sequenceur  avec 8 pas
 * 
 */
public class Sequenceur extends UnitGate {

	private PortOutput out;
	private PortGate gate;

	private double values[];
	private int step = 0;
	private double threshold;
	private boolean exceeded ;

	/**
	 * creation du sequenceur et initialisation des attribues
	 */
	public Sequenceur() {
		step = 0;
		threshold = 0.0;
		exceeded = false;
		Arrays.fill(values = new double[8], 0.);
		out = new PortOutput(this.getOutput());
		gate = new PortGate(this.input);
	}


	/**
	 * Change la valeur du pas (ind+1) par d
	 * @param d valeur remplacement
	 * @param ind valeur remplacée
	 */
	public void setValue(double d, int ind) {
		values[ind] = d;
	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		for (int i = start; i < limit; i++) {
			nextStep(inputs[i]);
			outputs[i] = Constraints.verifAmp((inputs[i] * values[step])*5)/5;
		}
	}

	/**
	 * passer au pas suivant si front montant
	 * @param v voltage
	 */
	private void nextStep(double v) {
		if (v > threshold) {
			if (!exceeded) {
				exceeded = true;
				this.step = (this.step < 7) ? step + 1 : 0;
			}
		}
		else exceeded = false;
	}

	//Setters & Getters
	/**
	 * retourne le output du sequenceur
	 * @return port de sortie
	 */
	public PortOutput getOutputPort() {
		return out;
	}

	/**
	 * retourne le input gate
	 * @return port gate
	 */
	public PortGate getGatePort() {
		return gate;
	}

}
