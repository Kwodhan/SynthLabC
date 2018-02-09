package com.istic.modulesController;


import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.vco.VCO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import org.omg.PortableServer.POA;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class VCOModuleController extends ModuleController implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @FXML
    AnchorPane pane;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio, squareRadio;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    ImageView outPort;
    @FXML
    ImageView fmPort;

    @FXML
    Label txtHertz;

    protected VCO vco;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sawRadio.setToggleGroup(group);
        triangleRadio.setToggleGroup(group);
        squareRadio.setToggleGroup(group);
        squareRadio.setSelected(true);
        Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            frequencySlider.setValue(Math.round(frequencySlider.getValue()));
            vco.changeOctave((int) frequencySlider.getValue());
            Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        });

        frequencyFineSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            //frequencyFineSlider.setValue(Math.round(frequencyFineSlider.getValue()));
            vco.changeFin(frequencyFineSlider.getValue());
            Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        });
        //this.synth.start();
        //vco.start();


    }


    public PortOutput getOutPort() {

        return this.vco.getOutput();

    }

    public void init(Controller controller) {
        super.init(controller);
        this.vco = new VCO();
        this.controller.getSynth().add(vco);

    }

    /**
     * Connecting the fmPort to draw the cable
     */
    public void connectFmPort() {

        if(!this.vco.getFm().isConnected()) {
            currentPort = 1;
            getLayout(fmPort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.vco.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }

    public Port getCurrentPort() {
        if (currentPort == 0) {
            return vco.getOutput();
        } else if (currentPort == 1) {
            return vco.getFm();
        }
        return null;
    }

    public void squareSound() {
        vco.changeShapeWave(VCO.SQUAREWAVE);
    }

    public void sawSound() {
        vco.changeShapeWave(VCO.SAWWAVE);

    }

    public void triangleSound() {
        vco.changeShapeWave(VCO.TRIANGLEWAVE);

    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, vco.getOutput());
        hashMap.put(fmPort, vco.getFm());
        return hashMap;
    }

    @FXML
    public void removeOutput(InputEvent e) throws IOException {
        //Deconnexion cable
        Port fm = vco.getFm();
        Port out = vco.getOutput();
        super.disconnect(fm);
        super.disconnect(out);
        // Deconnexion du module Output du synthetizer
        this.controller.getSynth().remove(vco);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        StackPane stackPane = (StackPane) pane.getParent();
        // supprime le mod niveau ihm
        stackPane.getChildren().remove(pane);
    }

}
