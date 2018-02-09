package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IHMTestDragDrop extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

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

        // box 8 y'a un anchor pane avec l'id
        assertNotNull(box4.lookup("#module-1"));
        assertEquals(box4.getChildren().get(0).getId(), "module-1");
    }

    @Test
    public void testDragDropWithCable() {
        AnchorPane output = lookup("#module-1").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn(output.lookup("#inPort"));
        clickOn(vco1.lookup("#outPort"));

        Line cable1 = lookup("#cable-1").query();
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
    }



}
