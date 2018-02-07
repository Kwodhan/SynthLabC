package com.istic.eg;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.EnvelopeDAHDSR;

public class EG extends EnvelopeDAHDSR {
//	EnvelopeDAHDSR env = new EnvelopeDAHDSR();
//	
//	public EnvelopeDAHDSR getEnv() {
//		return this.env;
//	}

	PortGate portGate;

	PortOutput portOutput;

	public EG() {
		this.portGate = new PortGate(this.input);
		this.portOutput = new PortOutput(this.output);
	}

	public PortGate getGateInput() {
		return portGate;
	}

	public PortOutput getOutputPort() {
		return portOutput;
	}
 
	public void setAttack(double attack) {
		this.attack.set(attack);
	}
 

	public void setDecay(double decay) {
		this.decay.set(decay);
	}
 

	public void setSustain(double sustain) {
		this.sustain.set(sustain);

	} 
	public void setRelease(double release) {
		this.release.set(release);

	}

}
