package com.istic.modulesController;

import com.istic.keyboard.KB;
import com.istic.port.Port;

import com.istic.util.Style;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class KBModuleController  extends ModuleController implements Initializable,EventHandler<KeyEvent> {


    private KB kb;


    @FXML
    Label text;

    @FXML
    AnchorPane pane;
    
    @FXML
    ImageView gatePort;
    
    @FXML
    ImageView outPort;

    @Override
	public void initialize(URL location, ResourceBundle resources) {


	}

	public void init(Controller controller) {
		super.init(controller);
		this.kb = new KB();
		this.controller.getSynth().add(kb);
        this.text.setText(this.kb.print());
		Style.updateStyleTheme(pane, this.controller.choosedTheme);
	}

	/**
	 * Récupère l'information concernant le port sur lequel l'utilisateur a
	 * cliqué
	 *
	 * @return le port sur lequel l'utilisateur a cliqué côté IHM
	 */
	@Override
	public Port getCurrentPort() {
		if (currentPort == 0) {
			return kb.getCv();
		} else if (currentPort == 1) {
			return kb.getGate();
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
		if (this.controller.getTemporaryCableModuleController() == null) {
			// Deconnexion cables
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
            this.controller.remove(this);
            this.controller.mainPane.setOnKeyPressed(null);
            this.controller.mainPane.setOnKeyReleased(null);
        }
		
	}


	   /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.kb.getCv().isConnected()) {
        	currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    
    /**
     * Connecting the gatePort to draw the cable
     */
    public void connectGatePort() {

        if(!this.kb.getGate().isConnected()) {
        	currentPort = 1;
            getLayout(gatePort);
            super.connect();
        }
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (event.getCode()) {
                case Q:
                    kb.onpressDO();
                    break;
                case S:
                    kb.onpressRE();
                    break;
                case D:
                    kb.onpressMI();
                    break;
                case F:
                    kb.onpressFA();
                    break;
                case G:
                    kb.onpressSOL();
                    break;
                case H:
                    kb.onpressLA();
                    break;
                case J:
                    kb.onpressSI();
                    break;
                case K:
                    kb.onpressDO2();
                    break;


                case Z:
                    kb.onpressDOd();
                    break;
                case E:
                    kb.onpressREd();
                    break;
                case T:
                    kb.onpressFAd();
                    break;
                case Y:
                    kb.onpressSOLd();
                    break;
                case U:
                    kb.onpressLAd();
                    break;

                case X:
                    kb.onpressOctaveUP();
                    break;
                case W:
                    kb.onpressOctaveDOWN();
                    break;

            }
        } else if (event.getEventType() == KeyEvent.KEY_RELEASED) {

            switch (event.getCode()) {
                case Q:
                    kb.onreleaseDO();
                    break;
                case S:
                    kb.onreleaseRE();
                    break;
                case D:
                    kb.onreleaseMI();
                    break;
                case F:
                    kb.onreleaseFA();
                    break;
                case G:
                    kb.onreleaseSOL();
                    break;
                case H:
                    kb.onreleaseLA();
                    break;
                case J:
                    kb.onreleaseSI();
                    break;
                case K:
                    kb.onreleaseDO2();
                    break;


                case Z:
                    kb.onreleaseDOd();
                    break;
                case E:
                    kb.onreleaseREd();
                    break;
                case T:
                    kb.onreleaseFAd();
                    break;
                case Y:
                    kb.onreleaseSOLd();
                    break;
                case U:
                    kb.onreleaseLAd();
                    break;

            }

        }
        this.text.setText(this.kb.print());
    }

    @Override
	public void updateTheme(int i) {
		Style.updateStyleTheme(pane, i);
	}

}
