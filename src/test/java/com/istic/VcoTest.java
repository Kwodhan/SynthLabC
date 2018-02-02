package com.istic;

import com.jsyn.JSyn;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class VcoTest {

    private VCO vco;

    @Before
    public void initSynth() {
        this.vco = new VCO(JSyn.createSynthesizer());
    }

    @Test
    public void testFrequencyLa() {

    }

    @Test
    public void testFrequencyNote() {
        // la
        this.vco.changeOctave(0);
        this.vco.changeFineHertz(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),440d);

        // sol
        this.vco.changeOctave(0);
        this.vco.changeFineHertz(-2);
        assertEquals((double)Math.round(this.vco.getFrequence()),392d);

        // la
        this.vco.changeOctave(-2);
        this.vco.changeFineHertz(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),110d);

        // ré
        this.vco.changeOctave(3);
        this.vco.changeFineHertz(-7);
        assertEquals((double)Math.round(this.vco.getFrequence()),2349d);

        this.vco.changeFineHertz(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),3520d);

        this.vco.changeOctave(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),440d);

    }


}
