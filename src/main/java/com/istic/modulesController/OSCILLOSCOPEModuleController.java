package com.istic.modulesController;

import com.istic.oscillo.Oscilloscope;
import com.istic.port.Port;
import com.jsyn.scope.swing.AudioScopeView;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class OSCILLOSCOPEModuleController extends ModuleController implements Initializable {
    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */

    private Oscilloscope oscilloscope;

    @FXML
    ImageView outPort;

    @FXML
    ImageView inPort;

    @FXML
    Pane paneOscilloscope;

    @Override
    public void initialize(URL location, ResourceBundle resources) { }

    public void init(Controller controller) {
        super.init(controller);
        this.oscilloscope = new Oscilloscope(controller.getSynth());

        final SwingNode swingNode = new SwingNode();
        createSwingContent(swingNode, oscilloscope.getView());
        paneOscilloscope.getChildren().add(swingNode);

        oscilloscope.start();
    }

    @Override
    public Port getCurrentPort() {
        if (currentPort == 0) {
            return this.oscilloscope.getOutputPort();
        } else if (currentPort == 1) {
            return this.oscilloscope.getInputPort();
        }
        return null;
    }

    /**
     * Connecting the inPort to draw the cable
     */
    public void connectInPort() {
        currentPort = 1;
        getLayout(inPort);
        super.connect();
    }

    /**
     * Connecting the outPort to draw the cable
     */
    public void connectOutPort() {
        currentPort = 0;
        getLayout(outPort);
        super.connect();
    }

    /**
     * Display JPanel component in JavaFX
     * @param swingNode node where add component
     * @param audioScopeView JPanel from JSyn display Oscilloscope
     */
    private void createSwingContent(final SwingNode swingNode, AudioScopeView audioScopeView) {
        Dimension d = new Dimension(260, 105);
        audioScopeView.setMaximumSize(d);
        audioScopeView.setMinimumSize(d);
        audioScopeView.setPreferredSize(d);
        SwingUtilities.invokeLater(() -> swingNode.setContent(audioScopeView));
    }
}
