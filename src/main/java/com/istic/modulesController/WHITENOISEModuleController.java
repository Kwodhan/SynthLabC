package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.whitenoise.BruitBlanc;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class WHITENOISEModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    
    BruitBlanc bruitBlanc;
    
    @FXML
    ImageView outPort;

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
    }
    
    /**
     * Initialise le contrôleur du module et
     * ajoute le module au synthétiseur
     *
     * @param controller controleur general
     */
    public void init(Controller controller) {
        super.init(controller);
        this.bruitBlanc = new BruitBlanc();
        this.controller.getSynth().add(bruitBlanc);
    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    public Port getCurrentPort(){
        if(!this.bruitBlanc.getOutputPort().isConnected()) {
            return bruitBlanc.getOutputPort();
        }
        return null;
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
    	Map<ImageView, Port> hm = new HashMap<>();
    	hm.put(outPort, bruitBlanc.getOutputPort());
        return hm;
    }

    @Override
    public void serialize() {
        super.serialize();
    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
    setJsonCableObject(jsonObjectModule);
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.bruitBlanc.getOutput().isConnected()) {
            getLayout(outPort);
            super.connect();
        }
    }


    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     */
    @FXML
    public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            Port port = bruitBlanc.getOutputPort();
            super.disconnect(port);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(bruitBlanc);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }


    }

    /**
     * getter utilisé par l'IHM pour recuperer le port de sortie
     * @return port de sortie
     */
    public PortOutput getOutPort() {
        return this.bruitBlanc.getOutputPort();
    }

}
