package com.istic;

import com.istic.cable.Cable;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SquareOscillatorBL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CableTest {

    private Synthesizer synth;

    @Before
    public void initSynth() {
        this.synth = JSyn.createSynthesizer();
    }

    @Test
    public void testCreateCable() {
        OutMod lineOut = new OutMod(this.synth);
        VCO vco = new VCO(this.synth);

        Cable cable = new Cable(vco.getPortOutput(),lineOut.getPortInput());
        assertNotNull(cable);

        cable.connect();

        assertTrue(cable.getPortOne().isConnected());
        assertTrue(cable.getPortTwo().isConnected());

        cable.disconnect();

        assertFalse(cable.getPortOne().isConnected());
        assertFalse(cable.getPortTwo().isConnected());
    }
}
