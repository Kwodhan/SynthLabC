package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitInputPort;

public class PortInput extends Port {

    public PortInput(Module module, UnitInputPort unitInputPort) {
        super(module,unitInputPort);
    }

    @Override
    public boolean isInput() {
        return true;
    }

    @Override
    public boolean isOutput() {
        return false;
    }




}
