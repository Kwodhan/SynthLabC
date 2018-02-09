package com.istic.modulesController;

import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class VCFHPModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;

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
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        return null;
    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML // A decommenter et adapter quand le model vcf HP sera fait !
    public void removeModule() throws IOException {
//        //Deconnexion cables
//        Port gate = vcfhp.getGateInput();
//        Port out = vcfhp.getOutput();
//        super.disconnect(gate);
//        super.disconnect(out);
//        // Deconnexion du module Output du synthetizer
//        this.controller.getSynth().remove(vcfhp);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        StackPane stackPane = (StackPane) pane.getParent();
        // supprime le mod niveau ihm
        stackPane.getChildren().remove(pane);
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        return null;
    }
}
