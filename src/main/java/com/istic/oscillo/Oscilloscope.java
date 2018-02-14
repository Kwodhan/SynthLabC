package com.istic.oscillo;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;

/**
 *
 +----------------------------------------+
 |                                        |
 |                       +--------------+ |
 +---------------+       |Audio Scope   | |
 | Oscilloscope  0-------O              | |
 O PassThroug    |       +--------------+ |
 |               +--|                     |
 +---------------+  |---------------------0
 |                                        |
 |                                        |
 +----------------------------------------+

 */
public class Oscilloscope {

    private AudioScope scope;

    private PortInput portInput;
    private PortOutput portOutput;

    /**
     * Init Oscilloscope
     * @param synth Synthesizer to use
     */
    public Oscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);
        scope.setViewMode(AudioScope.ViewMode.WAVEFORM);
        scope.setTriggerMode(AudioScope.TriggerMode.AUTO);

        OscilloscopePassThrough oscilloscopePassThrough = new OscilloscopePassThrough();
        portInput = new PortInput(oscilloscopePassThrough.getInputPort());
        portOutput = new PortOutput(oscilloscopePassThrough.getOutputPort());

        addProbe(oscilloscopePassThrough.getOutputPortOscilloscope());
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

    public PortOutput getOutput() {
        return portOutput;
    }

    public PortInput getInput() {
        return portInput;
    }

}
