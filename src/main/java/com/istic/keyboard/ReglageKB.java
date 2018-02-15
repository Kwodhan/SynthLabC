package com.istic.keyboard;

import java.sql.Timestamp;

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
	
	int derniere_note=-1;
//	int derniere_octave=-1;
	int current_note=-1;
	//int current_octave=0;
    boolean notes_playing[] = new boolean[13];
    long notes_timestamp[] = new long[13];

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
    
    Synthesizer synth;
    SineOscillator sineOsc ;
    LineOut lineOut ;
    boolean firsttime=true;
    boolean playing = false;
private void play_signal (double hz, boolean play) {
		if (firsttime) {
			synth = JSyn.createSynthesizer();
		      sineOsc = new SineOscillator(hz*1000);
		       lineOut = new LineOut();
		    synth.add(lineOut);
		    synth.add(sineOsc);
		    sineOsc.output.connect(0, lineOut.input, 0);   // connect to left channel
		    sineOsc.output.connect(0, lineOut.input, 1);   // connect to right channel
		    lineOut.start();
		    synth.start();
		    firsttime=false;
		    return;
		}
		if (!play) {
			lineOut.stop();
			//synth.stop();
			return;
		}
		//play not first time
		sineOsc.frequency.set(hz*1000);
		lineOut.start();
 }

	
    public String update_ouput_signal() {
    	update_playing();
        this.displayArea.setText(this.print_frequencies());
        return "";

    }
    private int get_last_note () {
long max= 0;
int index=0;
for (int i = 0; i< 13;i++) {
	if (max < notes_timestamp[i]) {
		max=notes_timestamp[i];
		index=i;
	}
	
}
return index;
    }

    
    private void update_playing() {  
       int i = get_last_note();
       
            if (notes[i] == true && ! notes_playing[i]) {
          	  start_playing(i);
          	  return;
            }
           if (notes[i] == true &&  notes_playing[i]) {
          	continue_playing(i);
        	  return;
           }

          if (notes[i] == false && notes_playing[i]) {
          	stop_playing(i);
        	  return;

          }
      }


    
	private void stop_playing(int i) {
		notes_playing[i]=false;
		System.out.println("stop"+i);
		play_signal(notes_hz[i], false);

		
	}

	private void continue_playing(int i) {
//System.out.println("contine"+i);		
	}

	private void start_playing(int i) {
		stop_playing_all();
		notes_playing[i]=true;
		System.out.println("start"+i);
		play_signal(notes_hz[i], true);
		
	}

	private void stop_playing_all() {
        for (int i = 0; i < 13; i++) {
if (notes_playing[i]) {
	stop_playing(i);
	notes_playing[i]=false;
}
        }
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
private long get_timestamp() {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    return timestamp.getTime();  
}
    private void toggle_on(int note) {
this.notes_timestamp[note]=get_timestamp();
if (this.notes[note] == false) {
            this.notes[note] = true;
            this.compute_frequency(note);
            this.update_ouput_signal();
        } else {
            this.update_ouput_signal();

        }
    }

    private void toggle_off(int note) {
    	this.notes_timestamp[note]=get_timestamp();

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
