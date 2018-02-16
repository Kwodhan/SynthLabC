package com.istic.keyboard;

import java.util.ArrayList;

import com.istic.port.PortGate;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitOscillator;

import javafx.scene.control.TextArea;

public class KB  extends Circuit {
    private PortOutput portCv;
    private PortOutput portGate;
    public ReglageKB reglageKB ;
   public KBListener kblistener;

    public KB(TextArea displayArea) {
    	reglageKB = new ReglageKB( );
    	kblistener=  new KBListener(this.reglageKB);
    	add(reglageKB);
    	this.portGate = new PortOutput(this.reglageKB.getPortGate());
		this.portCv = new PortOutput(this.reglageKB.getPortCv());
    }
	public PortOutput getCv() {
		return portCv;
	}
	public PortOutput getGate() {
		return portGate;
	}
    
}
