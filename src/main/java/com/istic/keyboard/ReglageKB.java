package com.istic.keyboard;

import com.istic.Constraints;
import com.istic.port.PortOutput;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

public class ReglageKB extends UnitGenerator{
	int octave_min=0;
	int octave=0;
	int octave_max=0;
	final int Vo=1;
boolean notes[]= new boolean[13];
//int notes_index[] = {-9,-7,-5,-4,-2,0,1,2,3,4,5,6};//do re mi fa sol la si do
double notes_hz[]= new double[13];
String notes_descr []= {
		"DO","DOd","RE","REd","MI","FA","FAd","SOL","SOLd","LA","LAd","SI","DO2"};

private SineOscillator[] oscillators;

public   ReglageKB() {
	for (boolean n : notes) {
		n=false;
		}
	for (double n : notes_hz) {
		n=0.0d;
		}
	octave_min=0;
	octave =0;
	octave_max=0;
}

	@Override
	public void generate(int arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	public void update_ouput_signal() {
	for (int i =0;i< 13;i++) {
		oscillators[i].stop();
		if (notes[i]== true) {
			oscillators[i]=new SineOscillator(notes_hz[i]); 
			oscillators[i].start();
 		}
	}
}
    public static void main(String[] args) {
		ReglageKB rkb =  new ReglageKB();
		Synthesizer synth = JSyn.createSynthesizer();
		SineOscillator sineOsc = new SineOscillator(300);
		SineOscillator sineOsc2 = new SineOscillator(440); 
		LineOut  lineOut = new LineOut();
		synth.add(lineOut );
		synth.add(sineOsc2 );
				synth.add(sineOsc );				



		

		sineOsc.output.connect( 0, lineOut.input, 0 );   // connect to left channel
		sineOsc.output.connect( 0, lineOut.input, 1 );   // connect to right channel
		lineOut.start();
		synth.start();
	}
	/////////////////////////////////////////////////////////////////:
    private void print_frequencies () {
    	
    }
	private void compute_frequency(int index) {
		//		notes_hz[note]=  octave*0; //la formule...
		notes_hz[index]= formule_frequency_note(this.octave, index);

	}
	private double formule_frequency_note(int octave, int index ) {//r=1.05946f
		return this.Vo*Math.pow(2,octave)*Math.pow(2,index/12);

	}

private void toggle (int note) {
	this.notes[note]=  ! this.notes[note];
}
	
public void onpressDO() {
	toggle(0);		this.compute_frequency(0);this.update_ouput_signal();
}
public void onpressDOd() {
	toggle(1);		this.compute_frequency(1);this.update_ouput_signal();
}
public void onpressRE() {
	toggle(2);		this.compute_frequency(2);this.update_ouput_signal();
}
public void onpressREd() {
	toggle(3);		this.compute_frequency(3);this.update_ouput_signal();
}
public void onpressMI() {
	toggle(4);		this.compute_frequency(4);this.update_ouput_signal();
	
}
public void onpressFA() {
	toggle(5); this.compute_frequency(5);		this.update_ouput_signal();
}
public void onpressFAd() {
	toggle(6);		this.compute_frequency(6);this.update_ouput_signal();
}
public void onpressSOL() {
	toggle(7);		this.compute_frequency(7);this.update_ouput_signal();
}
public void onpressSOLd() {
	toggle(8);		this.compute_frequency(8);this.update_ouput_signal();
}
public void onpressLA() {
	toggle(9);		this.compute_frequency(9);this.update_ouput_signal();
}
public void onpressLAd() {
	toggle(10);		this.compute_frequency(10);this.update_ouput_signal();
}
public void onpressSI() {
	toggle(11);		this.compute_frequency(11);this.update_ouput_signal();
}
public void onpressDO2() {
	toggle(12);		this.compute_frequency(12);this.update_ouput_signal();
	
}

	public void onpressOctaveUP() {
if (this.octave< this.octave_max) octave ++;		this.update_ouput_signal();
	}

	public void onpressOctaveDOWN() {
		if (this.octave> this.octave_min) octave --;	this.update_ouput_signal();	
		
	}
	public void onreleaseDO() {
		toggle(0);		this.update_ouput_signal();
	}
	public void onreleaseDOd() {
		toggle(1);		this.update_ouput_signal();
	}
	public void onreleaseRE() {
		toggle(2);		this.update_ouput_signal();
	}
	public void onreleaseREd() {
		toggle(3);		this.update_ouput_signal();
	}
	public void onreleaseMI() {
		toggle(4);		this.update_ouput_signal();
		
	}

	public void onreleaseFA() {
		toggle(5);		this.update_ouput_signal();
	}
	public void onreleaseFAd() {
		toggle(6);		this.update_ouput_signal();
	}
	public void onreleaseSOL() {
		toggle(7);		this.update_ouput_signal();
	}
	public void onreleaseSOLd() {
		toggle(8);		this.update_ouput_signal();
	}
	public void onreleaseLA() {
		toggle(9);		this.update_ouput_signal();
	}
	public void onreleaseLAd() {
		toggle(10);		this.update_ouput_signal();
	}
	public void onreleaseSI() {
		toggle(11);		this.update_ouput_signal();
	}
	public void onreleaseDO2() {
		toggle(12);		this.update_ouput_signal();
		
	}

	public void onreleaseOctaveUP() {
		// TODO Auto-generated method stub
		
	}

	public void onreleaseOctaveDOWN() {
		// TODO Auto-generated method stub
		
	}


 
	 
}
