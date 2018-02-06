package com.istic.oscillo;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;
import com.jsyn.unitgen.PassThrough;

public class Oscilloscope extends PassThrough {

    private AudioScope scope;
    private PortOutput portOutput;
    private PortInput portInput;

    /**
     * Init Oscilloscope
     * @param synth Synthesizer to use
     */
    public Oscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);
        scope.setTriggerMode(AudioScope.TriggerMode.NORMAL);

        portOutput = new PortOutput(this.output);
        portInput = new PortInput(this.input);

        addProbe(this.output);
    }

    /**
     * Add probe to display
     * @param probe to analyse in oscilloscope
     */
    private void addProbe(UnitOutputPort probe) {
        scope.addProbe(probe);
    }

    public AudioScopeView getView() {
        return scope.getView();
    }

    public void start() {
        scope.start();
    }

    public void stop() {
        scope.stop();
    }

    public PortOutput getOutputPort() {
        return portOutput;
    }

    public PortInput getInputPort() {
        return portInput;
    }

}
