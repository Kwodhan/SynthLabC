package com.istic;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class OutMod extends LineOut implements Module {
	private double attenuation = 0;
	private VCO vco;
	private int mute = 0;
	Synthesizer synth;

	public OutMod(Synthesizer synth) {
		// TODO Auto-generated constructor stub
		this.synth = synth;
	}

	public double getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
		double amp = 1./Math.pow(10, attenuation/10); //changer 1 par l'amplitude du vco
		//apply change 
	}

	public int isMute() {
		return mute;
	}

	public void setOnMute() {
		this.mute = 0;
	}
	public void setOffMute() {
		this.mute = 1;
	}	
	
    @Override
    public void generate(int start, int limit) {
        double[] inputs0 = input.getValues(0);
        double[] inputs1 = input.getValues(1);
        double[] buffer0 = synthesisEngine.getOutputBuffer(0);
        double[] buffer1 = synthesisEngine.getOutputBuffer(1);
        for (int i = start; i < limit; i++) {
            buffer0[i] += inputs0[i]*mute;
            buffer1[i] += inputs1[i]*mute;
        }
    }
	

}
