package com.istic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class VCO implements Module{


    private final double f0 = 440.0;
    private Synthesizer synth;
    private UnitOscillator osc;
    private LinearRamp lag;
    private int octave = 0;

    public VCO(OutMod lineOut){

        synth = JSyn.createSynthesizer();

        // Add a tone generator.
        synth.add( osc = new SquareOscillatorBL() );
        // Add a lag to smooth out amplitude changes and avoid pops.
        synth.add( lag = new LinearRamp() );
        // Add an output mixer.
        synth.add( lineOut );
        // Connect the oscillator to the output.
        osc.output.connect( 0, lineOut.input, 0 );

        // Set the minimum, current and maximum values for the port.
        lag.output.connect( osc.amplitude );
        lag.input.setup( 0.0, 0.5, 1.0 );
        lag.time.set(  0.2 );

        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);
    }

    public void squareSound(OutMod lineOut){
        this.disconnectShapeWave(lineOut);

        // reconnect the new Oscillator
        synth.add( osc = new SquareOscillatorBL() );
        osc.output.connect( 0, lineOut.input, 0 );
        lag.output.connect( osc.amplitude );
        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);

        this.start(lineOut);



    }
    public void sawSound(OutMod lineOut){
        this.disconnectShapeWave(lineOut);

        // reconnect the new Oscillator
        synth.add( osc = new SawtoothOscillatorBL() );
        osc.output.connect( 0, lineOut.input, 0 );
        lag.output.connect( osc.amplitude );
        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);

        this.start(lineOut);



    }

    public void triangleSound(OutMod lineOut){
        this.disconnectShapeWave(lineOut);

        // reconnect the new Oscillator
        synth.add( osc = new TriangleOscillator() );
        osc.output.connect( 0, lineOut.input, 0 );
        lag.output.connect( osc.amplitude );
        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);

        this.start(lineOut);



    }
    public void stop() throws InterruptedException {
        synth.stop();
        //synth.sleepFor(2);


    }

    /**
     * Disconnet old Shape Wave
     */
    private void disconnectShapeWave(OutMod lineOut){
        synth.stop();
        osc.output.disconnect(0, lineOut.input, 0);
        lag.output.disconnect( osc.amplitude );
        synth.remove(osc);
    }

    /**
     *
     * @param octave between -5 and +5
     */
    public void changeOctave(int octave){
        this.octave = octave;

        this.osc.frequency.set(this.f0*Math.pow(2,octave));

    }

    public void start(OutMod lineOut){
        synth.start();
        lineOut.start();

    }

}
