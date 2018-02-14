package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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

        clickOn(vco2.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort2"));

        clickOn(vco3.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort3"));

        clickOn(vco4.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort4"));

        clickOn(mixer.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        sleep(1000);

        Slider mixerSlider1 = (Slider) mixer.lookup("#amplitudeSlider1");
        moveTo(mixerSlider1);
        mixerSlider1.setValue(0.5);

        sleep(1000);

        Slider mixerSlider2 = (Slider) mixer.lookup("#amplitudeSlider2");
        moveTo(mixerSlider2);
        mixerSlider2.setValue(0.2);

        sleep(1000);

        Slider mixerSlider3 = (Slider) mixer.lookup("#amplitudeSlider3");
        moveTo(mixerSlider3);
        mixerSlider3.setValue(0.8);

        sleep(1000);

        Slider mixerSlider4 = (Slider) mixer.lookup("#amplitudeSlider4");
        moveTo(mixerSlider4);
        mixerSlider4.setValue(0.5);

        sleep(2000);

        clickOn("#mute");


    }

}
