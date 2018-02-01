package com.istic;

import com.jsyn.unitgen.LineOut;

public class OutMod extends LineOut implements Module {
	private double attenuation = 0;
	private boolean mute = false;

	public double getAttenuation() {
		return attenuation;
	}

	public void setAttenuation(double attenuation) {
		this.attenuation = attenuation;
	}

	public boolean isMute() {
		return mute;
	}

	public void setOnMute() {
		this.mute = true;
	}
	public void setOffMute() {
		this.mute = false;
	}	
	

}
