package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");
        
        clickOn(vco1.lookup("#fmPort"));
        clickOn(seq.lookup("#outPort"));
        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");
        
        clickOn(vco2.lookup("#outPort"));
        clickOn(seq.lookup("#gatePort"));
        CubicCurve cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

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

	        }      
        	Thread.sleep(1000);
        }
        
    }

}
