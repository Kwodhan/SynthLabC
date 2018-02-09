package com.istic.cable;

import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CableController {
    /**
     *
     */
    AnchorPane pane;
    /**
     * Cable entre deux ports
     */
    Cable cable;
    /**
     * Ligne graphique
     */
    Line line;

    ModuleController mc1;
    ModuleController mc2;

    public CableController(AnchorPane pane, Cable cable) {
        this.cable = cable;
        this.pane = pane;
    }


    public void disconnect() {
            cable.disconnect();
            pane.getChildren().remove(line);
    }

    public void updatePosition(int i) {
        if(i==1){
            line.setStartX(mc1.getX());
            line.setStartY(mc1.getY());
        }else {
            line.setEndX(mc2.getX());
            line.setEndY(mc2.getY());
        }


    }

    public void drawCable(ModuleController moduleController1, ModuleController moduleController2,Integer id) {

        mc1 = moduleController1;
        mc2 = moduleController2;

        line = new Line(moduleController1.getX(),
                moduleController1.getY(), moduleController2.getX(),
                moduleController2.getY());
        line.setStrokeWidth(5);
        line.setStroke(Color.BLUEVIOLET);
        line.setId("cable-"+id);
        pane.getChildren().add(line);
        line.setOnMouseClicked(event -> {
            this.disconnect();
        });
    }

    public Cable getCable() {
        return cable;
    }

}
