package com.istic.modulesController;

import com.istic.eg.EG;
import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.vco.VCO;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class EGModuleController extends ModuleController implements Initializable {
	
	protected EG eg;
    @FXML
    ImageView outPort;
    @FXML
    ImageView gateport;
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

    }

    public Port getCurrentPort() {
        if (currentPort == 0) {
            return eg.getOutputPort();
        } else if (currentPort == 1) {
            return eg.getGateInput();
        }
        return null;
    }
    public PortOutput getOutPort() {

        return this.eg.getOutputPort();

    }
    public void init(Controller controller) {
        super.init(controller);
        this.eg = new EG();
        this.controller.getSynth().add(eg);

    }
    
    public void connectGatePort() {
        currentPort = 1;
        getLayout(gateport);
        super.connect();
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        currentPort = 0;
        getLayout(outPort);
        super.connect();
    }
}
