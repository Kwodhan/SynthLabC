package com.istic.modulesController;

import com.istic.port.Port;
import com.istic.rep.REP;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class REPLICATORModuleController extends ModuleController implements Initializable {

    protected REP rep;

    @FXML
    ImageView outPort1, outPort2, outPort3;
    @FXML
    ImageView inPort;

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

    public void init(Controller controller) {
        super.init(controller);
        this.rep = new REP();
        this.controller.getSynth().add(rep);

    }

    public void connectInPort() {
        if(!this.rep.getInput().isConnected()) {
            currentPort = 0;
            getLayout(inPort);
            super.connect();
        }
    }

    /**
     * Connecting the outPort1 to draw the cable
     */
    public void connectOutPort1() {
        if(!this.rep.getOutput1().isConnected()) {
            currentPort = 1;
            getLayout(outPort1);
            super.connect();
        }
    }

    /**
     * Connecting the outPort2 to draw the cable
     */
    public void connectOutPort2() {
        if(!this.rep.getOutput2().isConnected()) {
            currentPort = 2;
            getLayout(outPort2);
            super.connect();
        }
    }
    /**
     * Connecting the outPort3 to draw the cable
     */
    public void connectOutPort3() {
        if(!this.rep.getOutput3().isConnected()) {
            currentPort = 3;
            getLayout(outPort3);
            super.connect();
        }
    }


    @Override
    public Port getCurrentPort() {
        switch (currentPort) {

            case 0:
                return rep.getInput();
            case 1:
                return rep.getOutput1();
            case 2 :
                return rep.getOutput2();
            case 3 :
                return rep.getOutput3();

            default: return null;
        }
    }
}
