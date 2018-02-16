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

            if(lastnote == null){
                outputsCv[i] = 0;
            }else {
                double v = ((double)octave) + ((double)this.note)/12.;
                outputsCv[i] = v/Constraints.VOLT;
                System.out.println(outputsCv[i]);
            }
        }

    	
    }




    private void toggle_on(int note) {
        this.playing = true;
        this.note = note;

    }

    private void toggle_off(int note) {
        this.playing = false;
        this.lastnote = note;

    }

    public void onpressDO() {
        toggle_on(0);
    }

    public void onpressDOd() {
        toggle_on(1);
    }

    public void onpressRE() {
        toggle_on(2);
    }

    public void onpressREd() {
        toggle_on(3);
    }

    public void onpressMI() {
        toggle_on(4);
    }

    public void onpressFA() {
        toggle_on(5);
    }

    public void onpressFAd() {
        toggle_on(6);
    }

    public void onpressSOL() {
        toggle_on(7);
    }

    public void onpressSOLd() {
        toggle_on(8);
    }

    public void onpressLA() {
        toggle_on(9);
    }

    public void onpressLAd() {
        toggle_on(10);
    }

    public void onpressSI() {
        toggle_on(11);
    }

    public void onpressDO2() {
        toggle_on(12);

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


    public void onreleaseDO() {
        toggle_off(0);
    }

    public void onreleaseDOd() {
        toggle_off(1);
    }

    public void onreleaseRE() {
        toggle_off(2);
    }

    public void onreleaseREd() {
        toggle_off(3);
    }

    public void onreleaseMI() {
        toggle_off(4);
    }

    public void onreleaseFA() {
        toggle_off(5);
    }

    public void onreleaseFAd() {
        toggle_off(6);
    }

    public void onreleaseSOL() {
        toggle_off(7);
    }

    public void onreleaseSOLd() {
        toggle_off(8);
    }

    public void onreleaseLA() {
        toggle_off(9);
    }

    public void onreleaseLAd() {
        toggle_off(10);
    }

    public void onreleaseSI() {
        toggle_off(11);
    }

    public void onreleaseDO2() {
        toggle_off(12);
    }



}
