package com.istic.eg;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.EnvelopeDAHDSR;

public class EG {
	EnvelopeDAHDSR env = new EnvelopeDAHDSR();
	
	public EnvelopeDAHDSR getEnv() {
		return this.env;
	}

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
