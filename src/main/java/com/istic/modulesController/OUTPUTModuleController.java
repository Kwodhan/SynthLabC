package com.istic.modulesController;

import com.istic.OutMod;
import com.istic.VCO;
import com.istic.cable.Cable;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class OUTPUTModuleController extends Pane implements Initializable {

    @FXML
    ImageView outPort;



    private Synthesizer synth;

    private OutMod lineOut;



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
        this.synth = JSyn.createSynthesizer();

        this.lineOut = new OutMod(this.synth);
        lineOut.start();


    }
    public void connect(){
    System.out.println("connetion established");


    }
}
