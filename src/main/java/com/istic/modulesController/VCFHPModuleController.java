package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.vcfhp.VCFHP;
import com.istic.vcflp.VCFLP;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VCFHPModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;

    protected VCFHP vcfhp;

    @FXML
    ImageView outPort,inPort,fmPort;

    @FXML
    Slider frequencySlider;

    @FXML
    Label frequence;
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


        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.vcfhp.setF0(Math.pow(2,frequencySlider.getValue()));
            Platform.runLater(() ->this.frequence.setText((Math.round(this.vcfhp.getFrequence()*100.0) / 100.0) + " Hz"));

        });

//        resonanceSlider.setValue(resonanceSlider.getMin());



    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        switch (currentPort) {
            case 0:
                return vcfhp.getOutput();
            case 1:
                return vcfhp.getInput();
            case 2 :
                return vcfhp.getFm();
            default: return null;
        }
    }


    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(fmPort, vcfhp.getFm());
        hashMap.put(outPort, vcfhp.getOutput());
        hashMap.put(inPort, vcfhp.getInput());
        return hashMap;    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @FXML // A decommenter et adapter quand le model vcf LP sera fait !
    public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            //Deconnexion cable
            Port fm = this.vcfhp.getFm();
            Port out = this.vcfhp.getOutput();
            Port in = this.vcfhp.getInput();
            super.disconnect(fm);
            super.disconnect(out);
            super.disconnect(in);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(vcfhp);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }
    public void init(Controller controller) {
        super.init(controller);
        this.vcfhp = new VCFHP();
        this.controller.getSynth().add(vcfhp);


        this.vcfhp.setF0(Math.pow(2,frequencySlider.getValue()));

        this.frequence.setText((Math.round(this.vcfhp.getFrequence()*100.0) / 100.0) + " Hz");


    }
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.vcfhp.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        if(!this.vcfhp.getInput().isConnected()) {
            currentPort = 1;
            getLayout(inPort);
            super.connect();
        }
    }
    /**
     * Connecting the fmPort to draw the cable
     */
    public void connectFmPort() {
        if(!this.vcfhp.getFm().isConnected()) {
            currentPort = 2;
            getLayout(fmPort);
            super.connect();
        }
    }

    @Override
    public void serialize() {
    super.serialize();
        jsonCableObject.put("frequencySlider", Math.pow(2,frequencySlider.getValue()));


    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
    setJsonCableObject(jsonObjectModule);
        double frequency = (double) jsonObjectModule.get("frequencySlider");
        //model
        vcfhp.setF0(frequency);
        //graphique
        frequencySlider.setValue(frequency);
    }
}
