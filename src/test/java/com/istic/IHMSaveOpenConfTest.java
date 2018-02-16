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
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);

        // Drop
        sleep(1000);
        clickOn("#display").clickOn("#dropAllMenuItem");
        sleep(1000);

        // Open conf
        clickOn("#file").clickOn("#openConfigMenuItem");
        type(KeyCode.C);
        type(KeyCode.O);
        type(KeyCode.N);
        type(KeyCode.F);
        type(KeyCode.ENTER);
        type(KeyCode.DECIMAL);
        type(KeyCode.J);
        type(KeyCode.S);
        type(KeyCode.O);
        type(KeyCode.N);

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
        assertEquals(cable3.getStroke(), Color.BLUEVIOLET);

        CubicCurve cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(cable4.getStroke(), Color.GOLD);


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

    @Test
    public void testSaveOpenConf12Modules() {
        clickOn("#display").clickOn("#dropAllMenuItem");

        // module output était 1, donc on recommence à l'id 2

        type(KeyCode.F1);
        AnchorPane eg = lookup("#module-2").query();
        assertNotNull(eg);

        type(KeyCode.F2);
        AnchorPane mixer = lookup("#module-3").query();
        assertNotNull(mixer);

        type(KeyCode.F3);
        AnchorPane output = lookup("#module-4").query();
        assertNotNull(output);

        type(KeyCode.F4);
        AnchorPane oscillo = lookup("#module-5").query();
        assertNotNull(oscillo);

        type(KeyCode.F5);
        AnchorPane rep = lookup("#module-6").query();
        assertNotNull(rep);

        type(KeyCode.F6);
        AnchorPane seq = lookup("#module-7").query();
        assertNotNull(seq);

        type(KeyCode.F7);
        AnchorPane vca = lookup("#module-8").query();
        assertNotNull(vca);

        type(KeyCode.F8);
        AnchorPane vcfLp = lookup("#module-9").query();
        assertNotNull(vcfLp);

        type(KeyCode.F9);
        AnchorPane vcfHp = lookup("#module-10").query();
        assertNotNull(vcfHp);

        type(KeyCode.F10);
        AnchorPane vco = lookup("#module-11").query();
        assertNotNull(vco);

        type(KeyCode.F11); release(KeyCode.F11);
        AnchorPane whiteNoise = lookup("#module-12").query();
        assertNotNull(whiteNoise);

        type(KeyCode.F12);
        AnchorPane kb = lookup("#module-13").query();
        assertNotNull(kb);

        // Check box not null

        StackPane box1 = lookup("#box1").query();
        assertNotEquals(new ArrayList(), box1.getChildren());

        StackPane box2 = lookup("#box2").query();
        assertNotEquals(new ArrayList(), box2.getChildren());

        StackPane box3 = lookup("#box3").query();
        assertNotEquals(new ArrayList(), box3.getChildren());

        StackPane box4 = lookup("#box4").query();
        assertNotEquals(new ArrayList(), box4.getChildren());

        StackPane box5 = lookup("#box5").query();
        assertNotEquals(new ArrayList(), box5.getChildren());

        StackPane box6 = lookup("#box6").query();
        assertNotEquals(new ArrayList(), box6.getChildren());

        StackPane box7 = lookup("#box7").query();
        assertNotEquals(new ArrayList(), box7.getChildren());

        StackPane box8 = lookup("#box8").query();
        assertNotEquals(new ArrayList(), box8.getChildren());

        StackPane box9 = lookup("#box9").query();
        assertNotEquals(new ArrayList(), box9.getChildren());

        StackPane box10 = lookup("#box10").query();
        assertNotEquals(new ArrayList(), box10.getChildren());

        StackPane box11 = lookup("#box11").query();
        assertNotEquals(new ArrayList(), box11.getChildren());

        StackPane box12 = lookup("#box12").query();
        assertNotEquals(new ArrayList(), box12.getChildren());


        // **** CONNECTING CABLES ****

        clickOn(whiteNoise.lookup("#outPort"));
        clickOn(eg.lookup("#gatePort"));
        CubicCurve cable1 = lookup("#cable-1").query();
        assertNotNull(cable1);
        assertEquals(Color.BLUEVIOLET, cable1.getStroke());

        clickOn("#cableColorMenu").clickOn("#cableColorLightGreenMenuItem");

        clickOn(eg.lookup("#outPort"));
        clickOn(seq.lookup("#gatePort"));
        CubicCurve cable2 = lookup("#cable-2").query();
        assertNotNull(cable2);
        assertEquals(Color.LIGHTGREEN, cable2.getStroke());

        clickOn("#cableColorMenu").clickOn("#cableColorGoldMenuItem");

        clickOn(seq.lookup("#outPort"));
        clickOn(mixer.lookup("#inPort1"));
        CubicCurve cable3 = lookup("#cable-3").query();
        assertNotNull(cable3);
        assertEquals(Color.GOLD, cable3.getStroke());

        clickOn("#cableColorMenu").clickOn("#cableColorRedMenuItem");

        clickOn(mixer.lookup("#outPort"));
        clickOn(oscillo.lookup("#inPort"));
        CubicCurve cable4 = lookup("#cable-4").query();
        assertNotNull(cable4);
        assertEquals(Color.RED, cable4.getStroke());

        clickOn("#cableColorMenu").clickOn("#cableColorLightGreenMenuItem");

        clickOn(oscillo.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        CubicCurve cable5 = lookup("#cable-5").query();
        assertNotNull(cable5);
        assertEquals(Color.LIGHTGREEN, cable5.getStroke());


        // **** Save conf ****
        clickOn("#file").clickOn("#saveConfigMenuItem");
        type(KeyCode.ENTER);
        type(KeyCode.ENTER);


        // **** DROP ALL ****
        sleep(1000);
        clickOn("#display").clickOn("#dropAllMenuItem");
        sleep(1000);

        box1 = lookup("#box1").query();
        assertEquals(new ArrayList(), box1.getChildren());

        box2 = lookup("#box2").query();
        assertEquals(new ArrayList(), box2.getChildren());

        box3 = lookup("#box3").query();
        assertEquals(new ArrayList(), box3.getChildren());

        box4 = lookup("#box4").query();
        assertEquals(new ArrayList(), box4.getChildren());

        box5 = lookup("#box5").query();
        assertEquals(new ArrayList(), box5.getChildren());

        box6 = lookup("#box6").query();
        assertEquals(new ArrayList(), box6.getChildren());

        box7 = lookup("#box7").query();
        assertEquals(new ArrayList(), box7.getChildren());

        box8 = lookup("#box8").query();
        assertEquals(new ArrayList(), box8.getChildren());

        box9 = lookup("#box9").query();
        assertEquals(new ArrayList(), box9.getChildren());

        box10 = lookup("#box10").query();
        assertEquals(new ArrayList(), box10.getChildren());

        box11 = lookup("#box11").query();
        assertEquals(new ArrayList(), box11.getChildren());

        box12 = lookup("#box12").query();
        assertEquals(new ArrayList(), box12.getChildren());



        // **** Open conf ****
        clickOn("#file").clickOn("#openConfigMenuItem");
        type(KeyCode.C);
        type(KeyCode.O);
        type(KeyCode.N);
        type(KeyCode.F);
        type(KeyCode.ENTER);


        sleep(3000); // mandatory


        // Check box

        box1 = lookup("#box1").query();
        assertNotEquals(new ArrayList(), box1.getChildren());

        box2 = lookup("#box2").query();
        assertNotEquals(new ArrayList(), box2.getChildren());

        box3 = lookup("#box3").query();
        assertNotEquals(new ArrayList(), box3.getChildren());

        box4 = lookup("#box4").query();
        assertNotEquals(new ArrayList(), box4.getChildren());

        box5 = lookup("#box5").query();
        assertNotEquals(new ArrayList(), box5.getChildren());

        box6 = lookup("#box6").query();
        assertNotEquals(new ArrayList(), box6.getChildren());

        box7 = lookup("#box7").query();
        assertNotEquals(new ArrayList(), box7.getChildren());

        box8 = lookup("#box8").query();
        assertNotEquals(new ArrayList(), box8.getChildren());

        box9 = lookup("#box9").query();
        assertNotEquals(new ArrayList(), box9.getChildren());

        box10 = lookup("#box10").query();
        assertNotEquals(new ArrayList(), box10.getChildren());

        box11 = lookup("#box11").query();
        assertNotEquals(new ArrayList(), box11.getChildren());

        box12 = lookup("#box12").query();
        assertNotEquals(new ArrayList(), box12.getChildren());


        // Check cables


        CubicCurve cable6 = lookup("#cable-6").query();
        assertNotNull(cable1);
        assertEquals(Color.BLUEVIOLET, cable6.getStroke());

        CubicCurve cable7 = lookup("#cable-7").query();
        assertNotNull(cable7);
        assertEquals(Color.LIGHTGREEN, cable7.getStroke());

        CubicCurve cable8 = lookup("#cable-8").query();
        assertNotNull(cable8);
        assertEquals(Color.GOLD, cable8.getStroke());

        CubicCurve cable9 = lookup("#cable-9").query();
        assertNotNull(cable9);
        assertEquals(Color.RED, cable9.getStroke());

        CubicCurve cable10 = lookup("#cable-10").query();
        assertNotNull(cable10);
        assertEquals(Color.LIGHTGREEN, cable10.getStroke());


        // **** END ****
        clickOn("#mute");
        sleep(1000);
    }


}
