package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class IHMKeyboardTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testKeyboard() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#keyBoardMenuItem");
        AnchorPane keyboard = lookup("#module-3").query();


        // CONNECTING CABLE


        clickOn(keyboard.lookup("#outPort"));
        clickOn(vco.lookup("#fmPort"));

        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));


        type(KeyCode.Q);
        sleep(200);
        type(KeyCode.S);
        sleep(200);
        type(KeyCode.D);
        sleep(200);
        type(KeyCode.F);
        sleep(200);
        type(KeyCode.G);
        sleep(200);
        type(KeyCode.H);
        sleep(200);
        type(KeyCode.J);
        sleep(200);
        type(KeyCode.K);

        // Octave change
        type(KeyCode.X);

        type(KeyCode.G);
        sleep(100);
        type(KeyCode.F);
        sleep(400);
        type(KeyCode.H);
        sleep(300);
        type(KeyCode.J);
        sleep(100);
        type(KeyCode.F);
        sleep(400);
        type(KeyCode.Q);
        sleep(200);
        type(KeyCode.S);
        sleep(300);
        type(KeyCode.K);

        // Octave change
        type(KeyCode.W);
        type(KeyCode.W);

        type(KeyCode.G);
        sleep(300);
        type(KeyCode.S);
        sleep(100);
        type(KeyCode.F);
        sleep(200);
        type(KeyCode.J);
        sleep(500);
        type(KeyCode.F);
        sleep(300);
        type(KeyCode.Q);
        sleep(100);
        type(KeyCode.S);
        sleep(200);
        type(KeyCode.K);

        // Octave change
        type(KeyCode.X);

        sleep(200);
        type(KeyCode.Z);
        sleep(500);
        type(KeyCode.T);
        sleep(300);
        type(KeyCode.U);
        sleep(100);
        type(KeyCode.E);
        sleep(300);
        type(KeyCode.Y);


        // Check

        StackPane box1 = lookup("#box1").query();
        assertNotEquals(new ArrayList(), box1.getChildren());

        StackPane box2 = lookup("#box2").query();
        assertNotEquals(new ArrayList(), box2.getChildren());

        StackPane box3 = lookup("#box3").query();
        assertNotEquals(new ArrayList(), box3.getChildren());


        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(Color.BLUEVIOLET, cable1.getStroke());

        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(Color.BLUEVIOLET, cable2.getStroke());


        sleep(2000);
        clickOn("#mute");
    }

}
