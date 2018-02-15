package com.istic.port;


import javafx.scene.image.ImageView;

public class PortController {

    final private Port port;
    final private ImageView view;

    public PortController(Port port, ImageView view) {
        this.port = port;
        this.view = view;

    }



    public Port getPort() {
        return port;
    }

    public ImageView getView() {
        return view;
    }
}
