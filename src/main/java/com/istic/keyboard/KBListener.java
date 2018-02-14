package com.istic.keyboard;
import javafx.scene.Scene;

import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode; 

public class KBListener implements EventHandler<KeyEvent> {
	ReglageKB reglageKB;
	public KBListener(ReglageKB rkb) {
		this.reglageKB= rkb;
	}
	public void add_listener (Scene sc) {
		sc.setOnKeyPressed(this);
	}
	@Override
	public void handle(KeyEvent event) {
		if (event.getEventType() == KeyEvent.KEY_PRESSED) {
		   switch (event.getCode()) {
		   case Q:   reglageKB.onpressDO (); break;
		   case S:   reglageKB.onpressRE (); break;
		   case D:   reglageKB.onpressMI (); break;
		   case F:   reglageKB.onpressFA (); break;
		   case G:   reglageKB.onpressSOL (); break;
		   case H:   reglageKB.onpressLA(); break;
		   case J:   reglageKB.onpressSI (); break;
		   case K:   reglageKB.onpressDO2 (); break;

		   
		   case Z:   reglageKB.onpressDOd (); break;
		   case E:   reglageKB.onpressREd (); break;
		   case T:   reglageKB.onpressFAd (); break;
		   case Y:   reglageKB.onpressSOLd (); break;
		   case U:   reglageKB.onpressLAd (); break;

		   case W:   reglageKB.onpressOctaveUP (); break;
		   case X:   reglageKB.onpressOctaveDOWN (); break;
            }		
		}
		else if ( event.getEventType() == KeyEvent.KEY_RELEASED) {
			   switch (event.getCode()) {
			   case Q:   reglageKB.onreleaseDO (); break;
			   case S:   reglageKB.onreleaseRE (); break;
			   case D:   reglageKB.onreleaseMI (); break;
			   case F:   reglageKB.onreleaseFA (); break;
			   case G:   reglageKB.onreleaseSOL (); break;
			   case H:   reglageKB.onreleaseLA(); break;
			   case J:   reglageKB.onreleaseSI (); break;
			   case K:   reglageKB.onreleaseDO2 (); break;

			   
			   case Z:   reglageKB.onreleaseDOd (); break;
			   case E:   reglageKB.onreleaseREd (); break;
			   case T:   reglageKB.onreleaseFAd (); break;
			   case Y:   reglageKB.onreleaseSOLd (); break;
			   case U:   reglageKB.onreleaseLAd (); break;

			   case W:   reglageKB.onreleaseOctaveUP (); break;
			   case X:   reglageKB.onreleaseOctaveDOWN (); break;
	            }
			
		}
	}

}
 