package com.istic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SquareOscillatorBL;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class GlobalTest {

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
        VCO vco = new VCO();
        this.synth.add(vco);

        assertNotNull(vco);
    }

    @Test
    public void testCreateOutMod() {
        OutMod outMod = new OutMod();
        this.synth.add(outMod);
        assertNotNull(outMod);
    }
}
