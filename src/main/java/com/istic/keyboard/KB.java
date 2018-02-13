package com.istic.keyboard;

import com.istic.port.PortGate;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;

public class KB  extends Circuit {
    private PortOutput portCv; // +1V par octave ???
    private PortGate portGate; //+5V par octave ???
  //  private ReglageKB reglageKB;

    public KB() {
    	this.portGate = new PortGate(new UnitGatePort ("gate"));
		this.portCv = new PortOutput(new UnitOutputPort ("cv"));
    }
	public PortOutput getCv() {
		return portCv;
	}
	public PortGate getGate() {
		return portGate;
	}
    
}
