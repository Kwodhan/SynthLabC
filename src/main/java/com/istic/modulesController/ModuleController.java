package com.istic.modulesController;

import com.istic.cable.CableController;
import com.istic.port.Port;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class ModuleController implements Serializable {

    protected Controller controller;

    /**
     * valeur de la position du port
     */
    protected double x = 0, y = 0;

    protected int currentPort = -1;

    JSONObject jsonCableObject;

    /**
     * Lie le controller du module au controller général
     *
     * @param controller controller général
     */
    public void init(Controller controller) {
        this.controller = controller;

    }

    /**
     * Get the position of the module on the UI
     *
     * @param stacks the positions on the UI
     */
    public int getPosition(StackPane[] stacks) {
        int position = -1, current = -1;

        for (StackPane stack : stacks) {
            current++;
            if (!stack.getChildren().isEmpty()) {
                if (stack.getChildren().get(0).getUserData().equals(this)) {
                    position = current;
                }
            }
        }
        return position;
    }

    /**
     * Crée un cable entre deux ports
     */
    public void connect() {
        Line line = this.controller.getMouseLine();
        // si il y a déja une connection, on connect
        if (this.controller.isPlugged() && !this.controller.getTemporaryCableModuleController().equals(this)) {
            this.controller.connect(this);
            this.controller.setTemporaryCableModuleController(null);
            this.controller.setPlugged(false);
            line.setVisible(false);

        } else if (this.controller.isPlugged()) {  // si c'est le même module
            this.controller.setTemporaryCableModuleController(null);
            this.controller.setPlugged(false);
            line.setVisible(false);

        } else { // si c'est le premier port, on le garde en variable temporaire
            line.setStartX(this.getX());
            line.setStartY(this.getY());
            line.setVisible(true);
            this.controller.setPlugged(true);
            this.controller.setTemporaryCableModuleController(this);
        }
    }

    /**
     * Supprime le cable branché sur un port de module
     *
     * @param port port duquel on veut supprimer le cable
     */
    public void disconnect(Port port) {
        List<CableController> cables = this.controller.getCables();
        Port portOne;
        Port portTwo;
        CableController removeCable = null;
        for (CableController cableController : cables) {
            portOne = cableController.getCable().getPortOne();
            portTwo = cableController.getCable().getPortTwo();
            if (portOne.equals(port)) {
                cableController.disconnect();
                removeCable = cableController;
            }
            if (portTwo.equals(port)) {
                cableController.disconnect();
            }
        }
        if (removeCable != null) {
            this.controller.getCables().remove(removeCable);
        }
    }


    /**
     * Get the layout of the port on the UI
     *
     * @param port the port clicked on the UI
     */
    public void getLayout(ImageView port) {
        Bounds boundsInScene = port.localToScene(port.getBoundsInLocal());
        x = (boundsInScene.getMaxX() + boundsInScene.getMinX()) / 2.0;
        y = (boundsInScene.getMaxY() + boundsInScene.getMinY()) / 2.0;
    }


    public abstract Port getCurrentPort();

    /**
     * met à jour le cables du port lors d'un dragAndDrop
     *
     * @param port le port à mettre un jour
     */
    public void updateCablesPositionFromPort(Port port) {
        List<CableController> cables = this.controller.getCables();
        Port portOne;
        Port portTwo;
        for (CableController cableController : cables) {
            portOne = cableController.getCable().getPortOne();
            portTwo = cableController.getCable().getPortTwo();
            if (portOne.equals(port)) {
                cableController.updatePosition(1);
            } else if (portTwo.equals(port)) {
                cableController.updatePosition(2);
            }
        }
    }

    // Setters & Getters

    /**
     * Redéfinis dans chaque module
     * Met à jour la position des cables liés au module
     */
    public void updateCablesPosition() {
        for (Map.Entry<ImageView, Port> entry : getAllPorts().entrySet()) {
            getLayout(entry.getKey());
            this.updateCablesPositionFromPort(entry.getValue());
        }
    }

    public abstract void removeModule();

    public abstract Map<ImageView, Port> getAllPorts();

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void serialize() {
        jsonCableObject = new JSONObject();
        jsonCableObject.put("type", this.getClass().getSimpleName());
        jsonCableObject.put("position", getPosition(this.controller.getStacks()));


    }

    public JSONObject getJsonCableObject() {
        return jsonCableObject;
    }

    public void setJsonCableObject(JSONObject jsonCableObject) {

        this.jsonCableObject = jsonCableObject;
    }

    public abstract void restore(JSONObject jsonObjectModule);

    public void setCurrentPort(int currentPort) {
        this.currentPort = currentPort;
    }
}