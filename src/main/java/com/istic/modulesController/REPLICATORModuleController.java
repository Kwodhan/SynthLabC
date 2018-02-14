package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.rep.REP;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class REPLICATORModuleController extends ModuleController implements Initializable {

    protected REP rep;

    @FXML
    AnchorPane pane;
    @FXML
    ImageView outPort1, outPort2, outPort3;
    @FXML
    ImageView inPort;

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
        this.rep = new REP();
        this.controller.getSynth().add(rep);

    }

    /**
     * Connecte le port d'entrée pour tracer le cable
     */
    public void connectInPort() {
        if(!this.rep.getInput().isConnected()) {
            currentPort = 0;
            getLayout(inPort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort1 to draw the cable
     */
    public void connectOutPort1() {
        if(!this.rep.getOutput1().isConnected()) {
            currentPort = 1;
            getLayout(outPort1);
            super.connect();
        }
    }

    /**
     * Connecting the outPort2 to draw the cable
     */
    public void connectOutPort2() {
        if(!this.rep.getOutput2().isConnected()) {
            currentPort = 2;
            getLayout(outPort2);
            super.connect();
        }
    }
    /**
     * Connecting the outPort3 to draw the cable
     */
    public void connectOutPort3() {
        if(!this.rep.getOutput3().isConnected()) {
            currentPort = 3;
            getLayout(outPort3);
            super.connect();
        }
    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        switch (currentPort) {
            case 0:
                return rep.getInput();
            case 1:
                return rep.getOutput1();
            case 2 :
                return rep.getOutput2();
            case 3 :
                return rep.getOutput3();

            default: return null;
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
        if(this.controller.getTemporaryCableModuleController()==null) {
            Port in = rep.getInput();
            Port out1 = rep.getOutput1();
            Port out2 = rep.getOutput2();
            Port out3 = rep.getOutput3();
            super.disconnect(in);
            super.disconnect(out1);
            super.disconnect(out2);
            super.disconnect(out3);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(rep);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(inPort, rep.getInput());
        hashMap.put(outPort1, rep.getOutput1());
        hashMap.put(outPort2, rep.getOutput2());
        hashMap.put(outPort3, rep.getOutput3());
        return hashMap;
    }

    @Override
    public void serialize() {
        super.serialize();
    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
    }
}
