package com.istic.eg;

import com.istic.port.PortGate;
import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.EnvelopeDAHDSR;

/**
 * module Enveloppe Generator
 */
public class EG extends Circuit {

    /**
     * EG JSyn
     */
    private final EnvelopeDAHDSR dahdsr;

    /**
     * Port d'entrée Gate
     */
	private final PortGate portGate;

    /**
     * Port de Sortie
     */
	private final PortOutput portOutput;

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
     * temps de montée
     * @param attack varie entre 0 et 8 s
     */
	public void setAttack(double attack) {
		this.dahdsr.attack.set(attack);
	}

    /**
     * temps d’extinction
     * @param decay varie entre 0 et 8 s
     */
	public void setDecay(double decay) {
		this.dahdsr.decay.set(decay);
	}

    /**
     * niveau de maintien
     * @param sustain varie entre 0 et 1 d'amplitude
     */
	public void setSustain(double sustain) {
		this.dahdsr.sustain.set(sustain);
	}

    /**
     * temps de relâchement
     * @param release varie entre 0 et 8 s
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
    }

    public double getRelease(){
        return this.dahdsr.release.get();
    }
}
