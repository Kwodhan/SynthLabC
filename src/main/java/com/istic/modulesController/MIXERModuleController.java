package com.istic.modulesController;

import com.istic.mixer.MIXER;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MIXERModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    ImageView inPort1,inPort2,inPort3,inPort4,outPort;
    @FXML
    Slider amplitudeSlider1,amplitudeSlider2,amplitudeSlider3,amplitudeSlider4;

    MIXER mixer;

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
        amplitudeSlider1.valueProperty().addListener((ov, old_val, new_val) -> {
            mixer.changeAtt1( amplitudeSlider1.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider2.valueProperty().addListener((ov, old_val, new_val) -> {
            mixer.changeAtt2( amplitudeSlider2.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider3.valueProperty().addListener((ov, old_val, new_val) -> {
            mixer.changeAtt3( amplitudeSlider3.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

        });
        amplitudeSlider4.valueProperty().addListener((ov, old_val, new_val) -> {
            mixer.changeAtt4( amplitudeSlider4.getValue());
            //txtHertz.setText(Math.round(vco.getFrequence()) + " Hz");

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
        this.mixer = new MIXER();
        this.controller.getSynth().add(mixer);

        mixer.changeAtt1(amplitudeSlider1.getValue());
        mixer.changeAtt2(amplitudeSlider2.getValue());
        mixer.changeAtt3(amplitudeSlider3.getValue());
        mixer.changeAtt4(amplitudeSlider4.getValue());
    }

    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    @Override
    public Port getCurrentPort() {
        switch (currentPort) {
            case 0:
                return mixer.getOutput();
            case 1:
                return mixer.getInput1();
            case 2 :
                return mixer.getInput2();
            case 3 :
                return mixer.getInput3();
            case 4 :
                return mixer.getInput4();

            default: return null;
        }
    }


    @Override
    public Map<ImageView, Port> getAllPorts() {

        Map<ImageView, Port> hashMap = new HashMap<>();
        hashMap.put(outPort, mixer.getOutput());
        hashMap.put(inPort1, mixer.getInput1());
        hashMap.put(inPort2, mixer.getInput2());
        hashMap.put(inPort3, mixer.getInput3());
        hashMap.put(inPort4, mixer.getInput4());

        return hashMap;
    }

    @Override
    public void serialize() {
        super.serialize();
        jsonCableObject.put("amplitudeSlider1", amplitudeSlider1.getValue());
        jsonCableObject.put("amplitudeSlider2", amplitudeSlider2.getValue());
        jsonCableObject.put("amplitudeSlider3", amplitudeSlider3.getValue());
        jsonCableObject.put("amplitudeSlider4", amplitudeSlider4.getValue());

    }

    /**
     * Restaure la configuration à partir de l'objet JSON
     * @param jsonObjectModule configuration à charger
     */
    @Override
    public void restore(JSONObject jsonObjectModule) {
        double amplitude1 = (double) jsonObjectModule.get("amplitudeSlider1");
        double amplitude2 = (double) jsonObjectModule.get("amplitudeSlider2");
        double amplitude3 = (double) jsonObjectModule.get("amplitudeSlider3");
        double amplitude4 = (double) jsonObjectModule.get("amplitudeSlider4");

        //model
        mixer.changeAtt1( amplitude1);
        mixer.changeAtt2( amplitude2);
        mixer.changeAtt3( amplitude3);
        mixer.changeAtt4( amplitude4);
        //ui
        amplitudeSlider1.setValue(amplitude1);
        amplitudeSlider2.setValue(amplitude2);
        amplitudeSlider3.setValue(amplitude3);
        amplitudeSlider4.setValue(amplitude4);
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {

        if(!this.mixer.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }

    /**
     * Connecte le port d'entrée 1 pour tracer le cable
     */
    public void connectInPort1() {
        if(!this.mixer.getInput1().isConnected()) {
            currentPort = 1;
            getLayout(inPort1);
            super.connect();
        }
    }

    /**
     * Connecte le port d'entrée 2 pour tracer le cable
     */
    public void connectInPort2() {

        if(!this.mixer.getInput2().isConnected()) {
            currentPort = 2;
            getLayout(inPort2);
            super.connect();
        }
    }

    /**
     * Connecte le port d'entrée 3 pour tracer le cable
     */
    public void connectInPort3() {

        if(!this.mixer.getInput3().isConnected()) {
            currentPort = 3;
            getLayout(inPort3);
            super.connect();
        }
    }

    /**
     * Connecte le port d'entrée 4 pour tracer le cable
     */
    public void connectInPort4() {

        if(!this.mixer.getInput4().isConnected()) {
            currentPort = 4;
            getLayout(inPort4);
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
            //Deconnexion cables
            Port in1 = mixer.getInput1();
            Port in2 = mixer.getInput2();
            Port in3 = mixer.getInput3();
            Port in4 = mixer.getInput4();
            Port out = mixer.getOutput();

            super.disconnect(in1);
            super.disconnect(in2);
            super.disconnect(in3);
            super.disconnect(in4);
            super.disconnect(out);

            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(mixer);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }

}
