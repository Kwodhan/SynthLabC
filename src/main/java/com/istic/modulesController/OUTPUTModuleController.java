package com.istic.modulesController;

import com.istic.out.OutMod;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OUTPUTModuleController extends ModuleController implements Initializable {

    @FXML
    ImageView inPort;
    @FXML
    AnchorPane pane;
    @FXML
    public Button closeButton;

    private OutMod lineOut;

    @FXML
    private Slider attenuationSlider;

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

        attenuationSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            double newAttenuation = Math.round(attenuationSlider.getValue());
            attenuationSlider.setValue(newAttenuation);
            lineOut.setAttenuation(newAttenuation);
        });
    }


    /**
     * Initialise le contrôleur du module et
     * ajoute le module au synthétiseur
     *
     * @param controller controleur général
     */
    public void init(Controller controller){
        super.init(controller);
        this.lineOut = new OutMod();
        this.controller.getSynth().add(this.lineOut);
        lineOut.start();
    }

    /**
     * Connecte le port d'entrée pour tracer le cable
     */
    public void connect() {
        if(!this.lineOut.getPortInput().isConnected()) {
            super.getLayout(inPort);
            super.connect();
        }
    }

    /**
     * Gère la fonctionnalité "Muet"
     * Coupe le son
     */
    public void toggleMute() {
        this.lineOut.toggleMute();
    }


    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    public Port getCurrentPort(){
        if(!this.lineOut.getPortInput().isConnected()) {
            return lineOut.getPortInput();
        }
        return null;
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(inPort, lineOut.getPortInput());
        return hashMap;
    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML
    public void removeModule() {
        //Deconnexion cables
        Port port = lineOut.getPortInput();
        super.disconnect(port);
        // Deconnexion du module Output du synthetizer
        this.controller.getSynth().remove(lineOut);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        StackPane stackPane = (StackPane) pane.getParent();
        // supprime le mod niveau ihm
        stackPane.getChildren().remove(pane);
        this.controller.disconnect(this);
    }

}
