package com.istic.modulesController;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import com.istic.port.Port;

import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import org.json.simple.JSONObject;

public class KBModuleController  extends ModuleController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
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
	public Map<ImageView, Port> getAllPorts() {
		// TODO Auto-generated method stub
		return null;
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
