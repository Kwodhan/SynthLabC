package com.istic.modulesController;

import com.istic.port.Port;

public interface ModuleController {

    Port getCurrentPort();

    double getX();

    void setX(double x);

    double getY();

    void setY(double y);
}