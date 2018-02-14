package com.istic.cable;

import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import com.istic.port.Port;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CableController {
    Color color;

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
    CubicCurve line;
    JSONObject jsonModuleObject;

    Controller controller;
    ModuleController mc1;
    ModuleController mc2;

    public CableController(Controller controller, AnchorPane pane, Cable cable, Color color) {
        this.cable = cable;
        this.pane = pane;
        this.color = color;
        this.controller = controller;


    }

    public void serialize() {

        jsonModuleObject = new JSONObject();
        jsonModuleObject.put("type", this.getClass().getSimpleName());
        jsonModuleObject.put("color", color.toString());

        String portOne = "";
        String portTwo = "";
        //recuperer un port
        //mc1.getAllPorts()
        jsonModuleObject.put("positionM1", mc1.getPosition(this.controller.getStacks()));
        jsonModuleObject.put("positionM2", mc2.getPosition(this.controller.getStacks()));
        jsonModuleObject.put("portM1", cable.portOne.getClass().getSimpleName());
        jsonModuleObject.put("portM2", cable.portTwo.getClass().getSimpleName());


    }

    public void restore(JSONObject jsonObjectCable) {
        setJsonModuleObject(jsonObjectCable);
        this.color=Color.BLUE;
        //this.color = (Color) jsonObjectCable.get("color");

    }

    public void disconnect() {
        cable.disconnect();
        pane.getChildren().remove(line);
    }

    public void updatePosition(int i) {
        if (i == 1) {
            line.setStartX(mc1.getX());
            line.setStartY(mc1.getY());
            line.setControlX1(mc1.getX());
            line.setControlY1(mc1.getY() + getCurve(mc1.getX(), mc1.getY(), mc2.getX(), mc2.getY()));
            line.setControlX2(mc2.getX());
            line.setControlY2(mc2.getY() + getCurve(mc1.getX(), mc1.getY(), mc2.getX(), mc2.getY()));

        } else {
            line.setEndX(mc2.getX());
            line.setEndY(mc2.getY());
            line.setControlX1(mc1.getX());
            line.setControlY1(mc1.getY() + getCurve(mc1.getX(), mc1.getY(), mc2.getX(), mc2.getY()));
            line.setControlX2(mc2.getX());
            line.setControlY2(mc2.getY() + getCurve(mc1.getX(), mc1.getY(), mc2.getX(), mc2.getY()));

        }


    }

    public void drawCable(ModuleController moduleController1, ModuleController moduleController2, Integer id) {

        mc1 = moduleController1;
        mc2 = moduleController2;
        double x1, x2, y1, y2;
        x1 = mc1.getX();
        x2 = mc2.getX();
        y1 = mc1.getY();
        y2 = mc2.getY();
        line = new CubicCurve(x1, y1, x1, y1 + getCurve(x1, y1, x2, y2)
                , x2, y2 + getCurve(x1, y1, x2, y2), x2, y2);

        line.setFill(null);
        line.setStrokeWidth(5);

        line.setStroke(color);
        line.setId("cable-" + id);


        pane.getChildren().add(line);

        line.setOnMouseClicked(event -> {
            this.disconnect();
        });
    }

    public Cable getCable() {
        return cable;
    }

    public double getCurve(double x1, double y1, double x2, double y2) {
        double x = Math.abs(x1 - x2);
        double y = Math.abs(y1 - y2);
        return x / 6 + y / 2;

    }

    public JSONObject getJsonModuleObject() {
        return jsonModuleObject;
    }

    public void setJsonModuleObject(JSONObject jsonModuleObject) {
        this.jsonModuleObject = jsonModuleObject;
    }
}
