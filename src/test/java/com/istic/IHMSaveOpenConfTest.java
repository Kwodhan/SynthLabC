package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Ignore;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.io.File;
import java.security.Key;

import static org.junit.Assert.*;

public class IHMSaveOpenConfTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Save d'une configuration simple
     * 3 modules, 2 cables, 2 couleurs de cable
     * enregistre la conf, dropAll et réappliquer la config
     */
    @Test
    public void testSaveOpenConf() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-3").query();


        // CONNECTING CABLGES
        clickOn(vco1.lookup("#outPort"));
        clickOn(oscillo.lookup("#inPort"));

        clickOn("#cableColorMenu").clickOn("#cableColorGoldMenuItem");

        clickOn(oscillo.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));


        // Save conf
        clickOn("#file").clickOn("#saveConfigMenuItem");
        press(KeyCode.ENTER); release(KeyCode.ENTER);
        press(KeyCode.ENTER); release(KeyCode.ENTER);

        // Drop
        sleep(1000);
        clickOn("#display").clickOn("#dropAllMenuItem");
        sleep(1000);

        // Open conf
        clickOn("#file").clickOn("#openConfigMenuItem");
        press(KeyCode.C); release(KeyCode.C);
        press(KeyCode.O); release(KeyCode.O);
        press(KeyCode.N); release(KeyCode.N);
        press(KeyCode.F); release(KeyCode.F);
        press(KeyCode.ENTER);


        // Verify



    }


}
