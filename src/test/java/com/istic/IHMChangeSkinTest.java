package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;

public class IHMChangeSkinTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Update skin
     */
    @Test
    public void testChangeSkin() {
        AnchorPane pane = lookup("#mainPane").query();
        assertEquals(0, pane.getStylesheets().size());

        clickOn("#display").clickOn("#woodMenuItem");
        assertEquals(1, pane.getStylesheets().size());
        assertEquals("/skins/wood.css", pane.getStylesheets().get(0));

        clickOn("#display").clickOn("#darkMenuItem");
        assertEquals(1, pane.getStylesheets().size());
        assertEquals("/skins/dark.css", pane.getStylesheets().get(0));

        clickOn("#display").clickOn("#coralMenuItem");
        assertEquals(1, pane.getStylesheets().size());
        assertEquals("/skins/coral.css", pane.getStylesheets().get(0));

        clickOn("#display").clickOn("#defaultMenuItem");
        assertEquals(0, pane.getStylesheets().size());


        // Check click on already skin used


        clickOn("#display").clickOn("#darkMenuItem");
        assertEquals(1, pane.getStylesheets().size());
        assertEquals("/skins/dark.css", pane.getStylesheets().get(0));

        clickOn("#display").clickOn("#darkMenuItem");
        assertEquals(1, pane.getStylesheets().size());
        assertEquals("/skins/dark.css", pane.getStylesheets().get(0));

        clickOn("#display").clickOn("#defaultMenuItem");
        assertEquals(0, pane.getStylesheets().size());
    }


}
