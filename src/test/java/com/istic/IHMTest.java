package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.awt.*;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

public class IHMTest extends ApplicationTest {

    private final App app = new App();

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
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#outputMenuItem");

        clickOn("#outPort");
        clickOn("#inPort");

        clickOn("#connectButton");
    }

    @Test
    public void testMute() {
        clickOn("#mute");
        sleep(2000);
        clickOn("#mute");
        sleep(3000);
    }

    @Test
    public void testWaveChanged() {
        clickOn("#triangleRadio");
        sleep(3000);
    }

    @Test
    public void testSliderChanged() {

    }


}
