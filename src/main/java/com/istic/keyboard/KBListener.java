package com.istic.keyboard;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode; 

public class KBListener implements EventHandler<KeyEvent> {

	@Override
	public void handle(KeyEvent event) {
		   switch (event.getCode()) {
           case UP:     System.out.println("Hello World"); break;
           case DOWN:   System.out.println("Hello World");; break;
           case LEFT:   System.out.println("Hello World");break;
           case RIGHT:  System.out.println("Hello World"); break;
           case SHIFT:  System.out.println("Hello World"); break;
       }		
	}

}
 