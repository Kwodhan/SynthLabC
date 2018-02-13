package com.istic.eg;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EnvelopeDAHDSR;

public class EG extends Circuit {

	private EnvelopeDAHDSR dahdsr;

	PortGate portGate;

	PortOutput portOutput;

	public EG() {

		add(dahdsr = new EnvelopeDAHDSR());

		this.portGate = new PortGate(dahdsr.input);
		this.portOutput = new PortOutput(dahdsr.output);
		this.dahdsr.amplitude.setMaximum(1);
	}

	public PortGate getGateInput() {
		return portGate;
	}

	public PortOutput getOutput() {
		return portOutput;
	}

	/**
	 *
	 * @param attack varie 0.0003 et 8s
	 */
	public void setAttack(double attack) {
		this.dahdsr.attack.set(attack);
	}

	/**
	 *
	 * @param decay varie 0.0003 et 8s
	 */
	public void setDecay(double decay) {
		this.dahdsr.decay.set(decay);
	}

	/**
	 *
	 * @param sustain varie 0 et 5 V
	 */
	public void setSustain(double sustain) {
		this.dahdsr.sustain.set(sustain);
	}

	/**
	 *
	 * @param release varie 0.0003 et 8s
	 */
	public void setRelease(double release) {
		this.dahdsr.release.set(release);

	}
	public double getAttack(){
        return this.dahdsr.attack.get();
	}
    public double getSustain(){
        return this.dahdsr.sustain.get();
    }
    public double getDecay(){
        return this.dahdsr.decay.get();
    }public double getRelease(){
        return this.dahdsr.release.get();
    }
}
