package com.istic.sequencer;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

import com.istic.Constraints;
import com.istic.port.PortGate;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.ports.UnitPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.UnitGate;

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
	 * retourne le output du sequenceur 
	 * @return
	 */
	public PortOutput getOutputPort() {
		return out;
	}

	/**
	 * retourne le inpput gate
	 * @return
	 */
	public PortGate getGatePort() {
		return gate;
	}

	/**
	 * Change la valeur du pas (ind+1) par d
	 * @param d
	 * @param ind
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
	 * @param v
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
}
