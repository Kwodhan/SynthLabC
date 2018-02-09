package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.vca.VCA;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.input.InputEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import vcflp.VCFLP;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VCFLPModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    protected VCFLP vcflp;
    @FXML
    ImageView outPort,inPort,fmPort;
    @FXML
    Slider filterSlider,frequencySlider;

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
        filterSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            filterSlider.setValue(Math.round(filterSlider.getValue()));
            vcflp.changeFilter((int) filterSlider.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            frequencySlider.setValue(Math.round(frequencySlider.getValue()));
            vcflp.changeF0((int) frequencySlider.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        switch (currentPort) {

            case 0:
                return vcflp.getOutput();
            case 1:
                return vcflp.getInput();
            case 2 :
                return vcflp.getFm();
            default: return null;
        }
    }


    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, vcflp.getOutput());
        hashMap.put(fmPort, vcflp.getFm());
        hashMap.put(inPort, vcflp.getInput());
        return hashMap;    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML // A decommenter et adapter quand le model vcf LP sera fait !
    public void removeModule() throws IOException {
//        //Deconnexion cable
//        Port gate = vcflp.getGateInput();
//        Port out = vcflp.getOutput();
//        super.disconnect(gate);
//        super.disconnect(out);
//        // Deconnexion du module Output du synthetizer
//        this.controller.getSynth().remove(vcflp);
        // Get parent node of pane corresponding to OutMod
        // Recupere le noeud parent fxml du outmod
        StackPane stackPane = (StackPane) pane.getParent();
        // supprime le mod niveau ihm
        stackPane.getChildren().remove(pane);
    }
    public void init(Controller controller) {
        super.init(controller);
        this.vcflp = new VCFLP();
        this.controller.getSynth().add(vcflp);


    }
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.vcflp.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        if(!this.vcflp.getInput().isConnected()) {
            currentPort = 1;
            getLayout(inPort);
            super.connect();
        }
    }
    /**
     * Connecting the fmPort to draw the cable
     */
    public void connectFmPort() {
        if(!this.vcflp.getFm().isConnected()) {
            currentPort = 2;
            getLayout(fmPort);
            super.connect();
        }
    }
}
