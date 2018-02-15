package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

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
     * Puis drag le vco et vérifie it's OK
     */
    @Test
    public void testSaveOpenConf() {
        StackPane box1 = lookup("#box1").query();
        StackPane box2 = lookup("#box2").query();
        StackPane box3 = lookup("#box3").query();
        StackPane box4 = lookup("#box4").query();
        StackPane box5 = lookup("#box5").query();

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

        // check all is OK
        assertNotNull(output);
        assertNotNull(vco1);
        assertNotNull(oscillo);

        assertNotEquals(new ArrayList(), box1.getChildren());
        assertNotEquals(new ArrayList(), box2.getChildren());
        assertNotEquals(new ArrayList(), box3.getChildren());

        assertNotNull(lookup("#cable-1").query());
        assertNotNull(lookup("#cable-2").query());
        assertNull(lookup("#cable-3").query());
        assertNull(lookup("#cable-4").query());

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

        sleep(500);


        // VERIFY

        assertNotNull(output);
        assertNotNull(vco1);
        assertNotNull(oscillo);

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        CubicCurve cable2 = lookup("#cable-2").query();
        assertNull(cable2);

        CubicCurve cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);

        CubicCurve cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);

        assertNotEquals(new ArrayList(), box1.getChildren());
        assertNotEquals(new ArrayList(), box2.getChildren());
        assertNotEquals(new ArrayList(), box3.getChildren());
        assertEquals(new ArrayList(), box4.getChildren());
        assertEquals(new ArrayList(), box5.getChildren());


        // Drag&Drop + VERIFY


//        drag(vco1, MouseButton.PRIMARY);
//        dropTo(box5);
//
//        assertNotEquals(new ArrayList(), box1.getChildren());
//        assertEquals(new ArrayList(), box2.getChildren());
//        assertNotEquals(new ArrayList(), box3.getChildren());
//        assertEquals(new ArrayList(), box4.getChildren());
//        assertNotEquals(new ArrayList(), box5.getChildren());

        clickOn("#mute");
        sleep(1000);
    }


}
