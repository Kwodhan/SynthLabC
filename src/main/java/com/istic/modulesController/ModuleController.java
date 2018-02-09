package com.istic.modulesController;

import com.istic.cable.CableController;
import com.istic.port.Port;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

import java.util.List;

public abstract class ModuleController {

    protected Controller controller;

    protected double x = 0, y = 0;

    protected int currentPort = -1;


    /**
     * Lie le controller du module au controller général
     *
     * @param controller
     */
    public void init(Controller controller) {

        this.controller = controller;


    }

    /**
     * Crée un cable entre deux ports
     *
     */
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
     * Supprime le cable branché sur un port de module
     * @param port port duquel on veut supprimer le cable
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

    // Setters & Getters

    public abstract Port getCurrentPort();

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