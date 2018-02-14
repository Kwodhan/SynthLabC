package com.istic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import javafx.scene.control.Slider;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.service.query.NodeQuery;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class IHMSequenceurTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Déplace le module d'une box à une autre
     * et vérifie que le module à bien changé de place
     * @throws InterruptedException 
     */
    @Test
    public void testSequenseurSimple() throws InterruptedException {
    	clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#sequencerMenuItem");
    	clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
    	clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane output = lookup("#module-1").query();
        AnchorPane seq = lookup("#module-2").query();
        AnchorPane vco1 = lookup("#module-3").query();
        AnchorPane vco2 = lookup("#module-4").query();

        clickOn(output.lookup("#inPort"));
        clickOn(vco1.lookup("#outPort"));
        
        clickOn(vco1.lookup("#fmPort"));
        clickOn(seq.lookup("#out"));
        
        clickOn(vco2.lookup("#outPort"));
        clickOn(seq.lookup("#gate"));
        Slider frequencySlider;
        Slider slider;
        slider = (Slider) vco2.lookup("#frequencySlider");
        slider.setValue(-9);
        slider = (Slider) vco2.lookup("#frequencyFineSlider");
        slider.setValue(-9);
        for(int j=0;j < 10;j++) {
	        for(int i = 1; i <=8; i++){
	        	slider = lookup("#sliderSeq"+i).query();
	        	double value = Math.random()*(-1+(i%2)*2);
	        	slider.setValue(value);
	        	System.out.println(value);
	        }      
        	Thread.sleep(1000);
        }
        
    }

}
