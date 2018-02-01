package com.istic;

import com.istic.cable.Cable;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    final ToggleGroup group = new ToggleGroup();
    @FXML
    MenuItem vcoMenuItem;
    @FXML
    HBox HBox1;
    @FXML
    Button startVCOButton,stopVCOButton;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio,squareRadio;

    private Synthesizer synth;

    private VCO vco;
    private OutMod lineOut;


    public void initialize(URL location, ResourceBundle resources) {
        this.synth = JSyn.createSynthesizer();

        this.lineOut = new OutMod(this.synth);
        this.vco = new VCO(this.synth);

        Cable cable = new Cable(vco.getPortOutput(),lineOut.getPortInput());
        cable.connect();


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
            vco.changeFineHertz(frequencyFineSlider.getValue());


        });




    }


    public void startSoundVCO() throws InterruptedException {
        this.synth.start();
        vco.start();
        lineOut.start();


    }

    public void stopSoundVCO() throws InterruptedException {
        this.synth.stop();
        vco.stop();
        lineOut.stop();
    }

    public void squareSound(){
        vco.changeShapeWave(ShapeWave.Square);

    }
    public void sawSound(){
        vco.changeShapeWave(ShapeWave.Saw);

    }
    public void triangleSound(){
       vco.changeShapeWave(ShapeWave.Triangle);

    }
    public void addVCO() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/cvo.fxml"));

        HBox1.getChildren().add(root);

    }

}
