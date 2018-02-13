package com.istic.modulesController;

import com.istic.out.OutMod;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import java.io.File;
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
    @FXML
    ToggleButton mute;
    private OutMod lineOut;

    private File dest;
    private File source = new File("./src/main/resources/sound/savedSound.wav");

    @FXML
    protected Slider attenuationSlider;

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
    public void init(Controller controller) {
        super.init(controller);
        this.lineOut = new OutMod();
        this.controller.getSynth().add(this.lineOut);
        lineOut.start();
//        File file = new File("/home/jnsll/Documents/file.wav");
//        try {
//            this.recorder = new EnregistreurWave(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * Connecte le port d'entrée pour tracer le cable
     */
    public void connect() {
        if (!this.lineOut.getPortInput().isConnected()) {
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
        serialize();

    }

    /**
     * Gère la fonctionnalité "Muet"
     * Coupe le son
     */
    public void toggleRecord() throws IOException {
//        double[] inputs0 = lineOut.getInput().getValues(0);
//        double[] data = this.lineOut.getSynthesisEngine().getOutputBuffer(1);
//        try {
//            this.recorder.toggleRecord();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        this.lineOut.setRecord(!this.lineOut.isRecord());

        if (!this.lineOut.isRecord()) {
            this.lineOut.getWriter().close();
            try {
            FileUtils.copyFile(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        } else {
//            if (this.lineOut.getWriter() == null) {
                //choose emplacement file wave
            this.dest = null;
            this.dest = this.controller.saveToMP3();
            if (this.dest != null) {
                this.lineOut.setLocationSelected(true);
            } else {
                this.lineOut.setRecord(false);
            }


//            }
        }
    }


    /**
     * Récupère l'information concernant le port sur lequel l'utilisateur a cliqué
     *
     * @return le port sur lequel l'utilisateur a cliqué côté IHM
     */
    public Port getCurrentPort() {
        if (!this.lineOut.getPortInput().isConnected()) {
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

    @Override
    public void serialize() {
        super.serialize();
        jsonModuleObject.put("mute", lineOut.getMute());
        jsonModuleObject.put("attenuation", lineOut.getAttenuation());


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
        if (this.controller.getTemporaryCableModuleController() == null) {
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

    public OutMod getLineOut() {
        return lineOut;
    }

    public ToggleButton getMute() {
        return mute;
    }

    public Slider getAttenuationSlider() {
        return attenuationSlider;
    }

    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
        double attenuation = (double) jsonObjectModule.get("attenuation");
        int mute = ((Long) jsonObjectModule.get("mute")).intValue();
        //model
        this.getLineOut().setAttenuation(attenuation);
        this.getLineOut().setMute(mute);
        //graphique
        this.getAttenuationSlider().setValue(attenuation);
        if (mute == 0)
            this.getMute().setSelected(true);

    }
}
