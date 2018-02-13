package com.istic.modulesController;

import com.istic.eg.EG;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


public class EGModuleController extends ModuleController implements Initializable {
	
	protected EG eg;

    @FXML
    AnchorPane pane;
	@FXML
    ImageView outPort;
    @FXML
    ImageView gatePort;
    @FXML
    Slider attackSlider;
    @FXML
    Slider decaySlider;
    @FXML
    Slider sustainSlider;
    @FXML
    Slider releaseSlider;

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

        attackSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setAttack(4*Math.exp(attackSlider.getValue()-9));
        });

        releaseSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setRelease(4*Math.exp(releaseSlider.getValue()-9));
        });

        decaySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setDecay(4*Math.exp(decaySlider.getValue()-9));
        });

        sustainSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.eg.setSustain(sustainSlider.getValue()/10);
        });



    }

    /**
     * Initialise le contrôleur du module et
     * ajoute le module au synthétiseur
     *
     * @param controller controleur général
     */
    public void init(Controller controller) {
        super.init(controller);
        this.eg = new EG();
        this.controller.getSynth().add(eg);

        attackSlider.setValue(attackSlider.getMin());
        releaseSlider.setValue(releaseSlider.getMin());
        decaySlider.setValue(decaySlider.getMin());
        sustainSlider.setValue(sustainSlider.getMin());

    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return eg.getOutput();
        } else if (currentPort == 1) {
            return eg.getGateInput();
        }
        return null;
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, eg.getOutput());
        hashMap.put(gatePort, eg.getGateInput());
        return hashMap;
    }
    
    /**
     * Connecte le port Gate pour tracer le cable
     */
    public void connectGatePort() {
        if(!this.eg.getGateInput().isConnected()) {
            currentPort = 1;
            getLayout(gatePort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.eg.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML
    public void removeModule() {
        //Deconnexion cable
        if(this.controller.getTemporaryCableModuleController() == null) {
            Port gate = eg.getGateInput();
            Port out = eg.getOutput();
            super.disconnect(gate);
            super.disconnect(out);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(eg);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }

}
