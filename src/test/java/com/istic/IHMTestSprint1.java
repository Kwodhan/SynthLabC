package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

public class IHMTestSprint1 extends ApplicationTest {



    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../sample_sprint1.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Before
    public void testConnectVCOwithOuput() {
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");

        clickOn("#outPort");
        clickOn("#inPort");


    }

    @Test
    public void testSprint1() {
        // Mute
        sleep(2000);
        clickOn("#mute");
        sleep(2000);
        clickOn("#mute");
        sleep(2000);

        // Type de signal
//        clickOn("#triangleRadio");
//        sleep(2000);
//        clickOn("#sawRadio");
//        sleep(1000);
//        clickOn("#squareRadio");
//        sleep(1000);

        // Octave slider
        moveTo("#frequencySlider");
        Slider octave = lookup("#frequencySlider").query();
        octave.setValue(3);
        sleep(1000);
        octave.setValue(-3);
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

        // Attenuation
        moveTo("#attenuationSlider");
        Slider attenuation = lookup("#attenuationSlider").query();
        attenuation.setValue(-6);
        sleep(1000);
        attenuation.setValue(-12);
        sleep(1000);
        attenuation.setValue(0);

        // end
        sleep(2000);
    }

}
