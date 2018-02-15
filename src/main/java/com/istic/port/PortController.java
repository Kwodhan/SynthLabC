package com.istic.port;


import javafx.scene.image.ImageView;

public class PortController {

    Port port;
    ImageView view;

    public PortController(Port port, ImageView view) {
        this.port = port;
        this.view = view;
    }

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    public ImageView getView() {
        return view;
    }

    public void setView(ImageView view) {
        this.view = view;
    }
}
