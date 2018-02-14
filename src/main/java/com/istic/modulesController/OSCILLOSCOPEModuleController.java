package com.istic.modulesController;

import com.istic.oscillo.Oscilloscope;
import com.istic.port.Port;
import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class OSCILLOSCOPEModuleController extends ModuleController implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */

    private Oscilloscope oscilloscope;

    @FXML
    AnchorPane pane;

    @FXML
    ImageView outPort;

    @FXML
    ImageView inPort;

    @FXML
    Pane paneOscilloscope;

    SwingNode swingNode;

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
     * @param controller controleur général
     */
    public void init(Controller controller) {
        super.init(controller);
        this.oscilloscope = new Oscilloscope(controller.getSynth());

        final SwingNode swingNode = new SwingNode();
        oscilloscope.start();
        createSwingContent(swingNode, oscilloscope.getView());
        paneOscilloscope.getChildren().add(swingNode);

    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return this.oscilloscope.getOutput();
        } else if (currentPort == 1) {
            return this.oscilloscope.getInput();
        }
        return null;
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, oscilloscope.getOutput());
        hashMap.put(inPort, oscilloscope.getInput());
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

    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        if(!this.oscilloscope.getInput().isConnected()) {
            currentPort = 1;
            getLayout(inPort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.oscilloscope.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }

    /**
     * Display JPanel component in JavaFX
     * @param swingNode node where add component
     * @param audioScopeView JPanel from JSyn display Oscilloscope
     */
    private void createSwingContent(final SwingNode swingNode, AudioScopeView audioScopeView) {
        Dimension d = new Dimension(260, 105);
        audioScopeView.setMaximumSize(d);
        audioScopeView.setMinimumSize(d);
        audioScopeView.setPreferredSize(d);
        this.swingNode = swingNode;
        SwingUtilities.invokeLater(() -> swingNode.setContent(audioScopeView));
    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML
    public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            //Deconnexion cables
            Port in = oscilloscope.getInput();
            Port out = oscilloscope.getOutput();
            super.disconnect(in);
            super.disconnect(out);
            // Deconnexion du module Output du synthetizer
            //this.controller.getSynth().remove(oscilloscope);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }
    @Override
    public void updateCablesPosition() {
       super.updateCablesPosition();
        createSwingContent(swingNode, oscilloscope.getView());
    }

}
