package com.istic.modulesController;

import com.istic.keyboard.KB;
import com.istic.port.Port;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class KBModuleController  extends ModuleController implements Initializable {
	private KB kb;
    @FXML
    TextArea displayArea;
    @FXML
    AnchorPane pane;
    
    @FXML
    ImageView gatePort;
    
    @FXML
    ImageView outPort;

    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        //this.kbListener = new KBListener();
        //listener
        //displayArea.addEventHandler(kbListener);
        displayArea.setStyle("-fx-line-spacing: 0.5em;");
        //displayArea.setText(kbListener.getReglageKB().update_ouput_signal());
        displayArea.setText("CLAVIER DE PIANO");
        displayArea.setEditable(false);
         //displayArea.setFocusTraversable(false);
	}

    public void init(Controller controller) {
        super.init(controller);
        this.kb= new KB(displayArea);
        //kbListener.add_listener(this.controller.pane);
        //this.controller.getSynth().add(kbListener);

        displayArea.setOnKeyPressed(kb.kblistener);
        displayArea.setOnKeyReleased(kb.kblistener);


    }
    
    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return kb.getOutputPort();
        } else if (currentPort == 1) {
            return kb.getOutputGate();
        }
        return null;
    }
    
    
    @Override
    public void serialize() {
    	super.serialize();
    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
    }


    @FXML
	public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            //Deconnexion cables
            Port gate = kb.getOutputGate();
            Port out = kb.getOutputPort();

            super.disconnect(gate);
            super.disconnect(out);

            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(kb);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
		
	}


	   /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.kb.getOutputPort().isConnected()) {
        	currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    
    /**
     * Connecting the gatePort to draw the cable
     */
    public void connectGatePort() {

        if(!this.kb.getOutputGate().isConnected()) {
        	currentPort = 1;
            getLayout(gatePort);
            super.connect();
        }
    }
    
}
