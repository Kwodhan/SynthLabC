package com.istic.out;

import com.istic.port.PortInput;
import com.jsyn.unitgen.LineOut;
import com.jsyn.util.WaveFileWriter;
import com.softsynth.math.AudioMath;

import java.io.IOException;

/**
 * Module de sortie
 */
public class OutMod extends LineOut {
	/**
	 * Permet d'eviter la saturation
	 */
	private double attenuation = 0.;

	/**
	 * Permet d'enregistrer le son
	 */
	private WaveFileWriter writer;

	/**
	 * true : entrain d'enregister | false : n'est pas entrain d'enregister
	 */
	private boolean record = false;

	/**
	 * 0 : la sortie audio est null | 1 : on entend la sortie audio
	 */
	private int mute = 1;

	/**
	 * Port d'entrée
	 */
	private final PortInput portInput;

	public OutMod() {
		portInput =  new PortInput(this.getInput());
	}

    /**
     * Coupe le son de sortie
     */
	public void toggleMute() {
		this.mute = (this.mute == 1) ? 0 : 1;
	}

    @Override
    public void generate(int start, int limit) {
        double[] inputs0 = input.getValues(0);
        double[] inputs1 = input.getValues(1);
        double[] buffer0 = synthesisEngine.getOutputBuffer(0);
        double[] buffer1 = synthesisEngine.getOutputBuffer(1);
        double fromDCBL ;
        double value0;
        for (int i = start; i < limit; i++) {
        	fromDCBL = AudioMath.semitonesToFrequencyScaler(this.attenuation);
        	value0 = inputs0[i]*fromDCBL;
            buffer0[i] += value0*mute;
            buffer1[i] += inputs1[i]*mute*fromDCBL;
            if (record && (this.writer != null)) {
				try {
					this.writer.write(value0);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
    }

    //Setters & Getters

	public PortInput getPortInput() {
		return this.portInput;
	}

	public void setAttenuation(double att){
		this.attenuation = att;
	}

	public double getAttenuation() {
		return attenuation;
	}

	public int getMute() {
		return mute;
	}

	public void setMute(int mute) {
		this.mute = mute;
	}

	public WaveFileWriter getWriter() {
		return writer;
	}

	public void setWriter(WaveFileWriter writer) {
		this.writer = writer;
	}

	public boolean isRecord() {
		return record;
	}

	public void setRecord(boolean record) {
		this.record = record;
	}

}
