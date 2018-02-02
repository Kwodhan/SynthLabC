package com.istic;

import com.istic.port.PortInput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.softsynth.math.AudioMath;

public class OutMod extends LineOut implements Module {
	private double attenuation = 0.;

	private int mute = 1;
	Synthesizer synth;

	public OutMod(Synthesizer synth) {
		// TODO Auto-generated constructor stub
		this.synth = synth;
		this.synth.add(this);
	}

	public double getAttenuation() {
		return attenuation;
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
	
	public void setAttenuation(double att){
		this.attenuation = att;
	}
	
    @Override
    public void generate(int start, int limit) {
        double[] inputs0 = input.getValues(0);
        double[] inputs1 = input.getValues(1);
        double[] buffer0 = synthesisEngine.getOutputBuffer(0);
        double[] buffer1 = synthesisEngine.getOutputBuffer(1);
        double fromDCBL ;
        for (int i = start; i < limit; i++) {
        	fromDCBL = AudioMath.decibelsToAmplitude(this.attenuation);
            buffer0[i] += inputs0[i]*mute*fromDCBL;
            buffer1[i] += inputs1[i]*mute*fromDCBL;
        }
    }


	public PortInput getPortInput() {
		return new PortInput(this,this.getInput());
	}
}
