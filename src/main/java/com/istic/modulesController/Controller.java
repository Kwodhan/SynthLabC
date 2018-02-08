package com.istic.modulesController;

import com.istic.cable.Cable;
import com.istic.cable.CableController;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    MenuItem vcoMenuItem;
    @FXML
    HBox hBox1, hBox2,hBox3;
    @FXML
    Button startVCOButton,stopVCOButton;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio,squareRadio;

    private List<ModuleController> moduleControllers;
    private List<CableController> cables;


    private Synthesizer synth;
    /**
     * Module temporaire pour le cablage
     */
	ModuleController temporaryCableModuleController;
    /**
     * valeur incrementale pour chaque id
     */
	private Integer cableId = 1;
	private Integer moduleId = 1;

    private boolean isPlugged = false;

	public void initialize(URL location, ResourceBundle resources) {
		this.synth = JSyn.createSynthesizer();
		this.synth.start();

        this.moduleControllers = new ArrayList<>();
        this.cables = new ArrayList<>();

		try {
			addOutput();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }


	public void addVCO() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vco.fxml"));
		addMod(root);
        VCOModuleController vcoModuleController = (VCOModuleController) root.getUserData();
		this.moduleControllers.add(vcoModuleController);
        vcoModuleController.init(this);
	}

	public void addOutput() throws IOException {

		// outputModuleController=new OUTPUTModuleController();
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/output.fxml"));

		addMod(root);

        OUTPUTModuleController outputModuleController = (OUTPUTModuleController) root.getUserData();
        this.moduleControllers.add(outputModuleController);
        outputModuleController.init(this);

	}

	public void addMixer() throws IOException {

		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/mixer.fxml"));
		addMod(root);
	}

	public void addEG() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/eg.fxml"));
		addMod(root);

		EGModuleController egModuleController = (EGModuleController) root.getUserData();
		this.moduleControllers.add(egModuleController);
		egModuleController.init(this);

	}

	public void addOscilloscope() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/oscilloscope.fxml"));
		addMod(root);

		OSCILLOSCOPEModuleController oscilloscopeModuleController = (OSCILLOSCOPEModuleController) root.getUserData();
		this.moduleControllers.add(oscilloscopeModuleController);
		oscilloscopeModuleController.init(this);
	}

	public void addReplicator() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/replicator.fxml"));
		addMod(root);

		REPLICATORModuleController replicatorModuleController = (REPLICATORModuleController) root.getUserData();
		this.moduleControllers.add(replicatorModuleController);
		replicatorModuleController.init(this);

	}

	public void addSequencer() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/sequencer.fxml"));
		addMod(root);

	}

	public void addVca() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vca.fxml"));
		addMod(root);

		VCAModuleController vcaModuleController = (VCAModuleController) root.getUserData();
        this.moduleControllers.add(vcaModuleController);
        vcaModuleController.init(this);

	}

	public void addVcfLp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfLp.fxml"));
		addMod(root);
	}

	public void addVcfHp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfHp.fxml"));
		addMod(root);
	}

	public void addWhiteNoise() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/whiteNoise.fxml"));
		addMod(root);
	}

	public void connect(ModuleController moduleController) {
        Cable cable = new Cable(this.temporaryCableModuleController.getCurrentPort(),moduleController.getCurrentPort());
        if (cable.connect()) {
            CableController cableController = new CableController(pane,cable);
            cableController.drawCable(this.temporaryCableModuleController,moduleController,cableId++);
            this.cables.add(cableController);
        }

    }

    public void disconnect(ModuleController moduleController) {
	    for (ModuleController module: this.moduleControllers) {
	    	if (module.equals(moduleController)) {
	    		this.moduleControllers.remove(module);
	    		moduleController = null;
			}
		}
    }

    public void addMod(Node root) {
		root.setId("module-" + moduleId++);
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
                    if(hBox1.getChildren().size()==3 && hBox2.getChildren().size()==3&&hBox3.getChildren().size()==3)
                    {

                    }
                }
            }
        }
    }

//    public void removeMod(){
    // old code to get parent hbox1 : not necessary anymore
//        SplitPane splitPane= (SplitPane) pane.getChildren().get(1);
//        AnchorPane anch = (AnchorPane) splitPane.getItems().get(1);
//        GridPane grid = (GridPane) anch.getChildren().get(0);
//        //grid.getChildren().get(0).toString()
//        HBox hbox1 = (HBox) grid.getChildren().get(0);
//        System.out.println(hbox1.toString());
//    }


    //Setters & Getters
	public AnchorPane getPane() {
		return pane;
	}

	public boolean isPlugged() {
		return isPlugged;
	}

	public void setPlugged(boolean plugged) {
		isPlugged = plugged;
	}


    public ModuleController getTemporaryCableModuleController() {
        return temporaryCableModuleController;
    }

    public void setTemporaryCableModuleController(ModuleController temporaryCableModuleController) {
        this.temporaryCableModuleController = temporaryCableModuleController;
    }
	public Synthesizer getSynth() {
		return synth;
	}

	public List<CableController> getCables() {
        return cables;
    }

}
