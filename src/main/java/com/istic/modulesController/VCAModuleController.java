package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.vca.VCA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VCAModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    Slider amplitudeSlider;
    @FXML
    ImageView outPort,inPort,amPort;
    protected VCA vca;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amplitudeSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider.setValue(Math.round(amplitudeSlider.getValue()));
            vca.changeA0((int) amplitudeSlider.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });

    }

    public void init(Controller controller) {
        super.init(controller);
        this.vca = new VCA();
        this.controller.getSynth().add(vca);


    }

    /**
     * Connecting the AmPort to draw the cable
     */
    public void connectAmPort() {
        if(!this.vca.getAm().isConnected()) {
            currentPort = 1;
            getLayout(amPort);
            super.connect();
        }
    }
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.vca.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        if(!this.vca.getInput().isConnected()) {
            currentPort = 2;
            getLayout(inPort);
            super.connect();
        }
    }


    @Override
    public Port getCurrentPort() {
        switch (currentPort) {

            case 0:
                return vca.getOutput();
            case 1:
                return vca.getAm();
            case 2 :
                return vca.getInput();

            default: return null;
        }
    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException
     */
    @FXML
    public void removeModule() throws IOException {
        //Deconnexion cables
        Port am = vca.getAm();
        Port in = vca.getInput();
        Port out = vca.getOutput();
        super.disconnect(am);
        super.disconnect(in);
        super.disconnect(out);
        // Deconnexion du module Output du synthetizer
        this.controller.getSynth().remove(vca);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        HBox hbox1= (HBox) pane.getParent();
        // supprime le mod niveau ihm
        hbox1.getChildren().remove(pane);
    }
}
