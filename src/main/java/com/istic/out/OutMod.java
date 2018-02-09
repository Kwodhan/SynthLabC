package com.istic.out;

import com.istic.port.PortInput;
import com.jsyn.unitgen.LineOut;
import com.softsynth.math.AudioMath;

/**
 * Module de sortie
 */
public class OutMod extends LineOut {
	/**
	 * permet d'eviter la saturation
	 */
	private double attenuation = 0.;

	/**
	 * 0 la sortie audio est null | 1 on entend la sortie audio
	 */
	private int mute = 1;

	/**
	 * entrée du signal audio
	 */
	private PortInput portInput;

	public OutMod() {
		portInput =  new PortInput(this.getInput());


	}

    /**
     * Coupe le son de sortie
     */
	public void toggleMute() {
		this.mute = (this.mute == 1) ? 0 : 1;
	}

    /**
     * Modifie le signal
     * @param start
     * @param limit
     */
    @Override
    public void generate(int start, int limit) {
        double[] inputs0 = input.getValues(0);
        double[] inputs1 = input.getValues(1);
        double[] buffer0 = synthesisEngine.getOutputBuffer(0);
        double[] buffer1 = synthesisEngine.getOutputBuffer(1);
        double fromDCBL ;
        for (int i = start; i < limit; i++) {
        	fromDCBL = AudioMath.semitonesToFrequencyScaler(this.attenuation);
            buffer0[i] += inputs0[i]*mute*fromDCBL;
            buffer1[i] += inputs1[i]*mute*fromDCBL;
        }
    }

    //Setters & Getters

	public PortInput getPortInput() {
		return this.portInput;
	}

	public void setAttenuation(double att){
		this.attenuation = att;
	}
}
