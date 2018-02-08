package com.istic.modulesController;

import com.istic.eg.EG;
import com.istic.port.Port;
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


public class EGModuleController extends ModuleController implements Initializable {
	
	protected EG eg;

    @FXML
    AnchorPane pane;
	@FXML
    ImageView outPort;
    @FXML
    ImageView gatePort;
    @FXML
    Slider attackSlider;
    @FXML
    Slider decaySlider;
    @FXML
    Slider sustainSlider;
    @FXML
    Slider releaseSlider;
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
        attackSlider.valueProperty().addListener((ov, old_val, new_val) -> {



            this.eg.setAttack(8*Math.exp(attackSlider.getValue()-10));
        });

        releaseSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setRelease(8*Math.exp(releaseSlider.getValue()-10));
        });

        decaySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setDecay(8*Math.exp(decaySlider.getValue()-10));
        });

        sustainSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setSustain(sustainSlider.getValue()/10);
        });

    }

    public Port getCurrentPort() {
        if (currentPort == 0) {
            return eg.getOutput();
        } else if (currentPort == 1) {
            return eg.getGateInput();
        }
        return null;
    }

    public void init(Controller controller) {
        super.init(controller);
        this.eg = new EG();
        this.controller.getSynth().add(eg);

    }
    
    public void connectGatePort() {
        if(!this.eg.getGateInput().isConnected()) {
            currentPort = 1;
            getLayout(gatePort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.eg.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    @FXML
    public void removeModule(InputEvent e) throws IOException {
        //Deconnexion cable
        Port gate = eg.getGateInput();
        Port out = eg.getOutput();
        super.disconnect(gate);
        super.disconnect(out);
        // Deconnexion du module Output du synthetizer
        this.controller.getSynth().remove(eg);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        HBox hbox1= (HBox) pane.getParent();
        // supprime le mod niveau ihm
        hbox1.getChildren().remove(pane);
    }

}
