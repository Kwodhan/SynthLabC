package com.istic.sequencer;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

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

public class Sequence extends UnitGate {

	private PortOutput out;
	private PortGate gate;

	private double values[];
	private int step = 0;
	private double threshold;
	private boolean exceeded ;

	public Sequence() {
		step = 0;
		threshold = 0.0;
		exceeded = false;
		Arrays.fill(values = new double[8], 0.);
		out = new PortOutput(this.getOutput());
		gate = new PortGate(this.input);
	}

	public PortOutput getOutputPort() {
		return out;
	}

	public PortGate getGatePort() {
		return gate;
	}

	public void setValue(double d, int ind) {
		values[ind] = d;
	}

	@Override
	public void generate(int start, int limit) {
		double[] inputs = input.getValues();
		double[] outputs = output.getValues();
		for (int i = start; i < limit; i++) {
			nextStep(inputs[i]);
			outputs[i] = inputs[i] * values[step];
		}
	}

	private void nextStep(double v) {
		if (v > threshold) {
			if (!exceeded) {
				exceeded = true;
				this.step = (this.step < 7) ? step + 1 : 0;
				System.out.println(step);
			}
		}
		else exceeded = false;
	}
}
