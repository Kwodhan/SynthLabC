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

public class IHMMixerTest extends ApplicationTest {
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
    public void connectionFullVCOToMixer() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco3 = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco4 = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#mixerMenuItem");
        AnchorPane mixer = lookup("#module-6").query();

        // CONNECTING CABLES
        clickOn(vco1.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort1"));
        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");


        clickOn(vco2.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort2"));
        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        clickOn(vco3.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort3"));
        CubicCurve cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(cable3.getId(), "cable-3");

        clickOn(vco4.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort4"));
        CubicCurve cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getId(), "cable-4");

        clickOn(mixer.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        CubicCurve cable5 = lookup("#cable-5").query();
        assertNotNull(cable5);
        assertEquals(cable5.getId(), "cable-5");

        Slider mixerSlider1 = (Slider) mixer.lookup("#amplitudeSlider1");
        moveTo(mixerSlider1);
        mixerSlider1.setValue(0.5);

        Slider mixerSlider2 = (Slider) mixer.lookup("#amplitudeSlider2");
        moveTo(mixerSlider2);
        mixerSlider2.setValue(0.2);

        Slider mixerSlider3 = (Slider) mixer.lookup("#amplitudeSlider3");
        moveTo(mixerSlider3);
        mixerSlider3.setValue(0.8);

        Slider mixerSlider4 = (Slider) mixer.lookup("#amplitudeSlider4");
        moveTo(mixerSlider4);
        mixerSlider4.setValue(0.5);

        sleep(2000);

        clickOn("#mute");


    }

}
