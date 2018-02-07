package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.vca.VCA;
import com.istic.vco.VCO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class VCAModuleController extends ModuleController implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    Slider amplitudeSlider;
    @FXML
    ImageView outPort,inPort,amPort;
    protected VCA vca;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amplitudeSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider.setValue(Math.round(amplitudeSlider.getValue()));
            vca.changeA0((int) amplitudeSlider.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");
            System.out.println(vca.getAmplitude());
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
        currentPort = 1;
        getLayout(amPort);
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
    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        currentPort = 2;
        getLayout(inPort);
        super.connect();
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
}
