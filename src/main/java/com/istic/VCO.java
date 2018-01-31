package com.istic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class VCO {


    private final double f0=440.0;
    private Synthesizer synth;
    private UnitOscillator osc;
    private LinearRamp lag;
    private LineOut lineOut;

    public VCO(){
        synth = JSyn.createSynthesizer();

        // Add a tone generator.
        synth.add( osc = new SquareOscillatorBL() );
        // Add a lag to smooth out amplitude changes and avoid pops.
        synth.add( lag = new LinearRamp() );
        // Add an output mixer.
        synth.add( lineOut = new LineOut() );
        // Connect the oscillator to the output.
        osc.output.connect( 0, lineOut.input, 0 );

        // Set the minimum, current and maximum values for the port.
        lag.output.connect( osc.amplitude );
        lag.input.setup( 0.0, 0.5, 1.0 );
        lag.time.set(  0.2 );

        osc.frequency.setup( 30, this.f0, 10000);
    }

    public void squareSound(){
        synth.stop();
        synth.remove(osc);
        synth.add( osc = new SquareOscillatorBL() );
        osc.frequency.setup( 30, this.f0, 10000);
        synth.start();
        lineOut.start();



    }
    public void sawSound(){
        synth.stop();
        synth.remove(osc);
        synth.add( osc = new SawtoothOscillatorBL() );
        osc.frequency.setup( 30, this.f0, 10000);
        synth.start();
        lineOut.start();



    }
    public void triangleSound(){
        synth.stop();
        synth.remove(osc);
        synth.add( osc = new TriangleOscillator() );
        osc.frequency.setup( 30, this.f0, 10000);
        synth.start();
        lineOut.start();



    }
    public void stop() throws InterruptedException {
        synth.stop();
        //synth.sleepFor(2);


    }

    public void changeOctave(int octave){
        System.out.println(this.f0*Math.pow(2,octave));
        this.osc.frequency.set(this.f0*Math.pow(2,octave));


    }

    public void start(){
        // Start synthesizer using default stereo output at 44100 Hz.
        synth.start();
        // We only need to start the LineOut. It will pull data from the
        lineOut.start();
        //Listener for the frequency change
    }

}
