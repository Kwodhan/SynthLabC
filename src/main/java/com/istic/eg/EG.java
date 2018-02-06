package com.istic.eg;

import java.nio.channels.GatheringByteChannel;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.EnvelopeDAHDSR;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SquareOscillator;

public class EG {
	EnvelopeDAHDSR env = new EnvelopeDAHDSR();

	public PortGate getGateInput() {
		return new PortGate(env.input);
	}

	public PortOutput getOutputPort() {
		return new PortOutput(env.output);
	}

	public double getAttack() {
		return this.env.attack.getValue();
	}

	public void setAttack(double attack) {
		this.env.attack.set(attack);
	}

	public double getDecay() {
		return this.env.decay.getValue();
	}

	public void setDecay(double decay) {
		this.env.decay.set(decay);

	}

	public double getSustain() {
		return this.env.sustain.getValue();
	}

	public void setSustain(double sustain) {
		this.env.sustain.set(sustain);

	}

	public double getRelease() {
		return this.env.release.getValue();
	}

	public void setRelease(double release) {
		this.env.release.set(release);

	}

}
