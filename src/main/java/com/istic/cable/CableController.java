package com.istic.cable;

import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import org.json.simple.JSONObject;

import java.util.ArrayList;

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
    JSONObject jsonCableObject =null;
    Controller controller;
    ModuleController mc1;
    ModuleController mc2;

    public CableController(Controller controller, AnchorPane pane, Cable cable, Color color) {
        this.cable = cable;
        this.pane = pane;
        this.color = color;
        this.controller = controller;


    }

    public boolean serialize() {
        if(jsonCableObject !=null) {
            return false;
        }else{

            jsonCableObject = new JSONObject();
            jsonCableObject.put("type", this.getClass().getSimpleName());
            jsonCableObject.put("color", color.toString());

            String portOne = "";
            String portTwo = "";
            //recuperer un port
            //mc1.getAllPorts()
            jsonCableObject.put("positionM1", mc1.getPosition(this.controller.getStacks()));
            jsonCableObject.put("positionM2", mc2.getPosition(this.controller.getStacks()));
            jsonCableObject.put("portM1", cable.portOne.getClass().getSimpleName());
            jsonCableObject.put("portM2", cable.portTwo.getClass().getSimpleName());
            jsonCableObject.put("StartX", line.getStartX());
            jsonCableObject.put("EndX", line.getEndX());
            jsonCableObject.put("StartY", line.getStartY());
            jsonCableObject.put("EndY", line.getEndY());

            return true;

        }
    }

    public void restore(JSONObject jsonObjectCable) {
        setJsonCableObject(jsonObjectCable);
        this.color=Color.BLUE;
        //this.color = (Color) jsonObjectCable.get("color");

    }

    public void disconnect() {
        this.controller.getCables().remove(this);
        jsonCableObject =null;
        pane.getChildren().remove(line);
        cable.disconnect();
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

    public void restoreLine(ArrayList<Double> lineData){

        double x1, x2, y1, y2;
        x1 = lineData.get(0);
        x2 = lineData.get(1);
        y1 = lineData.get(2);
        y2 = lineData.get(3);
        String lineId =line.getId();
        pane.getChildren().remove(line);

        line.setOnMouseClicked(null);

        line = new CubicCurve(x1, y1, x1, y1 + getCurve(x1, y1, x2, y2)
                , x2, y2 + getCurve(x1, y1, x2, y2), x2, y2);

        line.setFill(null);
        line.setStrokeWidth(5);

        line.setStroke(Color.GOLD);

        line.setId(lineId);

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

    public JSONObject getJsonCableObject() {
        return jsonCableObject;
    }

    public void setJsonCableObject(JSONObject jsonCableObject) {
        this.jsonCableObject = jsonCableObject;
    }

    public ModuleController getMc1() {
        return mc1;
    }

    public ModuleController getMc2() {
        return mc2;
    }
}
