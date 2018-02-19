package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

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
        clickOn("#display").clickOn("#woodMenuItem");
        clickOn("#display").clickOn("#metalMenuItem");
        clickOn("#display").clickOn("#defaultMenuItem");
    }


}
