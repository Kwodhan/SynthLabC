package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IHMSprint3Test extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @After
    public void dropAll() {
        clickOn("#display").clickOn("#dropAllMenuItem");
        sleep(1000);
    }

    @Test
    public void testSprint3MontageA() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#egMenuItem");
        AnchorPane eg = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcaMenuItem");
        AnchorPane vca = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane replicator = lookup("#module-6").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcfLpMenuItem");
        AnchorPane vcfLp = lookup("#module-7").query();


        // CONNECTING CABLES


        // la sortie `out` du VCO n°1 est reliée à l’entrée `fm` du VCF ;
        // la sortie `out` du VCO n°1 est reliée à l’entrée `gate`de l’EG ;
        // Replicator needed !
        clickOn(vco1.lookup("#outPort"));
        clickOn(replicator.lookup("#inPort"));
        clickOn("RED");

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        clickOn(replicator.lookup("#outPort1"));
        clickOn(vcfLp.lookup("#fmPort"));
        clickOn("GOLD");

        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        clickOn(replicator.lookup("#outPort2"));
        clickOn(eg.lookup("#gatePort"));
        clickOn("RED");

        CubicCurve cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        // la sortie `out` du VCO n°2 est reliée à l’entrée `in` du VCF ;
        clickOn(vco2.lookup("#outPort"));
        clickOn(vcfLp.lookup("#inPort"));
        clickOn("GOLD");

        CubicCurve cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        // la sortie `out` du VCF  est reliée à l’entrée `in` du VCA ;
        clickOn(vcfLp.lookup("#outPort"));
        clickOn(vca.lookup("#inPort"));
        clickOn("RED");

        CubicCurve cable5 = lookup("#cable-5").query();
        assertNotNull(cable5);
        assertEquals(cable5.getId(), "cable-5");

        // la sortie `out` du VCA est reliée  à l’entrée `in` du module de sortie son ;
        clickOn(vca.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        clickOn("GOLD");

        CubicCurve cable6 = lookup("#cable-6").query();
        assertNotNull(cable6);

        assertEquals(cable6.getId(), "cable-6");

        // la sortie `out` de l’EG est reliée à l’entrée `am` du VCA.
        clickOn(eg.lookup("#outPort"));
        clickOn(vca.lookup("#amPort"));
        clickOn("RED");

        CubicCurve cable7 = lookup("#cable-7").query();
        assertNotNull(cable7);
        assertEquals(cable7.getId(), "cable-7");


        // CHANGE SLIDER


        // un VCO n°1 réglé à la fréquence de 0,5 Hz (comme un LFO) ;
        Slider vco1SliderOctave = (Slider) vco1.lookup("#frequencySlider");
        moveTo(vco1SliderOctave);
        vco1SliderOctave.setValue(-9);

        Slider vco1SliderFin = (Slider) vco1.lookup("#frequencyFineSlider");
        moveTo(vco1SliderFin);
        vco1SliderFin.setValue(-9.5);

        // un VCO n°2 réglé à la fréquence de base de 1 kHz, signal dent de scie ;
        Slider vco2SliderOctave = (Slider) vco2.lookup("#frequencySlider");
        moveTo(vco2SliderOctave);
        vco2SliderOctave.setValue(1);

        Slider vco2SliderFin = (Slider) vco2.lookup("#frequencyFineSlider");
        moveTo(vco2SliderFin);
        vco2SliderFin.setValue(2.23);

        clickOn(vco2.lookup("#sawRadio"));

        // un VCF LP 24 dB/octave réglé à la fréquence de 1 kHz
        Slider vcfFrequency = (Slider) vcfLp.lookup("#frequencySlider");
        moveTo(vcfFrequency);
        vcfFrequency.setValue(1000);

        sleep(5000);

        clickOn("#mute");
    }

    @Test
    public void testSprint3MontageB() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane rep = lookup("#module-4").query();


        // CONNECTING CABLES


        // la sortie `out` du VCO n°1 est reliée à l’entrée `fm` du VCO n°2 ;
        clickOn(vco1.lookup("#outPort"));
        clickOn(vco2.lookup("#fmPort"));
        clickOn("RED");

        // la sortie `out` du VCO n°2 est reliée à l’entrée `in` du réplicateur ;
        clickOn(vco2.lookup("#outPort"));
        clickOn(rep.lookup("#inPort"));
        clickOn("RED");

        // la sortie `out1` du réplicateur  est reliée à l’entrée `fm` du VCO n° 1 ;
        clickOn(rep.lookup("#outPort1"));
        clickOn(vco1.lookup("#fmPort"));
        clickOn("GOLD");

        // la sortie `out2` du réplicateur est reliée  à l’entrée `in` du module de sortie son.
        clickOn(rep.lookup("#outPort2"));
        clickOn(output.lookup("#inPort"));
        clickOn("RED");


        // CHANGE SLIDER


        // un VCO n°1 réglé à la fréquence de 1,5 kHz, signal triangle ;
        Slider vco1SliderOctave = (Slider) vco1.lookup("#frequencySlider");
        moveTo(vco1SliderOctave);
        vco1SliderOctave.setValue(2);

        Slider vco1SliderFin = (Slider) vco1.lookup("#frequencyFineSlider");
        moveTo(vco1SliderFin);
        vco1SliderFin.setValue(-2.765);

        clickOn(vco1.lookup("#triangleRadio"));

        // un VCO n°2 réglé à la fréquence de base de 1 kHz, signal triangle ;
        Slider vco2SliderOctave = (Slider) vco2.lookup("#frequencySlider");
        moveTo(vco2SliderOctave);
        vco2SliderOctave.setValue(1);

        Slider vco2SliderFin = (Slider) vco2.lookup("#frequencyFineSlider");
        moveTo(vco2SliderFin);
        vco2SliderFin.setValue(2.23);

        clickOn(vco2.lookup("#triangleRadio"));


        sleep(5000);

        clickOn("#mute");
    }

}
