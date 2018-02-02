package com.istic;

import com.istic.cable.Cable;
import com.istic.modulesController.OUTPUTModuleController;
import com.istic.modulesController.VCOModuleController;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    Cable cable1;
    Line line ;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    AnchorPane pane;
    @FXML
    MenuItem vcoMenuItem;
    @FXML
    HBox hBox1, hBox2,hBox3;
    @FXML
    Button startVCOButton,stopVCOButton,muteButton;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio,squareRadio;
    VCOModuleController vcoModuleController;
    OUTPUTModuleController outputModuleController;
    private Synthesizer synth;

    private VCO vco;
    private OutMod lineOut;


    public void initialize(URL location, ResourceBundle resources) {
        this.synth = JSyn.createSynthesizer();

        this.lineOut = new OutMod(this.synth);
        this.vco = new VCO(this.synth);


        Cable cable = new Cable(vco.getPortOutput(),lineOut.getPortInput());
        System.out.println(cable.connect());

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
        //vcoModuleController=new VCOModuleController();
        Node root = FXMLLoader.load(getClass().getResource("../../modules/vco.fxml"));
        addMod(root);
        vcoModuleController= (VCOModuleController) root.getUserData();
        //vcoModuleController.init(this,synth);
    }
    public void addOutput() throws IOException {
        //outputModuleController=new OUTPUTModuleController();
        Node root = FXMLLoader.load(getClass().getResource("../../modules/output.fxml"));
        addMod(root);

        outputModuleController= (OUTPUTModuleController) root.getUserData();
        //outputModuleController.init(this,synth);

    }
    public void addMixer() throws IOException {

        Node root = FXMLLoader.load(getClass().getResource("../../modules/mixer.fxml"));
        addMod(root);

    }
    public void addEG() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/eg.fxml"));
        addMod(root);

    }

    public void addOscilloscope() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/oscilloscope.fxml"));
        addMod(root);

    }
    public void addReplicator() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/replicator.fxml"));
        addMod(root);

    }
    public void addSequencer() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/sequencer.fxml"));
        addMod(root);

    }
    public void addVca() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/vca.fxml"));
        addMod(root);

    }
    public void addVcfLp() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/vcfLp.fxml"));
        addMod(root);

    }
    public void addVcfHp() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/vcfHp.fxml"));
        addMod(root);

    }
    public void addWhiteNoise() throws IOException {
        Node root = FXMLLoader.load(getClass().getResource("../../modules/whiteNoise.fxml"));
        addMod(root);

    }
    public void mute(){
        if(lineOut.isMute()==1){
            lineOut.setOnMute();

        }else{
                lineOut.setOffMute();
        }



    }
public void drawCable(){
    if(line==null){

        line= new Line(vcoModuleController.getX(), vcoModuleController.getY(), outputModuleController.getX(), outputModuleController.getY());
        pane.getChildren().add(line);

    }else{
        pane.getChildren().remove(line);
        line= new Line(vcoModuleController.getX(), vcoModuleController.getY(), outputModuleController.getX(), outputModuleController.getY());
        pane.getChildren().add(line);

    }
}
    public void connect(){

        vcoModuleController.init(this,synth);
        outputModuleController.init(this,synth);
        cable1 = new Cable(vcoModuleController.connectOut(),outputModuleController.connect());
        if(cable1.connect()){
            drawCable();
        }
        this.synth.start();


    }
    public void addMod(Node root){



        if(hBox1.getChildren().size()<3){
            hBox1.getChildren().add(root);

        }else{

            if(hBox2.getChildren().size()<3){
                hBox2.getChildren().add(root);
            }else
            {

                if(hBox3.getChildren().size()<3)
                {
                    hBox3.getChildren().add(root);
                }else{
                    if(hBox1.getChildren().size()==3 && hBox2.getChildren().size()==3&&hBox3.getChildren().size()==3){

                    }
                }
            }
        }

    }

    public AnchorPane getPane() {
        return pane;
    }
}
