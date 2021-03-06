package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.port.PortController;
import com.istic.sequencer.Sequenceur;
import com.istic.util.Style;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SEQUENCERModuleController extends ModuleController implements Initializable {


    @FXML
    AnchorPane pane;
    
    @FXML
    ImageView gatePort;
    
    @FXML
    ImageView outPort;
    
    @FXML
    Slider sliderSeq1,sliderSeq2,sliderSeq3,sliderSeq4,sliderSeq5,sliderSeq6,sliderSeq7,sliderSeq8;
    
    Slider[] sliders;
    
    Sequenceur sequenceur;

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
    	
        sliders = new Slider[]{ sliderSeq1, sliderSeq2,sliderSeq3,sliderSeq4,sliderSeq5
        		,sliderSeq6,sliderSeq7,sliderSeq8};
    	
    	for(int i =0 ;i<sliders.length;i++){
    		final int j = i;
    		sliders[i].valueProperty().addListener((ov, old_val, new_val) -> {
            double value = sliders[j].getValue();
            sliders[j].setValue(value);
            sequenceur.setValue(value,j);

        });
    	}
    	
    }

    
    /**
     * Initialise le contrôleur du module et
     * ajoute le module au synthétiseur
     *
     * @param controller controleur general
     */
    public void init(Controller controller) {
        super.init(controller);
        this.sequenceur = new Sequenceur();
        this.controller.getSynth().add(sequenceur);
        this.portControllers.add(new PortController(this.sequenceur.getGatePort(),this.gatePort));
        this.portControllers.add(new PortController(this.sequenceur.getOutputPort(),this.outPort));
    	Style.updateStyleTheme(pane, this.controller.choosedTheme);
    }
    
    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return sequenceur.getOutputPort();
        } else if (currentPort == 1) {
            return sequenceur.getGatePort();
        }
        return null;
    }


    
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.sequenceur.getOutput().isConnected()) {
        	currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    
    /**
     * Connecting the gatePort to draw the cable
     */
    public void connectGatePort() {

        if(!this.sequenceur.getGatePort().isConnected()) {
        	currentPort = 1;
            getLayout(gatePort);
            super.connect();
        }
    }
    
    

    @Override
    public void serialize() {
    	super.serialize();
    	for (int i = 0; i<sliders.length;i++){
    		jsonModuleObject.put("sliderseq"+(i+1), sliders[i].getValue());
    	}

    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
        double val;
    	for (int i = 0; i<8;i++){
    		val = (double) jsonObjectModule.get("sliderseq"+(i+1));
    		sliders[i].setValue(val);
    	}

    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     */
    @FXML
    public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
        	Port port1 = sequenceur.getOutputPort();
        	Port port2 = sequenceur.getGatePort();
            super.disconnect(port1);
            super.disconnect(port2);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(sequenceur);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.remove(this);
        }
    }


	@Override
	public void updateTheme(int i) {
		Style.updateStyleTheme(pane, i);
	}
}