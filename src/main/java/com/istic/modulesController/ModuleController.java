package com.istic.modulesController;

import com.istic.port.Port;
import com.jsyn.Synthesizer;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;

public abstract class ModuleController {
    protected Controller controller;

    protected double x = 0, y = 0;

    protected int currentPort = -1;


    public void init(Controller controller) {

        this.controller = controller;


    }

    public void connect() {

        if (this.controller.isPlugged() && !this.controller.getModuleController().equals(this)) {

            this.controller.connect(this);
            this.controller.setModuleController(null);
            this.controller.setPlugged(false);

        } else {
            this.controller.setPlugged(true);
            this.controller.setModuleController(this);
        }
    }

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