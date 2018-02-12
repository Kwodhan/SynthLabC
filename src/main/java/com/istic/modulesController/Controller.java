package com.istic.modulesController;

import com.google.gson.Gson;
import com.istic.cable.Cable;
import com.istic.cable.CableController;
import com.istic.port.Port;
import com.istic.util.DragAndDrop;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.*;
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

	private List<ModuleController> moduleControllers;
	private List<CableController> cables;

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
	 * Drop all the modules
	 */
	public void dropAll(){



	}

	/**
	 * Open a configuration
	 */
	public void openConfig(){
        FileChooser fileChooser = new FileChooser();



        fileChooser.setTitle("Open Configuration File");
        //Show open file dialog
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());
        if(file != null){
            try {
                FileReader fileReader;
                List<String> records = new ArrayList<String>();
                fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);
                String line;
                dropAll();
                while ((line = reader.readLine()) != null)
                {

                    String[] strings = line.split(",");

                        if(strings!=null)
                        {
                            //System.out.println(strings[0]);
                            switch (strings[0]){
                                case "out" :
                                    //System.out.println("4");
                                    Node root = FXMLLoader.load(getClass().getResource(
                                            "../../../modules/output.fxml"));
                                    addMod(root);

                                    OUTPUTModuleController outputModuleController = (OUTPUTModuleController) root.getUserData();
                                    this.moduleControllers.add(outputModuleController);
                                    outputModuleController.init(this);
                                    double d =Double.parseDouble(strings[1]);
                                    int i =Integer.parseInt(strings[2]);
                                    System.out.println(i);
                                    outputModuleController.getLineOut().setAttenuation(d);
                                    outputModuleController.getLineOut().setMute(i);
                                    outputModuleController.attenuationSlider.setValue(d);
                                    if(i==0)
                                    outputModuleController.getMute().setSelected(true);
                            }
                        }

                    records.add(line);

                }
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(this.getClass()
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
	/**
	 * Save a configuration
	 */
	public void saveConfig(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration File");

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if(file != null){
            try {
                FileWriter fileWriter;

                fileWriter = new FileWriter(file);
                for(ModuleController moduleController: moduleControllers) {
                    //if(moduleController.getClass().(OUTPUTModuleController))
                    String line = moduleController.getClass().getName();
                    switch (line){
                        case "com.istic.modulesController.OUTPUTModuleController" :
                           OUTPUTModuleController out=(OUTPUTModuleController)moduleController;

                           line="out,"+out.getLineOut().getAttenuation()
                                   +","+out.getLineOut().getMute()+"\n";
                            fileWriter.write(line);

                           default:break;
                    }
                    System.out.println(line);
                	//fileWriter.write(json);
                }
                fileWriter.close();
            } catch (IOException ex) {
                Logger.getLogger(this.getClass()
                        .getName()).log(Level.SEVERE, null, ex);
            }
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

            ButtonType buttonGOLD = new ButtonType("GOLD");
            ButtonType buttonBLUEVIOLET = new ButtonType("BLUEVIOLET");
            ButtonType buttonRED = new ButtonType("RED");
            ButtonType buttonOLIVE = new ButtonType("OLIVE");
            ButtonType buttonSALMON = new ButtonType("SALMON");
            ButtonType buttonSILVER = new ButtonType("SILVER");
            ButtonType buttonMEDIUMAQUAMARINE = new ButtonType("MEDIUMAQUAMARINE");

            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonGOLD,
                    buttonBLUEVIOLET, buttonRED,buttonOLIVE,
                    buttonSALMON,buttonSILVER
                    ,buttonMEDIUMAQUAMARINE, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();

            CableController cableController=null;
            switch (result.get().getText()){
                case "GOLD" :                  cableController = new CableController(pane,cable, Color.GOLD);
                case "BLUEVIOLET" :                 cableController = new CableController(pane,cable, Color.BLUEVIOLET);
                case "RED" :                  cableController = new CableController(pane,cable, Color.RED);
                case "OLIVE" :                 cableController = new CableController(pane,cable, Color.OLIVE);
                case "SALMON" :                 cableController = new CableController(pane,cable, Color.SALMON);
                case "MEDIUMAQUAMARINE" :                 cableController = new CableController(pane,cable, Color.MEDIUMAQUAMARINE);
                default : break;
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


}
