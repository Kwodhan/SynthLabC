package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IHMDropAllTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Add 12 modules
     * verify box are not empty first and empty after drop
     */
    @Test
    public void testDropAllSimple() {
        StackPane panes[] = new StackPane[13];

        // add box
        for (int i = 1; i <= 12; i++) {
            panes[i] = lookup("#box" + i).query();
        }

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

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco3 = lookup("#module-8").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco4 = lookup("#module-9").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco5 = lookup("#module-10").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#whiteNoiseMenuItem");
        AnchorPane whiteNoise = lookup("#module-11").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-12").query();

        // check all box are not empty
        for (int i = 1; i <= 12; i++) {
            assertNotEquals(new ArrayList(), panes[i].getChildren());
        }

        // DROP !
        clickOn("#display").clickOn("#dropAllMenuItem");

        // check all box are empty
        for (int i = 1; i <= 12; i++) {
            assertEquals(new ArrayList(), panes[i].getChildren());
        }
    }

    /**
     * Add modules and cables
     * drop !
     * verify all are dropped !
     */
    @Test
    public void testDropWithCables() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-3").query();

        StackPane box1 = lookup("#box1").query();
        StackPane box2 = lookup("#box2").query();
        StackPane box3 = lookup("#box3").query();


        // CONNECTING CABLES


        clickOn(vco1.lookup("#outPort"));
        clickOn(oscillo.lookup("#inPort"));
        clickOn("RED");

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(cable1.getId(), "cable-1");

        clickOn(oscillo.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        clickOn("GOLD");

        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(cable2.getId(), "cable-2");

        assertNotEquals(new ArrayList(), box1.getChildren());
        assertNotEquals(new ArrayList(), box2.getChildren());
        assertNotEquals(new ArrayList(), box3.getChildren());

        // DROP !
        clickOn("#display").clickOn("#dropAllMenuItem");

        sleep(200);

        assertEquals(new ArrayList(), box1.getChildren());
        assertEquals(new ArrayList(), box2.getChildren());
        assertEquals(new ArrayList(), box3.getChildren());

        assertNull(lookup("#cable-1").query());
        assertNull(lookup("#cable-2").query());
    }

    /**
     * Supprimer un module à la mano avant de faire un dropAll
     */
    @Test
    public void testDeleteModuleBeforeDropAll() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-3").query();

        StackPane box1 = lookup("#box1").query();
        StackPane box2 = lookup("#box2").query();
        StackPane box3 = lookup("#box3").query();

        clickOn(vco1.lookup("#closeButton"));

        assertNotEquals(new ArrayList(), box1.getChildren());
        assertEquals(new ArrayList(), box2.getChildren());
        assertNotEquals(new ArrayList(), box3.getChildren());

        // DROP !
        clickOn("#display").clickOn("#dropAllMenuItem");

        assertEquals(new ArrayList(), box1.getChildren());
        assertEquals(new ArrayList(), box2.getChildren());
        assertEquals(new ArrayList(), box3.getChildren());
    }

}
