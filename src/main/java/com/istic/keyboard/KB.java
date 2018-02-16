package com.istic.keyboard;

import com.istic.port.PortOutput;
import com.jsyn.unitgen.Circuit;

/**
 * Module KeyBoard
 */
public class KB  extends Circuit {
    /**
     * Produit entre -5 et 5 V
     */
    private PortOutput portCv;

    /**
     *  Produit -5 V quand aucune touche est enfoncé
     *  Produit 5 V quand une touche est enfoncé
     */
    private PortOutput portGate;


    public ReglageKB reglageKB ;


    public KB() {
    	reglageKB = new ReglageKB( );

    	add(reglageKB);
    	this.portGate = new PortOutput(this.reglageKB.getPortGate());
		this.portCv = new PortOutput(this.reglageKB.getPortCv());
    }

    public String print() {
    	return reglageKB.print_frequencies();
	}

	public void onpressDO() {
		this.reglageKB.toggle_on(0);
	}

	public void onpressDOd() {
		this.reglageKB.toggle_on(1);
	}

	public void onpressRE() {
		this.reglageKB.toggle_on(2);
	}

	public void onpressREd() {
		this.reglageKB.toggle_on(3);
	}

	public void onpressMI() {
		this.reglageKB.toggle_on(4);
	}

	public void onpressFA() {
		this.reglageKB.toggle_on(5);
	}

	public void onpressFAd() {
		this.reglageKB.toggle_on(6);
	}

	public void onpressSOL() {
		this.reglageKB.toggle_on(7);
	}

	public void onpressSOLd() {
		this.reglageKB.toggle_on(8);
	}

	public void onpressLA() {
		this.reglageKB.toggle_on(9);
	}

	public void onpressLAd() {
		this.reglageKB.toggle_on(10);
	}

	public void onpressSI() {
		this.reglageKB.toggle_on(11);
	}

	public void onpressDO2() {
		this.reglageKB.toggle_on(12);

	}

	public void onpressOctaveUP() {
		this.reglageKB.onpressOctaveUP();

	}

	public void onpressOctaveDOWN() {
		this.reglageKB.onpressOctaveDOWN();


	}


	public void onreleaseDO() {
		this.reglageKB.toggle_off(0);
	}

	public void onreleaseDOd() {
		this.reglageKB.toggle_off(1);
	}

	public void onreleaseRE() {
		this.reglageKB.toggle_off(2);
	}

	public void onreleaseREd() {
		this.reglageKB.toggle_off(3);
	}

	public void onreleaseMI() {
		this.reglageKB.toggle_off(4);
	}

	public void onreleaseFA() {
		this.reglageKB.toggle_off(5);
	}

	public void onreleaseFAd() {
		this.reglageKB.toggle_off(6);
	}

	public void onreleaseSOL() {
		this.reglageKB.toggle_off(7);
	}

	public void onreleaseSOLd() {
		this.reglageKB.toggle_off(8);
	}

	public void onreleaseLA() {
		this.reglageKB.toggle_off(9);
	}

	public void onreleaseLAd() {
		this.reglageKB.toggle_off(10);
	}

	public void onreleaseSI() {
		this.reglageKB.toggle_off(11);
	}

	public void onreleaseDO2() {
		this.reglageKB.toggle_off(12);
	}
	public PortOutput getCv() {
		return portCv;
	}
	public PortOutput getGate() {
		return portGate;
	}
    
}
