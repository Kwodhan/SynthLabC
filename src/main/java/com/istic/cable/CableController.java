package com.istic.cable;

import com.istic.modulesController.Controller;
import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.CubicCurve;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class CableController {
    private Color color;

    /**
     *
     */
    private AnchorPane pane;
    /**
     * Cable entre deux ports
     */
    private Cable cable;
    /**
     * Ligne graphique
     */
    private CubicCurve line;
    private JSONObject jsonCableObject;
    private Controller controller;
    private ModuleController mc1;
    private ModuleController mc2;

    public CableController(Controller controller, AnchorPane pane, Cable cable, Color color) {
        this.cable = cable;
        this.pane = pane;
        this.color = color;
        this.controller = controller;


        jsonCableObject = null;
    }

    /**
     * Serialize the cable controller for a json specification
     *
     * @return false if already serialized
     */
    @SuppressWarnings({"unchecked", "JavaDoc"})
    public boolean serialize() {
        if (jsonCableObject != null) {
            return false;
        } else {

            jsonCableObject = new JSONObject();
            jsonCableObject.put("type", this.getClass().getSimpleName());
            jsonCableObject.put("color", color.toString());

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

    /**
     * restore the cable from a Json Object
     *
     * @param jsonObjectCable specification of the cable
     */
    public void restore(JSONObject jsonObjectCable) {
        setJsonCableObject(jsonObjectCable);
        this.color = Color.BLUE;
        //this.color = (Color) jsonObjectCable.get("color");

    }

    /**
     * Disconnect a cable from the UI
     * Listener to the click on the cable
     */
    public void disconnect() {
        this.controller.getCables().remove(this);
        jsonCableObject = null;
        pane.getChildren().remove(line);
        cable.disconnect();
    }

    /**
     * update the cable when changing the module on the UI
     *
     * @param i the new position
     */
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

    /**
     * Draw tha cable on the UI connecting two modules
     *
     * @param moduleController1 the first module
     * @param moduleController2 the second module
     * @param id                the id of the cable to be drawn
     */
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

        line.setOnMouseClicked(event -> this.disconnect());
    }

    /**
     * Restore the line after opening a configuration on the ui
     *
     * @param lineData the data of the cable to draw
     */
    public void restoreLine(ArrayList<Double> lineData) {

        double x1, x2, y1, y2;
        x1 = lineData.get(0);
        x2 = lineData.get(1);
        y1 = lineData.get(2);
        y2 = lineData.get(3);
        String lineId = line.getId();
        pane.getChildren().remove(line);

        line.setOnMouseClicked(null);

        line = new CubicCurve(x1, y1, x1, y1 + getCurve(x1, y1, x2, y2)
                , x2, y2 + getCurve(x1, y1, x2, y2), x2, y2);

        line.setFill(null);
        line.setStrokeWidth(5);

        line.setStroke(Color.GOLD);

        line.setId(lineId);

        pane.getChildren().add(line);

        line.setOnMouseClicked(event -> this.disconnect());

    }

    /**
     * Return the cable
     *
     * @return cable connecting the two port
     */
    public Cable getCable() {
        return cable;
    }

    /**
     * Get the curve to apply on the cable on the UI
     *
     * @param x1 startX
     * @param y1 startY
     * @param x2 endX
     * @param y2 endY
     * @return the value of the curve
     */
    private double getCurve(double x1, double y1, double x2, double y2) {
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
