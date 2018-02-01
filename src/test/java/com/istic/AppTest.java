package com.istic;

import com.istic.cable.Cable;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SquareOscillatorBL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AppTest {

    private Synthesizer synth;

    @Before
    public void initSynth() {
        this.synth = JSyn.createSynthesizer();
    }

    @Test
    public void testSimpleSynth() {
        assertNotNull(this.synth);
    }

    @Test
    public void testSynthWithSquareOscillatorBL() {
        SquareOscillatorBL osc = new SquareOscillatorBL();
        this.synth.add(osc);
        assertNotNull(osc);
    }

    @Test
    public void testCreateVCO() {
        VCO vco = new VCO(this.synth);
        assertNotNull(vco);
    }

    @Test
    public void testCreateOutMod() {
        OutMod outMod = new OutMod(this.synth);
        assertNotNull(outMod);
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
