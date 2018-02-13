package com.istic.keyboard;

import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class ReglageKB extends UnitGenerator{
	int octave_min=0;
	int octave=0;
	int octave_max=0;
boolean notes[]= new boolean[13];
double notes_hz[]= new double[13];

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
	
	void add_frequency(int note ) {
		notes_hz[note]=  octave*0; //la formule...
	}
public void update_signal() {
	for (int i =0;i< 13;i++) {
		if (notes[i]== true) {
			//notes_hz[i]; //push effect to output
		}
	}
}
private void toggle (int note) {
	this.notes[note]=  ! this.notes[note];
}
	public void onpressDO() {
		toggle(0);		
	}


	public void onpressRE() {
		toggle(1);		
		
	}

	public void onpressMI() {
		toggle(2);		
		
	}

	public void onpressFA() {
		toggle(3);		
		
	}

	public void onpressSOL() {
		toggle(4);		
		
	}

	public void onpressLA() {
		toggle(5);		
		
	}

	public void onpressSI() {
		toggle(6);		
		
	}

	public void onpressDO2() {
		toggle(7);		
		
	}

	public void onpressDOd() {
		toggle(8);		
		
	}

	public void onpressREd() {
		toggle(9);		
		
	}

	public void onpressFAd() {
		toggle(10);		
		
	}

	public void onpressSOLd() {
		toggle(11);		
		
	}
	public void onpressLAd() {
		toggle(12);		
		
	}
	public void onpressOctaveUP() {
if (this.octave< this.octave_max) octave ++;		
	}

	public void onpressOctaveDOWN() {
		if (this.octave> this.octave_min) octave --;		
		
	}

	public void onreleaseDO() {
		toggle(0);		
		
	}

	public void onreleaseRE() {
		toggle(1);		
		
	}

	public void onreleaseMI() {
		toggle(2);		
		
	}

	public void onreleaseFA() {
		toggle(3);		
		
	}

	public void onreleaseSOL() {
		toggle(4);		
		
	}

	public void onreleaseLA() {
		toggle(5);		
		
	}

	public void onreleaseSI() {
		toggle(6);		
		
	}

	public void onreleaseDO2() {
		toggle(7);		
		
	}

	public void onreleaseDOd() {
		toggle(8);		
		
	}

	public void onreleaseREd() {
		toggle(9);		
		
	}

	public void onreleaseFAd() {
		toggle(10);		
		
	}
	public void onreleaseSOLd() {
		toggle(11);		
		
	}

	public void onreleaseLAd() {
		toggle(12);		
		
	}

	public void onreleaseOctaveUP() {
		// TODO Auto-generated method stub
		
	}

	public void onreleaseOctaveDOWN() {
		// TODO Auto-generated method stub
		
	}


 
	 
}
