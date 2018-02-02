package com.istic.modulesController;

import com.istic.Controller;
import com.istic.VCO;
import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class VCOModuleController extends Pane implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    final ToggleGroup group = new ToggleGroup();
    private Controller controller;
    @FXML
    Button startVCOButton,stopVCOButton;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio,squareRadio;


    private VCO vco;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //this.vco = new VCO(this.synth);





        sawRadio.setToggleGroup(group);
        triangleRadio.setToggleGroup(group);
        squareRadio.setToggleGroup(group);
        squareRadio.setSelected(true);

        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            frequencySlider.setValue(Math.round(frequencySlider.getValue()));
            vco.changeOctave((int)frequencySlider.getValue());


        });

        frequencyFineSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            //frequencyFineSlider.setValue(Math.round(frequencyFineSlider.getValue()));
            vco.changeFin(frequencyFineSlider.getValue());


        });
        //this.synth.start();
        //vco.start();




    }
    public void init(Controller controller,Synthesizer synthesizer){
        this.controller=controller;
        this.vco = new VCO();
        vco.start();

    }
    public PortOutput connectOut(){
        return vco.getOutput();

    }


}
