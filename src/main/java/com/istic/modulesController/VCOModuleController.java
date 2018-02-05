package com.istic.modulesController;


import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.vco.VCO;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class VCOModuleController extends Pane implements Initializable,ModuleController {
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
    @FXML
    ImageView outPort;
    double x=0,y=0;
    @FXML
    ImageView fmPort;

    int currentPort=-1;


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
        synthesizer.add(vco);

        //vco.start();

    }
    public PortOutput connectOut(){

        return vco.getOutput();

    }

    /**
     * Connecting the fmPort to draw the cable
     */
    public void connectFmPort() {
        currentPort=1;
        connecting(fmPort);
        if(this.controller.isPlugged()  && !this.controller.getPort().equals(this.getCurrentPort())){
            this.controller.connect();
            this.controller.setPort(null);
            this.controller.setPlugged(false);

        }else{
            this.controller.setPlugged(true);
            this.controller.setPort(this.getCurrentPort());
        }

    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        System.out.println("connectOutPort");

        currentPort=0;
        connecting(outPort);


        if(this.controller.isPlugged() && !this.controller.getPort().equals(this.getCurrentPort())){

            this.controller.connect();
            this.controller.setPlugged(false);
            this.controller.setPort(null);
            System.out.println("merde connect VCO");
        }else{
            this.controller.setPlugged(true);
            this.controller.setPort(this.getCurrentPort());
            System.out.println("merde plugged VCO");

        }
    }

    /**
     * initialize x and y to draw
     * @param port
     */
    public void connecting(ImageView port){
        System.out.println("connecting VCO");

        Bounds boundsInScene = port.localToScene(port.getBoundsInLocal());
        x=(boundsInScene.getMaxX()+boundsInScene.getMinX())/2.0;
        y=(boundsInScene.getMaxY()+boundsInScene.getMinY())/2.0;
        System.out.println("vcoooooooooooooooooooooooooooo"+x);

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

    public void squareSound(){
        vco.changeShapeWave(VCO.SQUAREWAVE);

    }
    public void sawSound(){
        vco.changeShapeWave(VCO.SAWWAVE);

    }
    public void triangleSound(){
        vco.changeShapeWave(VCO.TRIANGLEWAVE);

    }

    public Port getCurrentPort(){
        System.out.println("getCurrentPort VCO");

        if(currentPort==0){
            return vco.getOutput();
        }else if(currentPort==1) {
                return vco.getFm() ;
        }
        return null;
    }
}
