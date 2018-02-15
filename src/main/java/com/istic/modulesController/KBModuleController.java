package com.istic.modulesController;

import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.istic.eg.EG;
import com.istic.keyboard.KB;
import com.istic.keyboard.KBListener;
import com.istic.port.Port;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

public class KBModuleController  extends ModuleController implements Initializable {
	KB kb;
    @FXML
    TextArea displayArea;
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

	@Override
	public void removeModule() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void restore(JSONObject jsonObjectModule) {

	}

    public void connectGatePort() {
    	System.out.println("gate");
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
    	System.out.println("out");

//        if(!this.eg.getOutput().isConnected()) {
//            currentPort = 0;
//            getLayout(outPort);
//            super.connect();
//        }
    }
    
}
