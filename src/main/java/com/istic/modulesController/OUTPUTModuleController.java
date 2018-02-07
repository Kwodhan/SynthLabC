package com.istic.modulesController;

import com.istic.out.OutMod;
import com.istic.port.Port;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OUTPUTModuleController extends ModuleController implements Initializable {

    @FXML
    ImageView inPort;
    @FXML
    AnchorPane pane;
    @FXML
    public Button closeButton;

    private OutMod lineOut;

    @FXML
    private Slider attenuationSlider;

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



    public void init(Controller controller){

        super.init(controller);
        this.lineOut = new OutMod();
        this.controller.getSynth().add(this.lineOut);
        lineOut.start();

    }

    public void connect() {
        super.getLayout(inPort);
        super.connect();
    }


    public void toggleMute() {
        this.lineOut.toggleMute();
    }


    public Port getCurrentPort(){

        if(!this.lineOut.getPortInput().isConnected()) {
            return lineOut.getPortInput();
        }
        return null;

    }

    public void removeOutput() throws IOException {
        // Suppression OUTmod model
        //suppression OUTmod controller
        // suppression Outmod IHM
            // suppression cable

        //1- Deconnexion cable
        Port port = lineOut.getPortInput();
        super.disconnect(port);

//        Node root = closeButton.getParent();
//        System.out.println(root.toString());

        //inPort
    }

}
