package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;

import static org.junit.Assert.*;

public class IHMRecordTest extends ApplicationTest {

    private String filePath = System.getProperty("user.home") + "/mySound";

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Test simple, enregistre le son d'un vco
     * pendant 5s dans un fichier
     * au format WAV
     */
    @Test
    public void testRecordSimpleWAV() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        // CONNECTING CABLES

        clickOn(vco1.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        // START RECORDING for 5s
        clickOn(output.lookup("#recordButton"));
        press(KeyCode.ENTER);
        sleep(5000);

        // STOP RECORDING
        clickOn(output.lookup("#recordButton"));

        File file = new File(filePath);
        assertNotNull(file);
        assertNotEquals(0, file.getTotalSpace());

        // DELETE FILE
        assertTrue(file.delete());
        assertFalse(file.exists());

        clickOn(output.lookup("#closeButton"));
        sleep(1000);
    }

    /**
     * Test simple, enregistre le son d'un vco
     * pendant 5s dans un fichier
     * au format MP3
     */

    @Test
    @Ignore
    public void testRecordSimpleMP3() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        // CONNECTING CABLES

        clickOn(vco1.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        // START RECORDING for 5s
        clickOn(output.lookup("#recordButton"));

        clickOn(1000, 680);
        clickOn(1000, 700);

        press(KeyCode.ENTER);
        sleep(5000);

        // STOP RECORDING
        clickOn(output.lookup("#recordButton"));

        File file = new File(filePath);
        assertNotNull(file);
        assertNotEquals(0, file.getTotalSpace());

        // DELETE FILE
        assertTrue(file.delete());
        assertFalse(file.exists());

        clickOn(output.lookup("#closeButton"));
        sleep(1000);
    }

    /**
     * ECHAP la fenetre de sauvegarde du son
     * doit annuler la sauvegarde
     */
    @Test
    public void testRecordWithEscape() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        // CONNECTING CABLES

        clickOn(vco1.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        // VERIFY file doesn't exist yet
        assertFalse(new File(filePath).exists());

        // START RECORDING for 5s
        clickOn(output.lookup("#recordButton"));
        press(KeyCode.ESCAPE);

        sleep(2000);

        // VERIFY file doesn't exist too
        assertFalse(new File(filePath).exists());

        clickOn(output.lookup("#closeButton"));
        sleep(1000);
    }
}
