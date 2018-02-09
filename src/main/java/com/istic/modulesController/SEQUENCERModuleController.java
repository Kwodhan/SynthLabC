package com.istic.modulesController;

import com.istic.port.Port;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class SEQUENCERModuleController extends ModuleController implements Initializable {
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

    @Override
    public Port getCurrentPort() {
        return null;
    }

    @Override
    public Map<ImageView, Port> getAllPorts() {
        return null;
    }
}
