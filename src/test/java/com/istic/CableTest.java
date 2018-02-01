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
    private OutMod lineOut;
    private VCO vco;

    @Before
    public void initSynth() {
        this.synth = JSyn.createSynthesizer();
        this.lineOut = new OutMod(this.synth);
        this.vco = new VCO(this.synth);
    }

    @Test
    public void testCreateGoodCable() {
        Cable cable = new Cable(this.vco.getPortOutput(), this.lineOut.getPortInput());
        assertNotNull(cable);

        // connect

        boolean isConnected = cable.connect();
        assertTrue(isConnected);

        assertTrue(cable.getPortOne().isConnected());
        assertTrue(cable.getPortTwo().isConnected());

        // disconnect

        cable.disconnect();

        assertFalse(cable.getPortOne().isConnected());
        assertFalse(cable.getPortTwo().isConnected());
    }

    @Test
    public void testCreateGoodCableInverse() {
        Cable cable = new Cable(this.lineOut.getPortInput(), this.vco.getPortOutput());
        assertNotNull(cable);

        // connect

        boolean isConnected = cable.connect();
        assertTrue(isConnected);

        assertTrue(cable.getPortOne().isConnected());
        assertTrue(cable.getPortTwo().isConnected());

        // disconnect

        cable.disconnect();

        assertFalse(cable.getPortOne().isConnected());
        assertFalse(cable.getPortTwo().isConnected());
    }

    @Test
    public void testCreateBadCable() {
        Cable cable = new Cable(this.vco.getPortOutput(), this.vco.getPortOutput());
        assertNotNull(cable);

        // connect

        boolean isConnected = cable.connect();
        assertFalse(isConnected);

        assertFalse(cable.getPortOne().isConnected());
        assertFalse(cable.getPortTwo().isConnected());

        // disconnect

        cable.disconnect();

        assertFalse(cable.getPortOne().isConnected());
        assertFalse(cable.getPortTwo().isConnected());
    }
}
