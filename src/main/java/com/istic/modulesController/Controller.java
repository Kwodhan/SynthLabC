package com.istic.modulesController;

import com.istic.cable.Cable;
import com.istic.cable.CableController;
import com.istic.port.Port;
import com.istic.util.DragAndDrop;
import com.istic.util.Files;
import com.istic.util.Style;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Pair;

import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Grand controller de l'application
 */
public class Controller implements Initializable {

    @FXML
    AnchorPane mainPane;

    @FXML
    AnchorPane boxpane;

    @FXML
    StackPane box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12;

    @FXML
    RadioMenuItem woodMenuItem, metalMenuItem, defaultMenuItem;

    @FXML
	RadioMenuItem cableColorGoldMenuItem, cableColorRedMenuItem, cableColorLightGreenMenuItem, cableColorBluevioletMenuItem;

    /**
     * Choix du Skin de la fenetre
     */
    final ToggleGroup groupSkin;
    static final int WOOD = 1;
    static final int METAL = 0;
    static final int BASIC = 2;
    /**
     * Choix de la couleur des cables
     */
    final ToggleGroup groupToggleCableColor;

    private StackPane[] stacks;

    /**
     * Couleur à applicer sur les futurs cables
     */
    private Color cableColor;
    /**
     * Liste des modules sur le board
     */
    private ArrayList<ModuleController> moduleControllers;

    /**
     * Liste des calbes sur le board
     */
    private ArrayList<CableController> cables;

    private Synthesizer synth;
    /**
     * La ligne qui s'affiche lorsqu'on tente un cablage
     */
    private Line mouseLine;

    /**
     * Module temporaire lors du cablage
     */
    private ModuleController temporaryCableModuleController;

    /**
     * Savoir si nous sommes entrain de cabler un nouveau cable
     */
    private boolean isPlugged = false;

    /**
     * Id fxml des cables. S'incremente a chaque création
     */
    private Integer cableId = 1;

    /**
     * Id fxml des modules. S'incremente a chaque création
     */
    private Integer moduleId = 1;


    /**
     * Réalise la gestion du drag and drop des modules
     */
    private DragAndDrop dragAndDrop;

    protected int choosedTheme = BASIC;


    public Controller() {
        groupSkin = new ToggleGroup();
        groupToggleCableColor = new ToggleGroup();
    }


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

        // Skin
        metalMenuItem.setToggleGroup(groupSkin);
        woodMenuItem.setToggleGroup(groupSkin);
        defaultMenuItem.setToggleGroup(groupSkin);
        groupSkin.selectToggle(defaultMenuItem);

        // Cable color
		cableColorGoldMenuItem.setToggleGroup(groupToggleCableColor);
		cableColorRedMenuItem.setToggleGroup(groupToggleCableColor);
		cableColorLightGreenMenuItem.setToggleGroup(groupToggleCableColor);
		cableColorBluevioletMenuItem.setToggleGroup(groupToggleCableColor);
		groupToggleCableColor.selectToggle(cableColorBluevioletMenuItem);

		this.dragAndDrop = new DragAndDrop(this);

        this.mouseLine = new Line();
        this.mouseLine.setVisible(false);
        this.mouseLine.setStrokeWidth(3);

        this.moduleControllers = new ArrayList<>();
        this.cables = new ArrayList<>();

