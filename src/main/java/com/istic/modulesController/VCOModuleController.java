package com.istic.modulesController;


import com.istic.port.Port;
import com.istic.port.PortOutput;
import com.istic.vco.VCO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class VCOModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    Slider frequencySlider;
    @FXML
    Slider frequencyFineSlider;
    @FXML
    RadioButton sawRadio, triangleRadio, squareRadio;
    final ToggleGroup group = new ToggleGroup();
    @FXML
    ImageView outPort;
    @FXML
    ImageView fmPort;
    @FXML
    Label txtHertz;

    protected VCO vco;

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     * <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sawRadio.setToggleGroup(group);
        triangleRadio.setToggleGroup(group);
        squareRadio.setToggleGroup(group);
        squareRadio.setSelected(true);
        Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        frequencySlider.valueProperty().addListener((ov, old_val, new_val) -> {
            frequencySlider.setValue(Math.round(frequencySlider.getValue()));
            vco.changeOctave((int) frequencySlider.getValue());
            Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        });

        frequencyFineSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            //frequencyFineSlider.setValue(Math.round(frequencyFineSlider.getValue()));
            vco.changeFin(frequencyFineSlider.getValue());
            Platform.runLater(() -> txtHertz.setText((Math.round(vco.getFrequence()*100.0) / 100.0) + " Hz"));
        });
        //this.synth.start();
        //vco.start();


    }

    /**
     * Initialise le contrôleur du module et
     * ajoute le module au synthétiseur
     *
     * @param controller controleur general
     */
    public void init(Controller controller) {
        super.init(controller);
        this.vco = new VCO();
        this.controller.getSynth().add(vco);

    }

    /**
     * Connecting the fmPort to draw the cable
     */
    public void connectFmPort() {

        if(!this.vco.getFm().isConnected()) {
            currentPort = 1;
            getLayout(fmPort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.vco.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }



    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return vco.getOutput();
        } else if (currentPort == 1) {
            return vco.getFm();
        }
        return null;
    }

    /**
     * Change la forme du signal en onde carrée
     */
    public void squareSound() {
        vco.changeShapeWave(VCO.SQUAREWAVE);
    }

    /**
     * Change la forme du signal en onde dent de scie
     */
    public void sawSound() {
        vco.changeShapeWave(VCO.SAWWAVE);

    }

    /**
     * Change la forme du signal en onde triangle
     */
    public void triangleSound() {
        vco.changeShapeWave(VCO.TRIANGLEWAVE);

    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
     *
     * @throws IOException si deconnexion impossible
     */
    @Override
    public Map<ImageView, Port> getAllPorts() {
        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, vco.getOutput());
        hashMap.put(fmPort, vco.getFm());
        return hashMap;
    }

    @Override
    public void serialize() {
    	super.serialize();
    	jsonModuleObject.put("frequencySlider", frequencySlider.getValue() );
    	jsonModuleObject.put("frequencyFineSlider", frequencyFineSlider.getValue());
    	jsonModuleObject.put("squareRadio", squareRadio.isSelected());
    	jsonModuleObject.put("sawRadio", sawRadio.isSelected());
    	jsonModuleObject.put("triangleRadio", triangleRadio.isSelected());

    }

    @Override
    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
        double frequencySlider_ = (double) jsonObjectModule.get("frequencySlider");
        double frequencyFineSlider_ = (double) jsonObjectModule.get("frequencyFineSlider");
        boolean squareRadio_ = (boolean) jsonObjectModule.get("squareRadio");
        boolean sawRadio_ = (boolean) jsonObjectModule.get("sawRadio");
        boolean triangleRadio_ = (boolean) jsonObjectModule.get("triangleRadio");
        
        
        frequencySlider.setValue(frequencySlider_);
        frequencyFineSlider.setValue(frequencyFineSlider_);
        squareRadio.setSelected(squareRadio_);
        sawRadio.setSelected(sawRadio_);
        triangleRadio.setSelected(triangleRadio_);
        
        this.vco.changeOctave(frequencySlider_);
        this.vco.changeFin(frequencyFineSlider_);
        int wave = (squareRadio_)? VCO.SQUAREWAVE : (sawRadio_)? VCO.SAWWAVE: VCO.TRIANGLEWAVE;
        this.vco.changeShapeWave(wave);
        
        
    }

    @FXML
    public void removeModule() {
        if(this.controller.getTemporaryCableModuleController()==null) {
            //Deconnexion cable
            Port fm = vco.getFm();
            Port out = vco.getOutput();
            super.disconnect(fm);
            super.disconnect(out);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(vco);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }


}
