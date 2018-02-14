package com.istic.oscillo;

import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.UnitGenerator;

/**
 * Permet la sortie du signal d'entrée sans modification
 */
public class OscilloscopePassThrough extends UnitGenerator {

    /**
     * Signal d'entrée
     */
    private UnitInputPort inputPort;
    /**
     * Signal de sortie
     */
    private UnitOutputPort outputPort;

    /**
     * Signal de sortie reservé uniquement pour l'Oscilloscope
     */
    private UnitOutputPort outputPortOscilloscope;

    public OscilloscopePassThrough() {
        addPort(this.inputPort = new UnitInputPort("inputPort"));
        addPort(this.outputPort = new UnitOutputPort("outputPort"));
        addPort(this.outputPortOscilloscope = new UnitOutputPort("outputPortOscilloscope"));
    }

    @Override
    public void generate(int start, int limit) {
        double[] input = inputPort.getValues();
        double[] output1 = outputPort.getValues();
        double[] output2 = outputPortOscilloscope.getValues();

        for (int i = start; i < limit; i++) {
            output1[i] = input[i];
            output2[i] = input[i];
        }
    }

    public UnitInputPort getInputPort() {
        return inputPort;
    }

    public UnitOutputPort getOutputPort() {
        return outputPort;
    }

    public UnitOutputPort getOutputPortOscilloscope() {
        return outputPortOscilloscope;
    }

}
