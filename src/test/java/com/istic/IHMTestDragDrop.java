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

import static org.junit.Assert.assertNull;

public class IHMTestDragDrop extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testDragDrop() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        StackPane box4 = lookup("#box4").query();
        StackPane box6 = lookup("#box6").query();
        StackPane box12 = lookup("#box12").query();

        drag(output, MouseButton.PRIMARY);
        dropTo(box4);

        drag(output, MouseButton.PRIMARY);
        dropTo(box12);

        drag(output, MouseButton.PRIMARY);
        dropTo(box6);

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();
    }



}
