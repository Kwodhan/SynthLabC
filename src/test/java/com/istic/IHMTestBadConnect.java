package com.istic;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurve;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.junit.Assert.assertNull;

public class IHMTestBadConnect extends ApplicationTest {

    @Override
    public void start (Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(App.class.getResource("../../main.fxml"));
        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testSprint2() {
        // get output module
        AnchorPane output = lookup("#module-1").query();

        // add modules
        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco1 = lookup("#module-2").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcoMenuItem");
        AnchorPane vco2 = lookup("#module-3").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#vcaMenuItem");
        AnchorPane vca = lookup("#module-4").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#egMenuItem");
        AnchorPane eg = lookup("#module-5").query();

        clickOn("#display").clickOn("#add").moveTo("#egMenuItem").clickOn("#replicatorMenuItem");
        AnchorPane replicator = lookup("#module-6").query();

        // la sortie `out` du VCO n°2 est reliée à l’entrée `fm` du VCO n°2 ;
        clickOn(vco2.lookup("#fmPort"));
        clickOn(vco2.lookup("#outPort"));

        CubicCurve cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // la sortie `out` du VCO n°2 est reliée à la sortie `out` du VCO n°1 ;
        clickOn(vco2.lookup("#outPort"));
        clickOn(vco1.lookup("#outPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // l'entrée `fm` du VCO n°2 est reliée à l’entrée `fm` du VCO n°1 ;
        clickOn(vco2.lookup("#fmPort"));
        clickOn(vco1.lookup("#fmPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // l'entrée `fm` du VCO n°2 est reliée à l’entrée `am` du VCA ;
        clickOn(vca.lookup("#amPort"));
        clickOn(vco2.lookup("#fmPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // l'entrée `in` du REP est reliée à l’entrée `am` du VCA ;
        clickOn(replicator.lookup("#inPort"));
        clickOn(vco2.lookup("#fmPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // l'entrée `in` du VCA est reliée à l’entrée `FM` du VCO ;
        clickOn(vca.lookup("#inPort"));
        clickOn(vco1.lookup("#fmPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // la sortie `out` du VC0 est reliée à la sortie `out1` du REP ;

        clickOn(vco2.lookup("#outPort"));
        clickOn(replicator.lookup("#outPort1"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // la sortie `out` du VCA est reliée à la sortie `out2` du REP ;
        clickOn(replicator.lookup("#outPort2"));
        clickOn(vca.lookup("#outPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // la sortie `out` du VC0 est reliée à la sortie `out3` du REP ;
        clickOn(replicator.lookup("#outPort3"));
        clickOn(vco2.lookup("#outPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // la sortie `out` du VCA est reliée à la sortie `out2` du REP ;
        clickOn(eg.lookup("#gatePort"));
        clickOn(replicator.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // la sortie `gate` du EG est reliée à `in` du VCA ;
        clickOn(eg.lookup("#gatePort"));
        clickOn(vca.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);

        // `gate` du EG est reliée à `in` du Sortie ;
        clickOn(eg.lookup("#gatePort"));
        clickOn(output.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // `gate` du EG est reliée à `vca` du Sortie ;
        clickOn(eg.lookup("#gatePort"));
        clickOn(vca.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // `gate` du EG est reliée à `out` du vca ;
        clickOn(eg.lookup("#gatePort"));
        clickOn(replicator.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);


        // `in` du Sortie est reliée à `in` du vca ;
        clickOn(output.lookup("#inPort"));
        clickOn(vca.lookup("#inPort"));

        cable1 = lookup("#cable-1").query();
        assertNull(cable1);
        clickOn("#mute");
    }



}
