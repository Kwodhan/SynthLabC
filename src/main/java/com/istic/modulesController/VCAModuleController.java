package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.port.PortController;
import com.istic.vca.VCA;
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

public class VCAModuleController extends ModuleController implements Initializable {

    @FXML
    AnchorPane pane;
    @FXML
    Slider amplitudeSlider;
    @FXML
    ImageView outPort,inPort,amPort;
    protected VCA vca;

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
        amplitudeSlider.valueProperty().addListener((ov, old_val, new_val) -> {
            amplitudeSlider.setValue(Math.round(amplitudeSlider.getValue()));
            vca.changeA0((int) amplitudeSlider.getValue());
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
        this.vca = new VCA();
        this.controller.getSynth().add(vca);

        vca.changeA0(amplitudeSlider.getValue());

        this.portControllers.add(new PortController(this.vca.getInput(),this.inPort));
        this.portControllers.add(new PortController(this.vca.getAm(),this.amPort));
        this.portControllers.add(new PortController(this.vca.getOutput(),this.outPort));



    }

    /**
     * Connecting the AmPort to draw the cable
     */
    public void connectAmPort() {
        if(!this.vca.getAm().isConnected()) {
            currentPort = 1;
            getLayout(amPort);
            super.connect();
        }
    }
    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        if(!this.vca.getOutput().isConnected()) {
            currentPort = 0;
            getLayout(outPort);
            super.connect();
        }
    }
    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        if(!this.vca.getInput().isConnected()) {
            currentPort = 2;
            getLayout(inPort);
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
                return vca.getOutput();
            case 1:
                return vca.getAm();
            case 2 :
                return vca.getInput();

            default: return null;
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
            Port am = vca.getAm();
            Port in = vca.getInput();
            Port out = vca.getOutput();
            super.disconnect(am);
            super.disconnect(in);
            super.disconnect(out);
            // Deconnexion du module Output du synthetizer
            this.controller.getSynth().remove(vca);
            // Get parent node of pane corresponding to OutMod
            // Recupere le noeud parent fxml du outmod
            StackPane stackPane = (StackPane) pane.getParent();
            // supprime le mod niveau ihm
            stackPane.getChildren().remove(pane);
            this.controller.disconnect(this);
        }
    }



    @Override
    public void serialize() {
    super.serialize();

        jsonModuleObject.put("amplitudeSlider", amplitudeSlider.getValue());

    }

    @Override
    public void restore(JSONObject jsonObjectModule) {

        double amplitude = (double) jsonObjectModule.get("amplitudeSlider");

        //graphique
        amplitudeSlider.setValue(Math.round(amplitude));

    }
}
