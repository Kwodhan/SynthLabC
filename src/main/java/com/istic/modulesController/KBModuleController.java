package com.istic.modulesController;

import com.istic.keyboard.KB;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
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
	@Override
	public Port getCurrentPort() {
		// TODO Auto-generated method stub
		return null;
	}

    @FXML
	public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            //Deconnexion cables
            Port gate = kb.getGate();
            Port out = kb.getCv();

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


	@Override
	public void restore(JSONObject jsonObjectModule) {

	}

    public void connectGatePort() {

//        if(!this.eg.getGateInput().isConnected()) {
//            currentPort = 1;
//            getLayout(gatePort);
//            super.connect();
//        }
//        serialize();
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {


//        if(!this.eg.getOutput().isConnected()) {
//            currentPort = 0;
//            getLayout(outPort);
//            super.connect();
//        }
    }
    
}
