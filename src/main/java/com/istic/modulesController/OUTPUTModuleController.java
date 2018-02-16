package com.istic.modulesController;

import com.istic.fileformat.AudioFile;
import com.istic.out.OutMod;
import com.istic.port.Port;
import com.istic.port.PortController;
import com.istic.util.Style;
import com.jsyn.util.WaveFileWriter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

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
    @FXML
    ToggleButton recordButton;

    private OutMod lineOut;

    private File dest;

    private String extension;


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
    	
    	closeButton.setTranslateX(5);
    	closeButton.setTranslateY(6);

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
        this.portControllers.add(new PortController(this.lineOut.getPortInput(),this.inPort));
        lineOut.start();
        Style.updateStyleTheme(pane, this.controller.choosedTheme);
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
     * @throws IOException in/out erreur
     */
    public void toggleRecord() throws IOException {
        // true if first click , false else
        this.lineOut.setRecord(!this.lineOut.isRecord());

        if (this.lineOut.isRecord()) { // si premier click
            this.dest = null;
            Pair infos = this.controller.saveSound();

            this.dest = (File) infos.getKey();
            this.extension = (String) infos.getValue();

           // cas où l'utilisateur renseigne bien le nom du fichier et path
            if (this.dest != null) {
                if (this.extension.equals("*.mp3")) {
                    this.lineOut.setWriter(new WaveFileWriter(new File("/tmp/mySound.wav")));
                } else {
                    this.lineOut.setWriter(new WaveFileWriter(new File(this.dest.getPath())));
                }
            } else { // cas où l'utilisateur appuie sur Annuler
                this.lineOut.setRecord(false);
                this.recordButton.setSelected(false);

            }
        } else { // deuxieme click
            this.lineOut.getWriter().close();
            this.lineOut.setWriter(null);
            // Conversion du fichier en mp3 si extension correspondante choisie,
            // check extension too for better coverage.
            if (this.extension.equals("*.mp3") || this.dest.getName().endsWith(".mp3")) {
                AudioFile file = new AudioFile("/tmp/mySound.wav");
                file.convertToMP3(this.dest.getPath());
            }
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
    public void serialize() {
        super.serialize();
        jsonModuleObject.put("mute", lineOut.getMute());
        jsonModuleObject.put("attenuation", lineOut.getAttenuation());


    }

    /**
     * Supprime le module du Board ainsi que les cables
     * et les dépendances côté modèle
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

    @Override
    public void restore(JSONObject jsonObjectModule) {
        setJsonModuleObject(jsonObjectModule);
        double attenuation = (double) jsonObjectModule.get("attenuation");
        int mute = ((Long) jsonObjectModule.get("mute")).intValue();


        //graphique
        this.getAttenuationSlider().setValue(attenuation);
        if (mute == 0)
            this.getMute().setSelected(true);

    }

    //Setters & Getters

    public OutMod getLineOut() {
        return lineOut;
    }

    public ToggleButton getMute() {
        return mute;
    }

    public Slider getAttenuationSlider() {
        return attenuationSlider;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
	@Override
	public void updateTheme(int i) {
		// TODO Auto-generated method stub
		
		Style.updateStyleTheme(pane, i);
		
	}
}
