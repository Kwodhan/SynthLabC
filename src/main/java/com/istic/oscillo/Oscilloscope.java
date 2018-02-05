package com.istic.oscillo;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.scope.AudioScope;
import com.jsyn.scope.swing.AudioScopeView;

import java.awt.*;

public class Oscilloscope {

    private AudioScope scope;


    /**
     * Init Oscilloscope
     * @param synth Synthesizer to use
     */
    public Oscilloscope(Synthesizer synth) {
        scope = new AudioScope(synth);
        scope.setTriggerMode(AudioScope.TriggerMode.NORMAL);
    }

    /**
     * Add probe to display
     * @param probe to analyse in oscilloscope
     */
    public void addProbe(UnitOutputPort probe) {
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
}
