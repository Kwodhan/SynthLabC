package com.istic.modulesController;

import com.istic.cable.Cable;
import com.istic.cable.CableController;
import com.istic.util.DragAndDrop;
import com.istic.util.Files;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable  {

	@FXML
	AnchorPane pane;
	@FXML
	MenuItem vcoMenuItem,saveConfigMenuItem,openConfigMenuItem,saveToMP3MenuItem,dropAllMenuItem;

	@FXML
	StackPane box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12;

	@FXML
	RadioMenuItem woodMenuItem,darkMenuItem,coralMenuItem,defaultMenuItem;

	final ToggleGroup group = new ToggleGroup();

	private StackPane[] stacks;
    private Files files;


    private ArrayList<ModuleController> moduleControllers;
	private ArrayList<CableController> cables;

	private Synthesizer synth;
	private Line mouseLine;

	/**
	 * Module temporaire pour le cablage
	 */
	private ModuleController temporaryCableModuleController;

	/**
	 * valeur incrementale pour chaque id
	 */
	private Integer cableId = 1;
	private Integer moduleId = 1;

	private DragAndDrop dragAndDrop;

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
	    //files.parseJSON();
	    this.synth = JSyn.createSynthesizer();
		this.synth.start();

		coralMenuItem.setToggleGroup(group);
		darkMenuItem.setToggleGroup(group);
		woodMenuItem.setToggleGroup(group);
		defaultMenuItem.setToggleGroup(group);
		group.selectToggle(defaultMenuItem);

		this.dragAndDrop = new DragAndDrop(this);

		this.mouseLine = new Line();
		this.mouseLine.setVisible(false);
		this.mouseLine.setStrokeWidth(3);

		this.moduleControllers = new ArrayList<>();
		this.cables = new ArrayList<>();

		pane.getChildren().add(mouseLine);
		pane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
			int x = (this.mouseLine.getStartX()>event.getX()?2:-2);
			int y = (this.mouseLine.getStartY()>event.getY()?2:-2);
			this.mouseLine.setEndX(event.getX()+x);
			this.mouseLine.setEndY(event.getY()+y);
		});

		stacks = new StackPane[]{ box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12};
		//make stackpane handle drop
		for (StackPane s : stacks) {
			this.dragAndDrop.addDropHandling(s);
		}

		try {
			addOutput();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    /**
     * Supprime tous les modules sur le board
     *
     */
	public void dropAll(){

    ArrayList<ModuleController> mod= (ArrayList<ModuleController>) moduleControllers.clone();
    for(ModuleController moduleController : mod){
        moduleController.removeModule();
    }
    //files.parseJSON();

    moduleControllers.clear();
    }

	/**
	 * Open a configuration
	 */
	public void openConfig() throws IOException, ParseException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        //Show open file dialog
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if(file != null){

                dropAll();
                files=new Files(file,this);
                files.open();
        }


    }
	/**
	 * Save a configuration
	 */
	public void saveConfig() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration File");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setInitialFileName("*.json");
        //Show save file dialog
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if(file != null){
            files = new Files(file,this);
            files.save();

        }

    }
	/**
	 * Save as MP3 file
	 */
	public void saveToMP3(){

	}
	/**
	 * Change le thème en coral
	 */
	public void coralTheme(){
		pane.getStylesheets().clear();
		pane.getStylesheets().add("/skins/coral.css");
	}

	/**
	 * Change le thème en default
	 */
	public void defaultTheme() {
		pane.getStylesheets().clear();
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
	public OUTPUTModuleController addOutput() throws IOException {

		// outputModuleController=new OUTPUTModuleController();
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/output.fxml"));
		addMod(root);

		OUTPUTModuleController outputModuleController = (OUTPUTModuleController) root.getUserData();
		this.moduleControllers.add(outputModuleController);
		outputModuleController.init(this);
		return outputModuleController;
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
	public EGModuleController addEG() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/eg.fxml"));
		addMod(root);

		EGModuleController egModuleController = (EGModuleController) root.getUserData();
		this.moduleControllers.add(egModuleController);
		egModuleController.init(this);
		return egModuleController;

	}

	/**
	 * Crée les objets nécessaires pour l'apparition d'un module Oscilloscope sur le board
	 *
	 * @throws IOException si ajout impossible
	 */
	public void addOscilloscope() throws IOException {
		Node root = FXMLLoader.load(getClass().getResource(
				"../../../modules/oscilloscope.fxml"));

		OSCILLOSCOPEModuleController oscilloscopeModuleController = (OSCILLOSCOPEModuleController) root.getUserData();
		this.moduleControllers.add(oscilloscopeModuleController);
		oscilloscopeModuleController.init(this);
		addMod(root);
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
		VCFLPModuleController vcflpModuleController = (VCFLPModuleController) root.getUserData();
		this.moduleControllers.add(vcflpModuleController);
		vcflpModuleController.init(this);
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
		WHITENOISEModuleController whiteModuleController = (WHITENOISEModuleController) root.getUserData();
        this.moduleControllers.add(whiteModuleController);
        whiteModuleController.init(this);
	}

	/**
	 * Ajout d'un cable
	 * @param moduleController controleur du module qu'il faut connecter
	 */
	public void connect(ModuleController moduleController) {
		Cable cable = new Cable(this.temporaryCableModuleController.getCurrentPort(),moduleController.getCurrentPort());
		if (cable.connect()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Color choice");
            alert.setHeaderText("Please choose a color");

            List<ButtonType> buttonTypes = new ArrayList<>();

			buttonTypes.add(new ButtonType("GOLD"));
			buttonTypes.add(new ButtonType("BLUEVIOLET"));
			buttonTypes.add(new ButtonType("RED"));
			buttonTypes.add(new ButtonType("OLIVE"));
			buttonTypes.add(new ButtonType("SALMON"));
			buttonTypes.add(new ButtonType("SILVER"));
			buttonTypes.add(new ButtonType("MEDIUMAQUAMARINE"));

            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypes);
            alert.getButtonTypes().add(buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
			CableController cableController =null;
			if(!result.get().getText().equals(buttonTypeCancel.getText())) {
				cableController = new CableController(pane, cable, Color.valueOf(result.get().getText()));
			}
            if (cableController!=null) {
                    cableController.drawCable(this.temporaryCableModuleController,moduleController,cableId++);
                    this.cables.add(cableController);
            } else {
                cable.disconnect();
                // ... user chose CANCEL or closed the dialog
            }

		}
	}

	/**
	 * Supprime un module controller de la liste du controller
	 *
	 * @param moduleController controleur du module à supprimer
	 */
	public void disconnect(ModuleController moduleController) {
		this.moduleControllers.remove(moduleController);
	}

	/**
	 * Ajoute un module sur le board
	 *
	 * @param root noeud du module à ajouter au board
	 */
	public void addMod(Node root) {
		root.setId("module-" + moduleId++);
		for(StackPane s : stacks) {
			if(s.getChildren().isEmpty()) {
				s.getChildren().add(root);
				this.dragAndDrop.dragNode(root);
				return;
			}
		}
	}






	// Setters & Getters

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

	public Line getMouseLine() {
		return mouseLine;
	}

    public StackPane[] getStacks() {
        return stacks;
    }

    public ArrayList<ModuleController> getModuleControllers() {
        return moduleControllers;
    }
}
