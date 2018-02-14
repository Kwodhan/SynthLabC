package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IHMChangeCableColorTest extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    /**
     * Update cable color
     */
    @Test
    public void testChangeCableColor() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco = lookup("#module-2").query();

        // CONNECTING CABLE

        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));

        CubicCurve cable = lookup("#cable-1").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-1");


        // Default we are on BLUEVIOLET !
        assertEquals(Color.BLUEVIOLET, cable.getStroke());


        // Change to GOLD
        clickOn("#cableColorMenu").clickOn("#cableColorGoldMenuItem");
        clickOn(vco.lookup("#outPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        cable = lookup("#cable-2").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-2");
        assertEquals(Color.GOLD, cable.getStroke());

        // Change to RED
        clickOn("#cableColorMenu").clickOn("#cableColorRedMenuItem");
        clickOn(vco.lookup("#outPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        cable = lookup("#cable-3").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-3");
        assertEquals(Color.RED, cable.getStroke());

        // Change to LIGHTGREEN
        clickOn("#cableColorMenu").clickOn("#cableColorLightGreenMenuItem");
        clickOn(vco.lookup("#outPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        cable = lookup("#cable-4").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-4");
        assertEquals(Color.LIGHTGREEN, cable.getStroke());

        // Change to BLUEVIOLET
        clickOn("#cableColorMenu").clickOn("#cableColorBluevioletMenuItem");
        clickOn(vco.lookup("#outPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        cable = lookup("#cable-5").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-5");
        assertEquals(Color.BLUEVIOLET, cable.getStroke());

        // RE Change to BLUEVIOLET
        clickOn("#cableColorMenu").clickOn("#cableColorBluevioletMenuItem");
        clickOn(vco.lookup("#outPort"));
        clickOn(vco.lookup("#outPort"));
        clickOn(output.lookup("#inPort"));
        cable = lookup("#cable-6").query();
        assertNotNull(cable);
        assertEquals(cable.getId(), "cable-6");
        assertEquals(Color.BLUEVIOLET, cable.getStroke());
    }

}
