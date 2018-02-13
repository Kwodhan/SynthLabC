package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IHMDragDropTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Déplace le module d'une box à une autre
     * et vérifie que le module à bien changé de place
     */
    @Test
    public void testDragDropSimple() {
        AnchorPane output = lookup("#module-1").query();
        StackPane box1 = lookup("#box1").query();
        StackPane box4 = lookup("#box4").query();

        // Drag&Drop
        drag(output, MouseButton.PRIMARY);
        dropTo(box4);

        // box1 should be empty
        assertEquals(new ArrayList(), box1.getChildren());

        // box 4 y'a un anchor pane avec l'id
        assertNotNull(box4.lookup("#module-1"));
        assertEquals(box4.getChildren().get(0).getId(), "module-1");

        clickOn("#mute");
    }

    /**
     * Ajoute deux modules, déplace un module
     * déplace le second module sur la même box
     * et vérifie que le second retourne bien à sa place
     * que la box est déjà occupée
     */
    @Test
    public void testDragDropOnBoxAlreadyUsed() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscillo = lookup("#module-2").query();

        StackPane box1 = lookup("#box1").query();
        StackPane box2 = lookup("#box2").query();
        StackPane box7 = lookup("#box7").query();

        // Drag&Drop output to box 7
        drag(output, MouseButton.PRIMARY);
        dropTo(box7);

        // box1 should be empty now
        assertEquals(new ArrayList(), box1.getChildren());

        // box 7 y'a un anchor pane avec l'id
        assertNotNull(box7.lookup("#module-1"));
        assertEquals(box7.getChildren().get(0).getId(), "module-1");

        // Try to Drag&Drop oscillo to box 7 too, should fail
        drag(oscillo, MouseButton.PRIMARY);
        dropTo(box7);

        // Oscillo should be always in box 2
        assertNotNull(box2.lookup("#module-2"));
        assertEquals(box2.getChildren().get(0).getId(), "module-2");

        // and box 7 should always contain output module-1
        assertNotNull(box7.lookup("#module-1"));
        assertEquals(box7.getChildren().get(0).getId(), "module-1");

        clickOn("#mute");
    }

    /**
     * Ajoute deux modules, connecte 1 cable
     * Bouge un des modules
     * Vérifie que un côté du cable seulement à bougé
     */
    @Test
    public void testDragDropWithCable() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn(output.lookup("#inPort"));
        clickOn(vco1.lookup("#outPort"));
        clickOn("MEDIUMAQUAMARINE");

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);

        double oldStartX = cable1.getStartX();
        double oldStartY = cable1.getStartY();
        double oldEndX = cable1.getEndX();
        double oldEndY = cable1.getEndY();

        StackPane box8 = lookup("#box8").query();
        drag(vco1.localToScene(vco1.getLayoutBounds()), MouseButton.PRIMARY);
        dropTo(box8);

        assertEquals(oldStartX, cable1.getStartX(), 0.01);
        assertEquals(oldStartY, cable1.getStartY(), 0.01);
        assertNotEquals(oldEndX, cable1.getEndX(), 0.01);
        assertNotEquals(oldEndY, cable1.getEndY(), 0.01);

        clickOn("#mute");
    }

    /**
     * Ajoute deux modules, connecte 1 cable
     * Bouge les deux modules
     * Vérifie que les deux côtés du cable ont bougé
     */
    @Test
    public void testDragDropWithCableComplex() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn(output.lookup("#inPort"));
        clickOn(vco1.lookup("#outPort"));
        clickOn("MEDIUMAQUAMARINE");

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);

        double oldStartX = cable1.getStartX();
        double oldStartY = cable1.getStartY();
        double oldEndX = cable1.getEndX();
        double oldEndY = cable1.getEndY();

        StackPane box8 = lookup("#box8").query();
        drag(vco1.localToScene(vco1.getLayoutBounds()), MouseButton.PRIMARY);
        dropTo(box8);

        StackPane box10 = lookup("#box10").query();
        drag(output.localToScene(output.getLayoutBounds()), MouseButton.PRIMARY);
        dropTo(box10);

        assertNotEquals(oldStartX, cable1.getStartX(), 0.01);
        assertNotEquals(oldStartY, cable1.getStartY(), 0.01);
        assertNotEquals(oldEndX, cable1.getEndX(), 0.01);
        assertNotEquals(oldEndY, cable1.getEndY(), 0.01);

        clickOn("#mute");
    }


    /**
     * Déplace le module Oscillo d'une box à une autre
     * et vérifie que le module à bien changé de place
     */
    @Test
    public void testDragDropOscilloSimple() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscilloscope = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-3").query();

        clickOn(oscilloscope.lookup("#inPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn("MEDIUMAQUAMARINE");

        StackPane box2 = lookup("#box2").query();
        StackPane box4 = lookup("#box4").query();

        // Drag&Drop
        drag(oscilloscope, MouseButton.PRIMARY);
        dropTo(box4);

        // box2 should be empty
        assertEquals(new ArrayList(), box2.getChildren());

        // box 4 y'a un anchor pane avec l'id
        assertNotNull(box4.lookup("#module-2"));
        assertEquals(box4.getChildren().get(0).getId(), "module-2");


        clickOn(vco.lookup("#squareRadio"));


        // Drag&Drop
        drag(oscilloscope, MouseButton.PRIMARY);
        dropTo(box2);

        // box2 should be empty
        assertEquals(new ArrayList(), box4.getChildren());

        // box 4 y'a un anchor pane avec l'id
        assertNotNull(box2.lookup("#module-2"));
        assertEquals(box2.getChildren().get(0).getId(), "module-2");

        clickOn(vco.lookup("#sawRadio"));

        clickOn("#mute");
    }

    /**
     * Essayer de Drag & Drop un module en mode "creation de cable"
     */
    @Test
    public void testDragDropWithCableStartConnect() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#oscilloscopeMenuItem");
        AnchorPane oscilloscope = lookup("#module-3").query();

        StackPane box1 = lookup("#box1").query();
        StackPane box2 = lookup("#box2").query();
        StackPane box3 = lookup("#box3").query();
        StackPane box9 = lookup("#box9").query();

        // must not drag&drop when cable creating is start
        clickOn(vco.lookup("#outPort"));
        drag(oscilloscope, MouseButton.PRIMARY);
        dropTo(box9);

        assertEquals(box1.getChildren().get(0).getId(), "module-1");
        assertEquals(box2.getChildren().get(0).getId(), "module-2");
        assertEquals(box3.getChildren().get(0).getId(), "module-3");
        assertEquals(new ArrayList(), box9.getChildren());

        clickOn(vco.lookup("#fmPort"));
        assertNull(lookup("#cable-1").query());

        sleep(2000);
    }

}
