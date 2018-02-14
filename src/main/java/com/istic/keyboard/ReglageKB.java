package com.istic.keyboard;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import javafx.scene.control.TextArea;

public class ReglageKB extends UnitGenerator {
    int octave_min = 0;
    int octave = 0;
    int octave_max = 0;
    final double Vo = 1.0476;
    boolean notes[] = new boolean[13];
    //int notes_index[] = {-9,-7,-5,-4,-2,0,1,2,3,4,5,6};//do re mi fa sol la si do
    double notes_hz[] = new double[13];
    String notes_descr[] = {
            "DO", "DOd", "RE", "REd", "MI", "FA", "FAd", "SOL", "SOLd", "LA", "LAd", "SI", "DO2"};

    private SineOscillator[] oscillators;

    private UnitGatePort portGate;
    private UnitOutputPort portCv;
    
	private TextArea displayArea;

    public UnitGatePort getPortGate() {
        return portGate;
    }

    public UnitOutputPort getPortCv() {
        return portCv;
    }

    public ReglageKB(TextArea displayArea) {
    	this.displayArea= displayArea;
        for (boolean n : notes) {
            n = false;
        }
        for (double n : notes_hz) {
            n = 0.0d;
        }
        octave_min = 0;
        octave = 3;
        octave_max = 7;
    }

    /////////////////////////////////////////////////////////////
    @Override
    public void generate(int arg0, int arg1) {
        // TODO Auto-generated method stub

    }

    public String update_ouput_signal() {
        for (int i = 0; i < 13; i++) {
            //oscillators[i].stop();
            if (notes[i] == true) {
//			oscillators[i]=new SineOscillator(notes_hz[i]); 
//			oscillators[i].start();
            }
        }
        
        this.displayArea.setText(this.print_frequencies());
        return "";

    }

    public static void main(String[] args) {
        ReglageKB rkb = new ReglageKB(null);

        Synthesizer synth = JSyn.createSynthesizer();
        SineOscillator sineOsc = new SineOscillator(440);
        SineOscillator sineOsc2 = new SineOscillator(440);
        LineOut lineOut = new LineOut();
        synth.add(lineOut);
        synth.add(sineOsc2);
        synth.add(sineOsc);
        sineOsc.output.connect(0, lineOut.input, 0);   // connect to left channel
        sineOsc.output.connect(0, lineOut.input, 1);   // connect to right channel

        sineOsc2.output.connect(0, lineOut.input, 1);   // connect to right channel
        sineOsc2.output.connect(0, lineOut.input, 1);   // connect to right channel
        lineOut.start();
        synth.start();
        
    }

    /////////////////////////////////////////////////////////////////:
    private String print_frequencies() {
        String res="";
        for (int i = 0; i < 13; i++) {
            if (notes[i] == true) {
               String  result = this.format_double(this.notes_hz[i]);

                res+="(" + this.notes_descr[i] + " " + result + "hz ) "+" //with octave=" + this.octave+"\n";
            } else {
                res+="(" + this.notes_descr[i] + " "  + ") "+" //with octave=" + this.octave+"\n";

            }
        }
         return  res;
    }

    private String format_double(double d) {
        return String.format("%.3f", d);
    }

    private void compute_frequency(int index) {
        //		notes_hz[note]=  octave*0; //la formule...
        notes_hz[index] = formule_frequency_note(this.octave, index);

    }

    private double formule_frequency_note(int octave, int index) {//r=1.05946f
        return this.Vo * Math.pow(2, octave - 5) * Math.pow(2, index / 12.0);

    }

    private void toggle_on(int note) {
        if (this.notes[note] == false) {
            this.notes[note] = true;
            this.compute_frequency(note);
            this.update_ouput_signal();
        } else {
            this.update_ouput_signal();

        }
    }

    private void toggle_off(int note) {
        if (this.notes[note] == true) {

            this.notes[note] = false;
            this.update_ouput_signal();
        } else {
            this.update_ouput_signal();

        }
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
        if (this.octave < this.octave_max) octave++;
        this.update_ouput_signal();
    }

    public void onpressOctaveDOWN() {
        if (this.octave > this.octave_min) octave--;
        this.update_ouput_signal();

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

    public void onreleaseOctaveUP() {
        // TODO Auto-generated method stub

    }

    public void onreleaseOctaveDOWN() {
        // TODO Auto-generated method stub

    }

	public void ignore_key() {
this.update_ouput_signal();		
	}


}
