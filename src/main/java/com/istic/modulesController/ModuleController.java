package com.istic.modulesController;

import com.istic.cable.CableController;
import com.istic.port.Port;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ModuleController {
    protected Controller controller;

    protected double x = 0, y = 0;

    protected int currentPort = -1;




    public void init(Controller controller) {

        this.controller = controller;


    }

    public void connect() {

        if (this.controller.isPlugged() && !this.controller.getTemporaryCableModuleController().equals(this)) {
            this.controller.connect(this);
            this.controller.setTemporaryCableModuleController(null);
            this.controller.setPlugged(false);

        } else {

            this.controller.setPlugged(true);
            this.controller.setTemporaryCableModuleController(this);
        }
    }

    /**
     * Supprime le cable branché sur le port du module
     * @param port port auquel on veut supprimer le cable
     */
    public void disconnect(Port port) {
        List<CableController> cables = this.controller.getCables();
        Port portOne;
        Port portTwo;
        for (CableController cableController : cables) {
             portOne = cableController.getCable().getPortOne();
             portTwo = cableController.getCable().getPortTwo();
             if (portOne.equals(port)) {
                 cableController.disconnect();
             }
            if (portTwo.equals(port)) {
                cableController.disconnect();
            }
        }
    }

//    public void deleteModuleControllerFromController(){
//        this.controller.disconnect(this);
//    }

    /**
     * Get the layout of the port on the UI
     * @param port the port clicked on the UI
     */
    public void getLayout(ImageView port){
        Bounds boundsInScene = port.localToScene(port.getBoundsInLocal());
        x=(boundsInScene.getMaxX()+boundsInScene.getMinX())/2.0;
        y=(boundsInScene.getMaxY()+boundsInScene.getMinY())/2.0;
    }


    public abstract Port getCurrentPort();

    public void updateCablesPositionFromPort(Port port) {
        List<CableController> cables = this.controller.getCables();
        Port portOne;
        Port portTwo;
        for (CableController cableController : cables) {
            portOne = cableController.getCable().getPortOne();
            portTwo = cableController.getCable().getPortTwo();
            if (portOne.equals(port)) {
                cableController.updatePosition();
            }
            if (portTwo.equals(port)) {
                cableController.updatePosition();
            }
        }
    }

    /**
     * Redéfinis dans chaque module
     * Met à jour la position des cables liés au module
     */
    public void updateCablesPosition() {
        for(Map.Entry<ImageView, Port> entry : getAllPorts().entrySet()) {
            getLayout(entry.getKey());
            this.updateCablesPositionFromPort(entry.getValue());
        }
    }

    public abstract Map<ImageView, Port> getAllPorts();

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}