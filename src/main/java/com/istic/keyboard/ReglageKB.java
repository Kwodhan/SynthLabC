package com.istic.keyboard;

import java.sql.Timestamp;

import com.istic.Constraints;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import javafx.scene.control.TextArea;

public class ReglageKB extends UnitGenerator {
    private final int octave_min = -9;
    private final int octave_max = 9;
    private int octave;


    private Integer note;
    private Integer lastnote;

    private UnitOutputPort portGate;
    private UnitOutputPort portCv;


    boolean playing = false;


	

    public UnitOutputPort getPortGate() {
        return portGate;
    }

    public UnitOutputPort getPortCv() {
        return portCv;
    }

    public ReglageKB() {
        addPort(portGate = new UnitOutputPort());
        addPort(portCv = new UnitOutputPort());
        octave = 0;
        lastnote = null;
    }

    /////////////////////////////////////////////////////////////
    @Override
    public void generate(int start, int limit) {
        double[] outputsGate= portGate.getValues();
        double[] outputsCv= portCv.getValues();


        for(int i = start; i < limit; ++i) {
            if(playing) {
                outputsGate[i] = -1;
            }else {
                outputsGate[i] = 1;
            }

            if(lastnote == null || this.note == null){
                outputsCv[i] = 0;
            }else {
                double v = ((double)octave) + ((double)this.note)/12.;
                outputsCv[i] = v/Constraints.VOLT;
                System.out.println(outputsCv[i]);
            }
        }

    	
    }




    protected void toggle_on(int note) {
        this.playing = true;
        System.out.println(note);
        this.note = note;

    }

    protected void toggle_off(int note) {
        this.playing = false;
        this.lastnote = note;

    }



    public void onpressOctaveUP() {
        if (this.octave <= this.octave_max) {
            octave++;
        }

    }

    public void onpressOctaveDOWN() {
        if (this.octave >= this.octave_min) {
            octave--;
        }


    }






}
