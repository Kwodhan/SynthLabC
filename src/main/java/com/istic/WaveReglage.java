package com.istic;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

public class WaveReglage extends UnitGenerator{
    private UnitInputPort inputPort;
    private UnitOutputPort outputPort;

    public WaveReglage() {
        addPort( inputPort = new UnitInputPort("input"));
        addPort(outputPort = new UnitOutputPort("output"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] inputs = inputPort.getValues();
        double[] outputs = outputPort.getValues();

        for (int i = start; i < limit; i++) {
            outputs[i] = inputs[i];
        }
    }

    public UnitInputPort getInputPort() {
        return inputPort;
    }

    public UnitOutputPort getOutputPort() {
        return outputPort;
    }
}
