package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IHMTestSprint2 extends ApplicationTest {


    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../sample_sprint1.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testSprint2() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcaMenuItem");
        AnchorPane vca = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#egMenuItem");
        AnchorPane eg = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane replicator = lookup("#module-6").query();


        // CONNECTING CABLES


        // la sortie `out` du VCO n°2 est reliée à l’entrée `in` du VCA ;
        clickOn(vco2.lookup("#outPort"));
        clickOn(vca.lookup("#inPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // la sortie `out` du VCO n°1 est reliée à l’entrée `fm` du VCO n°2 ;
        // la sortie `out` du VCO n°1 est reliée à l’entrée `gate`de l’EG ;
        // on passe par le replicator pour sortir la sortie out du VCO à deux endroits !
        clickOn(vco1.lookup("#outPort"));
        clickOn(replicator.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        clickOn(replicator.lookup("#outPort1"));
        clickOn(vco2.lookup("#fmPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        clickOn(replicator.lookup("#outPort2"));
        clickOn(eg.lookup("#gatePort"));

        Line cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");


        // la sortie `out` du VCA est reliée  à l’entrée `in` du module de sortie son.
        clickOn(vca.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        Line cable5 = lookup("#cable-5").query();
        assertNotNull(cable5);
        assertEquals(cable5.getId(), "cable-5");

        // la sortie `out` de l’EG est reliée à l’entrée `am` du VCA.
        clickOn(eg.lookup("#outPort"));
        clickOn(vca.lookup("#amPort"));

        Line cable6 = lookup("#cable-6").query();
        assertNotNull(cable6);
        assertEquals(cable6.getId(), "cable-6");


        // CHANGE SLIDER


        // un VCO n°1 réglé à la fréquence de 1 Hz (comme un LFO) ;
        Slider vco1SliderOctave = (Slider) vco1.lookup("#frequencySlider");
        moveTo(vco1SliderOctave);
        vco1SliderOctave.setValue(-8);

        Slider vco1SliderFin = (Slider) vco1.lookup("#frequencyFineSlider");
        moveTo(vco1SliderFin);
        vco1SliderFin.setValue(-9.4);

        // un VCO n°2 réglé à la fréquence de base de 1 kHz ;
        Slider vco2SliderOctave = (Slider) vco2.lookup("#frequencySlider");
        moveTo(vco2SliderOctave);
        vco2SliderOctave.setValue(1);

        Slider vco2SliderFin = (Slider) vco2.lookup("#frequencyFineSlider");
        moveTo(vco2SliderFin);
        vco2SliderFin.setValue(2.2);

        sleep(2000);

        clickOn("#mute");
    }

}
