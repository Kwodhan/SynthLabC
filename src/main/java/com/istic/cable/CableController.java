package com.istic.cable;

import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CableController {
    AnchorPane pane;
    Cable cable;
    Line line;

    public CableController(AnchorPane pane, Cable cable) {
        this.cable = cable;
        this.pane = pane;
    }


    public void disconnect() {
            cable.disconnect();
            pane.getChildren().remove(line);

    }

    public void drawCable(ModuleController moduleController1, ModuleController moduleController2,Integer id) {

        line = new Line(moduleController1.getX(),
                moduleController1.getY(), moduleController2.getX(),
                moduleController2.getY());
        line.setStrokeWidth(5);
        line.setStroke(Color.BLUEVIOLET);
        line.setId("cable"+id);
        pane.getChildren().add(line);
        line.setOnMouseClicked(event -> {
            this.disconnect();
        });
    }
}
