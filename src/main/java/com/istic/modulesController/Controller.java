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
    @FXML
	RadioMenuItem woodMenuItem,darkMenuItem,coralMenuItem;

    final ToggleGroup group = new ToggleGroup();

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

    /**
     * Initialise les objets nécessaires à l'application
     * et ajoute un module de sortie au board
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    public void initialize(URL location, ResourceBundle resources) {
		this.synth = JSyn.createSynthesizer();
		this.synth.start();
		coralMenuItem.setToggleGroup(group);
		darkMenuItem.setToggleGroup(group);
		woodMenuItem.setToggleGroup(group);

		this.moduleControllers = new ArrayList<>();
        this.cables = new ArrayList<>();

		try {
			addOutput();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * Change le thème en coral
     */
    public void coralTheme(){
		pane.getStylesheets().clear();
		pane.getStylesheets().add("/skins/coral.css");
	}

    /**
     * Change le thème en dark
     */
	public void darkTheme(){
		pane.getStylesheets().clear();
		pane.getStylesheets().add("/skins/dark.css");
	}

    /**
     * Change le thème en wood
     */
	public void woodTheme(){
		pane.getStylesheets().clear();
		pane.getStylesheets().add("/skins/wood.css");
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCO sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addVCO() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vco.fxml"));
		addMod(root);
        VCOModuleController vcoModuleController = (VCOModuleController) root.getUserData();
		this.moduleControllers.add(vcoModuleController);
        vcoModuleController.init(this);
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Output sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addOutput() throws IOException {

		// outputModuleController=new OUTPUTModuleController();
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/output.fxml"));
		addMod(root);

        OUTPUTModuleController outputModuleController = (OUTPUTModuleController) root.getUserData();
        this.moduleControllers.add(outputModuleController);
        outputModuleController.init(this);

	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Mixer sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addMixer() throws IOException {

		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/mixer.fxml"));
		addMod(root);
		MIXERModuleController mixerModuleController= (MIXERModuleController) root.getUserData();
		this.moduleControllers.add(mixerModuleController);
		mixerModuleController.init(this);
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module EG sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addEG() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/eg.fxml"));
		addMod(root);

		EGModuleController egModuleController = (EGModuleController) root.getUserData();
		this.moduleControllers.add(egModuleController);
		egModuleController.init(this);

	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Oscilloscope sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addOscilloscope() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/oscilloscope.fxml"));
		addMod(root);

		OSCILLOSCOPEModuleController oscilloscopeModuleController = (OSCILLOSCOPEModuleController) root.getUserData();
		this.moduleControllers.add(oscilloscopeModuleController);
		oscilloscopeModuleController.init(this);
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Réplicateur sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addReplicator() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/replicator.fxml"));
		addMod(root);

		REPLICATORModuleController replicatorModuleController = (REPLICATORModuleController) root.getUserData();
		this.moduleControllers.add(replicatorModuleController);
		replicatorModuleController.init(this);

	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Séquenceur sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addSequencer() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/sequencer.fxml"));
		addMod(root);

	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCA sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addVca() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vca.fxml"));
		addMod(root);

		VCAModuleController vcaModuleController = (VCAModuleController) root.getUserData();
        this.moduleControllers.add(vcaModuleController);
        vcaModuleController.init(this);

	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCF LP sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addVcfLp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfLp.fxml"));
		addMod(root);
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCF HP sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addVcfHp() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/vcfHp.fxml"));
		addMod(root);
	}

    /**
     * Crée les objets nécessaires pour l'apparition d'un module bruit blanc sur le board
     *
     * @throws IOException si ajout impossible
     */
	public void addWhiteNoise() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/whiteNoise.fxml"));
		addMod(root);
	}

    /**
     * Ajout d'un cable
     * @param moduleController controleur du module qu'il faut connecter
     */
	public void connect(ModuleController moduleController) {
        Cable cable = new Cable(this.temporaryCableModuleController.getCurrentPort(),moduleController.getCurrentPort());
        if (cable.connect()) {
            CableController cableController = new CableController(pane,cable);
            cableController.drawCable(this.temporaryCableModuleController,moduleController,cableId++);
            this.cables.add(cableController);
        }

    }

    /**
     * Supprime un module controller de la liste du controller
     *
     * @param moduleController controleur du module à supprimer
     */
    public void disconnect(ModuleController moduleController) {
	    for (ModuleController module: this.moduleControllers) {
	    	if (module.equals(moduleController)) {
	    		this.moduleControllers.remove(module);
	    		moduleController = null;
			}
		}
    }

    /**
     * Ajoute un module sur le board
     *
     * @param root noeud du module à ajouter au board
     */
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
