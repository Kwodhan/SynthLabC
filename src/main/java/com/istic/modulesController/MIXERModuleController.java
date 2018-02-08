package com.istic.modulesController;

import com.istic.mixer.MIXER;
import com.istic.port.Port;
import com.istic.vca.VCA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class MIXERModuleController extends ModuleController implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    ImageView inPort1,inPort2,inPort3,inPort4,outPort;
    @FXML
    Slider amplitudeSlider1,amplitudeSlider2,amplitudeSlider3,amplitudeSlider4;
    MIXER mixer;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        amplitudeSlider1.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider1.setValue(Math.round(amplitudeSlider1.getValue()));
            mixer.changeAtt1((int) amplitudeSlider1.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider2.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider2.setValue(Math.round(amplitudeSlider2.getValue()));
            mixer.changeAtt2((int) amplitudeSlider2.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider3.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider3.setValue(Math.round(amplitudeSlider3.getValue()));
            mixer.changeAtt3((int) amplitudeSlider3.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider4.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider4.setValue(Math.round(amplitudeSlider4.getValue()));
            mixer.changeAtt4((int) amplitudeSlider4.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });

    }

    @Override
    public Port getCurrentPort() {

        switch (currentPort) {

            case 0:
                return mixer.getOutput();
            case 1:
                return mixer.getInput1();
            case 2 :
                return mixer.getInput2();
            case 3 :
                return mixer.getInput3();
            case 4 :
                return mixer.getInput4();

            default: return null;
        }
    }
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.mixer.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    public void connectInPort1() {
        if(!this.mixer.getInput1().isConnected()) {
            currentPort = 1;
            getLayout(inPort1);
            super.connect();
        }
    }
    public void connectInPort2() {

        if(!this.mixer.getInput2().isConnected()) {
            currentPort = 2;
            getLayout(inPort2);
            super.connect();
        }
    }
    public void connectInPort3() {

        if(!this.mixer.getInput3().isConnected()) {
            currentPort = 3;
            getLayout(inPort3);
            super.connect();
        }
    }
    public void connectInPort4() {

        if(!this.mixer.getInput4().isConnected()) {
            currentPort = 4;
            getLayout(inPort4);
            super.connect();
        }
    }
    public void init(Controller controller) {
        super.init(controller);
        this.mixer = new MIXER();
        this.controller.getSynth().add(mixer);


    }

}
