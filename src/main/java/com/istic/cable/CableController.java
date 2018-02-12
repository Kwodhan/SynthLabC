package com.istic.cable;

import com.istic.modulesController.ModuleController;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.CubicCurve;
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
    CubicCurve line;

    ModuleController mc1;
    ModuleController mc2;

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

    public void updatePosition(int i) {
        if(i==1){
            line.setStartX(mc1.getX());
            line.setStartY(mc1.getY());
            line.setControlX1(mc1.getX());
            line.setControlY1(mc1.getY()+getCurve(mc1.getX(),mc1.getY(),mc2.getX(),mc2.getY()));

        }else {
            line.setEndX(mc2.getX());
            line.setEndY(mc2.getY());
            line.setControlX2(mc2.getX());
            line.setControlY2(mc2.getY()+getCurve(mc1.getX(),mc1.getY(),mc2.getX(),mc2.getY()));

        }


    }

    public void drawCable(ModuleController moduleController1, ModuleController moduleController2,Integer id) {

        mc1 = moduleController1;
        mc2 = moduleController2;
        double x1,x2,y1,y2;
        x1=mc1.getX();
        x2=mc2.getX();
        y1=mc1.getY();
        y2=mc2.getY();
        line = new CubicCurve( x1,y1, x1, y1+getCurve(x1,y1,x2,y2)
                , x2, y2+getCurve(x1,y1,x2,y2), x2, y2);

        line.setFill( null);
        line.setStrokeWidth(5);

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

    public double getCurve(double x1,double y1,double x2,double y2){
        double x=Math.abs(x1-x2);
        double y=Math.abs(y1-y2);
        return x/6+y/2;
        
    }

}
