package com.istic.port;

import com.istic.Module;
import com.jsyn.ports.UnitOutputPort;

public class PortOutput extends Port{

    public PortOutput(Module module, UnitOutputPort unitOutputPort) {
        super(module,unitOutputPort);
    }

    @Override
    public boolean isInput() {
        return false;
    }

    @Override
    public boolean isOutput() {
        return true;
    }


}