        mainPane.getChildren().add(mouseLine);
        mainPane.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            int x = (this.mouseLine.getStartX() > event.getX() ? 2 : -2);
            int y = (this.mouseLine.getStartY() > event.getY() ? 2 : -2);
            this.mouseLine.setEndX(event.getX() + x);
            this.mouseLine.setEndY(event.getY() + y);
        });

        stacks = new StackPane[]{box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11, box12};

        cableColor = Color.BLUEVIOLET;
        //make stackpane handle drop
        for (StackPane s : stacks) {
            this.dragAndDrop.addDropHandling(s);
        }

		try {
			addOutput();
		} catch (IOException e) {
			e.printStackTrace();
		}
		defaultTheme();
    }

    /**
     * Supprime tous les modules sur le board
     */
    @SuppressWarnings("unchecked")
    public void dropAll() {

        ArrayList<ModuleController> mod;
        mod = (ArrayList<ModuleController>) moduleControllers.clone();
        for (ModuleController moduleController : mod) {
            moduleController.removeModule();
        }
        //files.parseJSON();

        moduleControllers.clear();
    }

    /**
     * Ouvre une configuration
     * @throws IOException erreur Input/Output fichier
     * @throws ParseException erreur parsage fichier de config
     */
    public void openConfig() throws IOException, ParseException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Configuration File");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Show open file dialog
        File file = fileChooser.showOpenDialog(mainPane.getScene().getWindow());
        if (file != null) {
            dropAll();
            Files files = new Files(file, this);
            files.open();
        }
    }

    /**
     * Sauvegarde une configuration
     * @throws IOException In/Out erreur
     */
    public void saveConfig() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Configuration File");
        fileChooser.setInitialFileName("conf.json");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(mainPane.getScene().getWindow());

        if (file != null) {
            Files files = new Files(file, this);
            files.save();
        }
    }

    /**
     * Fenetre qui s'ouvre lors de la sauvegarde d'un fichier son
     * @return Destination du fichier et son extension
     */
	public Pair<File, String> saveSound(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Sound File");
        fileChooser.setInitialFileName("mySound");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("WAV files (*.wav)", "*.wav");
        FileChooser.ExtensionFilter extFiltermp3 = new FileChooser.ExtensionFilter("MP3 files (*.mp3)", "*.mp3");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.getExtensionFilters().add(extFiltermp3);

        //Show save file dialog
        File dest = fileChooser.showSaveDialog(mainPane.getScene().getWindow());
        String ext;
        //System.outPort.println("extension : " +fileChooser.getSelectedExtensionFilter().getExtensions());
        if (fileChooser.getSelectedExtensionFilter() != null) {
            ext = fileChooser.getSelectedExtensionFilter().getExtensions().get(0);
        } else {
            ext = null;
        }
        //System.outPort.println(ext);
        Pair<File, String> info_dest = new Pair<>(dest, ext);
        return info_dest;
    }

    /**
     * Change le thème en default
     */
    public void defaultTheme() {
    	choosedTheme = BASIC;
    	Style.updateStyle(boxpane, BASIC);
    	for(ModuleController m: moduleControllers){
    		m.updateTheme(BASIC);
    	}
    }

    /**
     * Change le thème en dark
     */
    public void metalTheme() {
    	choosedTheme = METAL;
    	Style.updateStyle(boxpane, METAL);
    	for(ModuleController m: moduleControllers){
    		m.updateTheme(METAL);
    	}
    }

    /**
     * Change le thème en wood
     */
    public void woodTheme() {
    	choosedTheme = WOOD;
    	Style.updateStyle(boxpane, WOOD);
    	for(ModuleController m: moduleControllers){
    		m.updateTheme(WOOD);
    	}
    }


    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCO sur le board
     * @return contrôleur du module VCO
     * @throws IOException si ajout impossible
     */
    public VCOModuleController addVco() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/vco.fxml"));

        if(addMod(root)) {
            VCOModuleController vcoModuleController = (VCOModuleController) root.getUserData();
            this.moduleControllers.add(vcoModuleController);
            vcoModuleController.init(this);
            vcoModuleController.setRoot(root);
            return vcoModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Output sur le board
     * @return contrôleur du module Output
     * @throws IOException si ajout impossible
     */
    public OUTPUTModuleController addOutput() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/output.fxml"));
        if(addMod(root)) {

            OUTPUTModuleController outputModuleController = (OUTPUTModuleController) root.getUserData();
            this.moduleControllers.add(outputModuleController);
            outputModuleController.init(this);
            outputModuleController.setRoot(root);
            return outputModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Mixer sur le board
     * @return contrôleur du module Mixer
     * @throws IOException si ajout impossible
     */
    public MIXERModuleController addMixer() throws IOException {

        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/mixer.fxml"));
        if(addMod(root)) {

            MIXERModuleController mixerModuleController = (MIXERModuleController) root.getUserData();
            this.moduleControllers.add(mixerModuleController);
            mixerModuleController.init(this);
            mixerModuleController.setRoot(root);
            return mixerModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Mixer sur le board
     * @return contrôleur du module Keyboard
     * @throws IOException si ajout impossible
     */
    public KBModuleController addKeyBoard() throws IOException {

        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/keyboard.fxml"));
        if(addMod(root)) {

            KBModuleController kbModuleController = (KBModuleController) root.getUserData();
            this.moduleControllers.add(kbModuleController);
            kbModuleController.init(this);
            kbModuleController.setRoot(root);
            this.mainPane.setOnKeyReleased(kbModuleController);
            this.mainPane.setOnKeyPressed(kbModuleController);
            return kbModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module EG sur le board
     * @return contrôleur du module EG
     * @throws IOException si ajout impossible
     */
    public EGModuleController addEG() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/eg.fxml"));
        if(addMod(root)) {

            EGModuleController egModuleController = (EGModuleController) root.getUserData();
            this.moduleControllers.add(egModuleController);
            egModuleController.init(this);
            egModuleController.setRoot(root);
            return egModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Oscilloscope sur le board
     * @return contrôleur du module Oscilloscope
     * @throws IOException si ajout impossible
     */
    public OSCILLOSCOPEModuleController addOscilloscope() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/oscilloscope.fxml"));

        if(addMod(root)) {
            OSCILLOSCOPEModuleController oscilloscopeModuleController = (OSCILLOSCOPEModuleController) root.getUserData();
            this.moduleControllers.add(oscilloscopeModuleController);
            oscilloscopeModuleController.init(this);

            oscilloscopeModuleController.setRoot(root);
            return oscilloscopeModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Réplicateur sur le board
     * @return contrôleur du module Replicator
     * @throws IOException si ajout impossible
     */
    public REPLICATORModuleController addReplicator() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/replicator.fxml"));
        if(addMod(root)) {

            REPLICATORModuleController replicatorModuleController = (REPLICATORModuleController) root.getUserData();
            this.moduleControllers.add(replicatorModuleController);
            replicatorModuleController.init(this);
            replicatorModuleController.setRoot(root);
            return replicatorModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module Séquenceur sur le board
     * @return contrôleur du module sequenceur
     * @throws IOException si ajout impossible
     */
    public SEQUENCERModuleController addSequencer() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/sequencer.fxml"));
        if(addMod(root)) {

            SEQUENCERModuleController seqModuleController = (SEQUENCERModuleController) root.getUserData();
            this.moduleControllers.add(seqModuleController);
            seqModuleController.init(this);
            seqModuleController.setRoot(root);
            return seqModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCA sur le board
     * @return contrôleur du module VCA
     * @throws IOException si ajout impossible
     */
    public VCAModuleController addVca() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/vca.fxml"));
        if(addMod(root)) {

            VCAModuleController vcaModuleController = (VCAModuleController) root.getUserData();
            this.moduleControllers.add(vcaModuleController);
            vcaModuleController.init(this);
            vcaModuleController.setRoot(root);
            return vcaModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCF LP sur le board
     * @return contrôleur du module VCF LP
     * @throws IOException si ajout impossible
     */
    public VCFLPModuleController addVcfLp() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/vcfLp.fxml"));
        if(addMod(root)) {
            VCFLPModuleController vcflpModuleController = (VCFLPModuleController) root.getUserData();
            this.moduleControllers.add(vcflpModuleController);
            vcflpModuleController.init(this);
            vcflpModuleController.setRoot(root);
            return vcflpModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module VCF HP sur le board
     * @return contrôleur du module VCF HP
     * @throws IOException si ajout impossible
     */
    public VCFHPModuleController addVcfHp() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/vcfHp.fxml"));
        if(addMod(root)) {
            VCFHPModuleController vcfhpModuleController = (VCFHPModuleController) root.getUserData();
            this.moduleControllers.add(vcfhpModuleController);
            vcfhpModuleController.init(this);
            vcfhpModuleController.setRoot(root);
            return vcfhpModuleController;
        }
        return null;
    }

    /**
     * Crée les objets nécessaires pour l'apparition d'un module bruit blanc sur le board
     * @return contrôleur du module White noise
     * @throws IOException si ajout impossible
     */
    public WHITENOISEModuleController addWhiteNoise() throws IOException {
        Node root = FXMLLoader.load(getClass().getClassLoader().getResource(
                "modules/whiteNoise.fxml"));
        if(addMod(root)) {
            WHITENOISEModuleController whiteModuleController = (WHITENOISEModuleController) root.getUserData();
            this.moduleControllers.add(whiteModuleController);
            whiteModuleController.init(this);
            whiteModuleController.setRoot(root);
            return whiteModuleController;
        }
        return null;
    }


    /**
     * Fonction appelé lors de l'ajout d'un cable
     * @param moduleController contrôleur du module qu'il faut connecter
     * @return contrôleur du cable
     */
	public CableController connect(ModuleController moduleController) {
		Cable cable = new Cable(this.temporaryCableModuleController.getCurrentPort(),moduleController.getCurrentPort());
        CableController cableController = null;
		if (cable.connect()) {
			cableController = new CableController(this,mainPane, cable, getCableColor());
			cableController.drawCable(this.temporaryCableModuleController, moduleController,cableId++);
			this.cables.add(cableController);
		}
		return cableController;
	}

    /**
     * Fonction appelé lors de l'ajout d'un cable lors d'un chargement d'un fichier de configuration
     * @param port1 port 1 à connecter
     * @param port2 port 2 à connecter
     * @param moduleController1 contrôleur 1 du module qu'il faut connecter
     * @param moduleController2 contrôleur 2 du module qu'il faut connecter
     * @return contrôleur du cable
     */
    public CableController connect(Port port1, Port port2,ModuleController moduleController1,ModuleController moduleController2) {
        Cable cable = new Cable(port1,port2);
        CableController cableController = null;
        if (cable.connect()) {
            cableController = new CableController(this,mainPane, cable, getCableColor());
            cableController.drawCable(moduleController1, moduleController2,cableId++);
            this.cables.add(cableController);
        }
        return cableController;
    }

    /**
     * Supprime un module controller de la liste du controller
     *
     * @param moduleController controleur du module à supprimer
     */
    public void remove(ModuleController moduleController) {
        this.moduleControllers.remove(moduleController);
    }

    /**
     *
     * Ajoute un module sur le board
     *
     * @param root noeud du module à ajouter au board
     * @return true si reussi à ajouter un module | false si il n'a pas reussi
     */
    private boolean addMod(Node root) {


        for (StackPane s : stacks) {

            if (s.getChildren().isEmpty()) {
                s.getChildren().add(root);
                root.setId("module-" + moduleId++);
                this.dragAndDrop.dragNode(root);
                return true;
            }
        }
        return false;
    }

    /**
     * Ajoute des modules sur le board lors d'un chargement de configuration
     * @param positions position des modules de chaque controller dans la StackPane
     * @param moduleControllers liste des controllers
     */
    public void addMod(int[] positions, ArrayList<ModuleController> moduleControllers) {
        int i=0;
        for (StackPane s : stacks) {
            s.getChildren().clear();
        }
        for (ModuleController moduleController : moduleControllers) {

            moduleController.getRoot().setId("module-" + moduleId++);
            stacks[positions[i]].getChildren().clear();
            stacks[positions[i]].getChildren().add(moduleController.getRoot());
            this.dragAndDrop.dragNode(moduleController.getRoot());
            i++;
        }
    }

    /**
     * Change cable color
     * @param event listener clicking on menu to select color
     */
    public void selectCableColor(ActionEvent event) {
        RadioMenuItem menu = (RadioMenuItem) event.getSource();
        String color = (String) menu.getUserData();

        cableColor = Color.valueOf(color.toUpperCase());
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

    public ArrayList<CableController> getCables() {
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

	/**
	 * @return Color for cable
	 */
    private Color getCableColor() {
		return cableColor;
	}



}
