package com.istic.eg;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.*;

public class EG extends Circuit {
	private EnvelopeDAHDSR dahdsr;

	PortGate portGate;

	PortOutput portOutput;

	public EG() {

		add(dahdsr = new EnvelopeDAHDSR());

		this.portGate = new PortGate(dahdsr.input);
		this.portOutput = new PortOutput(dahdsr.output);
		this.dahdsr.attack.setMaximum(0.05d);
		this.dahdsr.decay.setMaximum(0.5d);
		this.dahdsr.sustain.setMaximum(5);
		this.dahdsr.release.setMaximum(0.5d);
		// varie entre 0 et 5 volt
		this.dahdsr.amplitude.setMaximum(1);
	}

	public PortGate getGateInput() {
		return portGate;
	}

	public PortOutput getOutputPort() {
		return portOutput;
	}
 
	public void setAttack(double attack) {
		this.dahdsr.attack.set(attack);
	}
 

	public void setDecay(double decay) {
		this.dahdsr.decay.set(decay);
	}
 

	public void setSustain(double sustain) {
		this.dahdsr.sustain.set(sustain);

	} 
	public void setRelease(double release) {
		this.dahdsr.release.set(release);

	}

}
