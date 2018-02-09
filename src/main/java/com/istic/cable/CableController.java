package com.istic.cable;

import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CableController {
    List<Color> color;
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

    public CableController(AnchorPane pane, Cable cable) {
        this.cable = cable;
        this.pane = pane;
        this.color = new ArrayList<>();
        this.color.add(Color.GOLD);
        this.color.add(Color.BLUEVIOLET);
        this.color.add(Color.RED);
        this.color.add(Color.OLIVE);
        this.color.add(Color.SALMON);
        this.color.add(Color.SILVER);
        this.color.add(Color.MEDIUMAQUAMARINE);

    }


    public void disconnect() {
            cable.disconnect();
            pane.getChildren().remove(line);
    }

    public void drawCable(ModuleController moduleController1, ModuleController moduleController2,Integer id) {

        line = new Line(moduleController1.getX(),
                moduleController1.getY(), moduleController2.getX(),
                moduleController2.getY());
        line.setStrokeWidth(3);
        Random r = new Random();
        line.setStroke(this.color.get(r.nextInt(this.color.size())));
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
