package com.istic.modulesController;

import com.istic.port.Port;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import com.istic.vcflp.VCFLP;
import org.json.simple.JSONObject;

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
    Slider resonanceSlider,frequencySlider;

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
        resonanceSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            Platform.runLater(() ->this.vcflp.setResonance(resonanceSlider.getValue()));

        });

        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            this.vcflp.setF0(Math.pow(2,frequencySlider.getValue()));
            Platform.runLater(() ->this.frequence.setText((Math.round(this.vcflp.getFrequence()*100.0) / 100.0) + " Hz"));

        });



    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        switch (currentPort) {
            case 1:
                return vcflp.getInput();
            case 0:
                return vcflp.getOutput();
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

    @Override
    public void serialize() {
        super.serialize();
        jsonModuleObject.put("frequencySlider", Math.pow(2,frequencySlider.getValue()));
        jsonModuleObject.put("resonanceSlider", resonanceSlider.getValue());

    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
    setJsonModuleObject(jsonObjectModule);
        double frequency = (double) jsonObjectModule.get("frequencySlider");
        double resonance = (double) jsonObjectModule.get("resonanceSlider");
        //model
        this.vcflp.setF0(frequency);
        this.vcflp.setResonance(resonance);

        //graphique
        frequencySlider.setValue(frequency);
        resonanceSlider.setValue(resonance);

    }

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
            Port fm = this.vcflp.getFm();
            Port out = this.vcflp.getOutput();
            Port in = this.vcflp.getInput();
            super.disconnect(fm);
            super.disconnect(out);
            super.disconnect(in);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(vcflp);
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
        this.vcflp = new VCFLP();
        this.controller.getSynth().add(vcflp);
        this.frequencySlider.setValue(frequencySlider.getMax());

        this.vcflp.setF0(Math.pow(2,frequencySlider.getValue()));
        this.vcflp.setResonance(resonanceSlider.getValue());

        this.frequence.setText((Math.round(this.vcflp.getFrequence()*100.0) / 100.0) + " Hz");

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
