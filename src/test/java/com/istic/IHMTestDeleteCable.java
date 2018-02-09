package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IHMTestDeleteCable extends ApplicationTest {

    Stage stage;

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
        this.stage=stage;
    }



    @Test
    public void testDecoCableOutVCO() {
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");

        clickOn("#outPort");
        clickOn("#inPort");



        // Octave slider
        moveTo("#frequencySlider");
        Slider octave = lookup("#frequencySlider").query();
        octave.setValue(3);
        sleep(1000);
        octave.setValue(-3);
        sleep(1000);
        octave.setValue(0);
        sleep(2000);


        clickOn("#sawRadio");

        // Fin slider
        moveTo("#frequencyFineSlider");
        Slider fin = lookup("#frequencyFineSlider").query();
        fin.setValue(-7);
        sleep(1000);
        fin.setValue(2);
        sleep(1000);


        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // deconnexion
        clickOn("#cable-1");

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        sleep(2000);

        clickOn("#outPort");
        clickOn("#inPort");
        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        sleep(2000);

        clickOn("#mute");

    }

    @Test
    public void testDecoCableOutOscilloRepVCo() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane repli = lookup("#module-4").query();

        // out vco --> in rep
        clickOn(vco.lookup("#outPort"));
        clickOn(repli.lookup("#inPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out1 rep --> in oscillo
        clickOn(repli.lookup("#outPort1"));
        clickOn(oscillo.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        // out oscillo --> in oscillo
        clickOn(oscillo.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        sleep(2000);

        // Octave slider
        moveTo("#frequencySlider");
        Slider octave = lookup("#frequencySlider").query();
        octave.setValue(1);
        sleep(1000);
        octave.setValue(-2);
        sleep(1000);
        octave.setValue(0);
        sleep(2000);

        // Fin slider
        moveTo("#frequencyFineSlider");
        Slider fin = lookup("#frequencyFineSlider").query();
        fin.setValue(-7);
        sleep(1000);
        fin.setValue(2);
        sleep(1000);


        clickOn("#cable-1");
        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        sleep(1000);
        clickOn("#mute");


    }

    @Test
    public void testDecoCableOutOscilloVCOEGVCO() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#egMenuItem");
        AnchorPane eg = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-5").query();

        // out vco1 --> in eg
        clickOn(vco1.lookup("#outPort"));
        clickOn(eg.lookup("#gatePort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out eg --> fm vco2
        clickOn(eg.lookup("#outPort"));
        clickOn(vco2.lookup("#fmPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        // out vco2 --> in oscillo
        clickOn(vco2.lookup("#outPort"));
        clickOn(oscillo.lookup("#inPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        // out oscillo --> in sortie
        clickOn(oscillo.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        Line cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        sleep(2000);

        // VCO1 à 1 hz
        Slider vco1SliderOctave = (Slider) vco1.lookup("#frequencySlider");
        moveTo(vco1SliderOctave);
        vco1SliderOctave.setValue(-8);

        Slider vco1SliderFin = (Slider) vco1.lookup("#frequencyFineSlider");
        moveTo(vco1SliderFin);
        vco1SliderFin.setValue(-9.4);

        // VCO to SawTooth
        clickOn(vco2.lookup("#sawRadio"));


        sleep(1000);

        Slider egAttack = (Slider) eg.lookup("#attackSlider");
        moveTo(egAttack);
        egAttack.setValue(10);


        sleep(2000);

        Slider egRelease = (Slider) eg.lookup("#releaseSlider");
        moveTo(egRelease);
        egRelease.setValue(10);

        sleep(2000);

        Slider egSustain = (Slider) eg.lookup("#sustainSlider");
        moveTo(egSustain);
        egSustain.setValue(8);

        sleep(2000);

        Slider egDecay = (Slider) eg.lookup("#decaySlider");
        moveTo(egDecay);
        egDecay.setValue(10);

        sleep(2000);

        clickOn("#cable-2");
        cable2 = lookup("#cable-2").query();
        assertNull(cable2);

        sleep(2000);

        clickOn("#mute");


    }


}
