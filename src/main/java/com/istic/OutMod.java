package com.istic;

import com.istic.port.PortInput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;

public class OutMod implements Module {

    private Synthesizer synth;
    private LineOut lineOut;

    private PortInput portInput;

    public OutMod(Synthesizer synth) {
        this.synth = synth;
        this.synth.add(lineOut = new LineOut());
        this.portInput  = new PortInput(this,this.lineOut.input);
    }




    @Override
    public void start() {
        this.lineOut.start();
    }

    @Override
    public void stop() throws InterruptedException {
        this.lineOut.stop();
    }

    public PortInput getPortInput() {
        return portInput;
    }
}
