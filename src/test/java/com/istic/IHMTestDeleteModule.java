package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class IHMTestDeleteModule extends ApplicationTest {

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
    public void testDeleteVCO() {

        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();


        // out vco2 --> fm vco1
        clickOn(vco2.lookup("#outPort"));
        clickOn(vco1.lookup("#fmPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out vco1 --> in output
        clickOn(vco1.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

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




        clickOn(vco1.lookup("#closeButton"));

        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        cable2 = lookup("#cable-2").query();
        assertNull(cable2);

        // verif module
        vco2 = lookup("#module-3").query();
        assertNotNull(vco2);

        vco1 = lookup("#module-2").query();
        assertNull(vco1);

        output = lookup("#module-1").query();
        assertNotNull(output);

        sleep(2000);



        clickOn("#mute");

    }

    @Test
    public void testDeleteRep() {

        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane repli = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc1 = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc2 = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc3 = lookup("#module-6").query();


        // out vco --> in rep
        clickOn(vco.lookup("#outPort"));
        clickOn(repli.lookup("#inPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out1 rep --> in osc1
        clickOn(repli.lookup("#outPort1"));
        clickOn(osc1.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");


        // out2 rep --> in osc2
        clickOn(repli.lookup("#outPort2"));
        clickOn(osc2.lookup("#inPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        // out3 rep --> in osc3
        clickOn(repli.lookup("#outPort3"));
        clickOn(osc3.lookup("#inPort"));

        Line cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        clickOn(repli.lookup("#closeButton"));
        // verif module
        output = lookup("#module-1").query();
        assertNotNull(output);

        vco = lookup("#module-2").query();
        assertNotNull(vco);

        repli = lookup("#module-3").query();
        assertNull(repli);

        osc1 = lookup("#module-4").query();
        assertNotNull(osc1);

        osc2 = lookup("#module-5").query();
        assertNotNull(osc2);

        osc3 = lookup("#module-6").query();
        assertNotNull(osc3);
        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNull(cable1);
        cable2 = lookup("#cable-2").query();
        assertNull(cable2);
        cable3 = lookup("#cable-3").query();
        assertNull(cable3);
        cable4 = lookup("#cable-4").query();
        assertNull(cable4);

        clickOn("#mute");

    }

    @Test
    public void testDeleteOut() {

        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane repli = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc1 = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc2 = lookup("#module-5").query();

        // out vco --> in rep
        clickOn(vco.lookup("#outPort"));
        clickOn(repli.lookup("#inPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out1 rep --> in osc1
        clickOn(repli.lookup("#outPort1"));
        clickOn(osc1.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");


        // out2 rep --> in osc2
        clickOn(repli.lookup("#outPort2"));
        clickOn(osc2.lookup("#inPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        // out3 rep --> in output
        clickOn(repli.lookup("#outPort3"));
        clickOn(output.lookup("#inPort"));

        Line cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        clickOn(output.lookup("#closeButton"));
        // verif module
        output = lookup("#module-1").query();
        assertNull(output);

        vco = lookup("#module-2").query();
        assertNotNull(vco);

        repli = lookup("#module-3").query();
        assertNotNull(repli);

        osc1 = lookup("#module-4").query();
        assertNotNull(osc1);

        osc2 = lookup("#module-5").query();
        assertNotNull(osc2);

        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);

        cable4 = lookup("#cable-4").query();
        assertNull(cable4);

    }

    @Test
    public void testDeleteEg() {
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


        sleep(1000);

        Slider egRelease = (Slider) eg.lookup("#releaseSlider");
        moveTo(egRelease);
        egRelease.setValue(10);

        sleep(1000);

        Slider egSustain = (Slider) eg.lookup("#sustainSlider");
        moveTo(egSustain);
        egSustain.setValue(8);

        sleep(1000);

        Slider egDecay = (Slider) eg.lookup("#decaySlider");
        moveTo(egDecay);
        egDecay.setValue(10);

        sleep(1000);

        clickOn(eg.lookup("#closeButton"));
        //verif module
        output = lookup("#module-1").query();
        assertNotNull(output);

        vco1 = lookup("#module-2").query();
        assertNotNull(vco1);

        oscillo = lookup("#module-3").query();
        assertNotNull(oscillo);

        eg = lookup("#module-4").query();
        assertNull(eg);

        vco2 = lookup("#module-5").query();
        assertNotNull(vco2);

        //verif cable
        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        cable2 = lookup("#cable-2").query();
        assertNull(cable2);

        cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);

        cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);

        sleep(2000);

        clickOn("#mute");

    }

    @Test
    public void testDeleteVCA() {
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

        clickOn(vca.lookup("#closeButton"));
        // verif module
        output = lookup("#module-1").query();
        assertNotNull(output);

        vco1 = lookup("#module-2").query();
        assertNotNull(vco1);

        vco2 = lookup("#module-3").query();
        assertNotNull(vco2);

        vca = lookup("#module-4").query();
        assertNull(vca);

        eg = lookup("#module-5").query();
        assertNotNull(eg);

        replicator = lookup("#module-6").query();
        assertNotNull(replicator);

        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);

        cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);

        cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);

        cable5 = lookup("#cable-5").query();
        assertNull(cable5);

        cable6 = lookup("#cable-6").query();
        assertNull(cable6);

        clickOn("#mute");
    }

    @Test
    public void testDeleteOsc() {

        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane repli = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc1 = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc2 = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane osc3 = lookup("#module-6").query();


        // out vco --> in rep
        clickOn(vco.lookup("#outPort"));
        clickOn(repli.lookup("#inPort"));

        Line cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        // out1 rep --> in osc1
        clickOn(repli.lookup("#outPort1"));
        clickOn(osc1.lookup("#inPort"));

        Line cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");


        // out2 rep --> in osc2
        clickOn(repli.lookup("#outPort2"));
        clickOn(osc2.lookup("#inPort"));

        Line cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        // out3 rep --> in osc3
        clickOn(repli.lookup("#outPort3"));
        clickOn(osc3.lookup("#inPort"));

        Line cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        // out osc1 --> in output
        clickOn(osc1.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        Line cable5 = lookup("#cable-5").query();
        assertNotNull(cable5);
        assertEquals(cable5.getId(), "cable-5");


        // del osc1
        clickOn(osc1.lookup("#closeButton"));

        // verif module
        output = lookup("#module-1").query();
        assertNotNull(output);

        vco = lookup("#module-2").query();
        assertNotNull(vco);

        repli = lookup("#module-3").query();
        assertNotNull(repli);

        osc1 = lookup("#module-4").query();
        assertNull(osc1);

        osc2 = lookup("#module-5").query();
        assertNotNull(osc2);

        osc3 = lookup("#module-6").query();
        assertNotNull(osc3);
        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        cable2 = lookup("#cable-2").query();
        assertNull(cable2);
        cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        cable5 = lookup("#cable-5").query();
        assertNull(cable5);


        // del osc2
        clickOn(osc2.lookup("#closeButton"));

        // verif module
        output = lookup("#module-1").query();
        assertNotNull(output);

        vco = lookup("#module-2").query();
        assertNotNull(vco);

        repli = lookup("#module-3").query();
        assertNotNull(repli);

        osc1 = lookup("#module-4").query();
        assertNull(osc1);

        osc2 = lookup("#module-5").query();
        assertNull(osc2);

        osc3 = lookup("#module-6").query();
        assertNotNull(osc3);
        // verif cable
        cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        cable2 = lookup("#cable-2").query();
        assertNull(cable2);
        cable3 = lookup("#cable-3").query();
        assertNull(cable3);
        cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        cable5 = lookup("#cable-5").query();
        assertNull(cable5);

        clickOn("#mute");
    }
}
