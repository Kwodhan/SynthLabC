package com.istic.modulesController;

import com.istic.out.OutMod;
import com.istic.port.Port;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class OUTPUTModuleController extends Pane implements Initializable, ModuleController {

    @FXML
    ImageView inPort;
    @FXML
    AnchorPane pane;
    Controller controller;
    double x=0,y=0;

    private OutMod lineOut;

    @FXML
    private Slider attenuationSlider;

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

        //this.lineOut = new OutMod(this.synth);
        //lineOut.start();

        attenuationSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            double newAttenuation = Math.round(attenuationSlider.getValue());
            attenuationSlider.setValue(newAttenuation);
            lineOut.setAttenuation(newAttenuation);
        });
    }

    public void connect() {
        System.out.println("connect");

        connectIn();
        //boolean a=!this.controller.getPort().equals(this.getCurrentPort());
        //System.out.println("pourquoi  "+a);
        if(this.controller.isPlugged()  && !this.controller.getPort().equals(this.getCurrentPort()) ){
            this.controller.connect();
            this.controller.setPort(this.getCurrentPort());
            this.controller.setPlugged(false);

            System.out.println("connetion OUT");
        }else{
            this.controller.setPlugged(true);
            this.controller.setPort(this.getCurrentPort());
            System.out.println("merde plugged OUT");
        }

        System.out.println("Done connecting");


    }
    public void connectIn(){
        System.out.println("connecting");
        Bounds boundsInScene = inPort.localToScene(inPort.getBoundsInLocal());
        x=(boundsInScene.getMaxX()+boundsInScene.getMinX())/2.0;
        y=(boundsInScene.getMaxY()+boundsInScene.getMinY())/2.0;
        System.out.println(boundsInScene.toString());

    }

    public void init(Controller controller,Synthesizer synthesizer){

        this.controller=controller;
        this.lineOut = new OutMod();
        synthesizer.add(this.lineOut);
        lineOut.start();

    }

    public void toggleMute() {
        this.lineOut.toggleMute();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
    @Override
    public Port getCurrentPort(){

        System.out.println("getCurrentPort");
        if(lineOut!=null) {

            return lineOut.getPortInput();
        }

        return  null;
    }

}
