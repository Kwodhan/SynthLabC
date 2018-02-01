package com.istic;

import com.istic.port.PortInput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class OutMod implements Module {
	private double attenuation = 0;
	private boolean mute = false;


	public double getAttenuation() {
		return attenuation;
	}
    private Synthesizer synth;
    private LineOut lineOut;

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


    private PortInput portInput;

    public OutMod(Synthesizer synth) {
        this.synth = synth;
        this.synth.add(lineOut = new LineOut());
        this.portInput  = new PortInput(this,this.lineOut.input);
    }




    @Override
    public void start() {
        this.lineOut.start();
    }

    @Override
    public void stop() throws InterruptedException {
        this.lineOut.stop();
    }

    public PortInput getPortInput() {
        return portInput;
    }
}
