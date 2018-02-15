package com.istic.modulesController;

import java.awt.*;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.istic.eg.EG;
import com.istic.keyboard.KBListener;
import com.istic.port.Port;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

public class KBModuleController  extends ModuleController implements Initializable {
    KBListener kbListener;
    @FXML
    TextArea displayArea;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
        this.kbListener = new KBListener();
        //listener
        //displayArea.addEventHandler(kbListener);
        displayArea.setText(kbListener.getReglageKB().update_ouput_signal());
	}

    public void init(Controller controller) {
        super.init(controller);
        kbListener.add_listener(this.controller.mainPane);
        //this.controller.getSynth().add(kbListener);



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
