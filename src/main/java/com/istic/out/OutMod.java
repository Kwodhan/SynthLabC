package com.istic.out;

import com.istic.port.PortInput;
import com.jsyn.unitgen.LineOut;
import com.jsyn.util.WaveFileWriter;
import com.softsynth.math.AudioMath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Module de sortie
 */
public class OutMod extends LineOut {
	/**
	 * permet d'eviter la saturation
	 */
	private double attenuation = 0.;
	private WaveFileWriter writer;

	private boolean record = false;


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
		try {
			writer = new WaveFileWriter(new File("./src/main/resources/sound/savedSound.wav"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

    /**
     * Coupe le son de sortie
     */
	public void toggleMute() {
		this.mute = (this.mute == 1) ? 0 : 1;
	}

    /**
     * Modifie le signal
     * @param start début de modification
     * @param limit fin de modification
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
            if (record) {
				try {
					writer.write(buffer0[i]);
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

	public boolean isRecord() {
		return record;
	}
	public void setRecord(boolean record) {
		this.record = record;
	}
}
