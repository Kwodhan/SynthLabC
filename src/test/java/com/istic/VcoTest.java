package com.istic;

import com.istic.vco.VCO;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class VcoTest {

    private VCO vco;

    @Before
    public void initSynth() {
        Synthesizer syn = JSyn.createSynthesizer();
        syn.add(this.vco = new VCO());

    }

    @Test
    public void testFrequencyLa() {

    }

    @Test
    public void testFrequencyNote() {
        // la
        this.vco.changeOctave(0);
        this.vco.changeFin(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),440d);

        // sol
        this.vco.changeOctave(0);
        this.vco.changeFin(-2);
        assertEquals((double)Math.round(this.vco.getFrequence()),392d);

        // la
        this.vco.changeOctave(-2);
        this.vco.changeFin(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),110d);

        // ré
        this.vco.changeOctave(3);
        this.vco.changeFin(-7);
        assertEquals((double)Math.round(this.vco.getFrequence()),2349d);

        this.vco.changeFin(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),3520d);

        this.vco.changeOctave(0);
        assertEquals((double)Math.round(this.vco.getFrequence()),440d);

    }

//    @Test
//    public void testChangeOfShapeWave(){
//        this.vco.changeShapeWave(VCO.SAWWAVE);
//        // check change of wave shape
//    }


}
