package com.istic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class VCO {


    private final double f0 = 440.0;
    private Synthesizer synth;
    private UnitOscillator osc;
    private LinearRamp lag;
    private LineOut lineOut;
    private int octave = 0;

    public VCO() {
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

        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);
    }

    public void changeShapeWave(ShapeWave shapeWave) {

        // Disconnect
        synth.stop();
        osc.output.disconnect(0, lineOut.input, 0);
        lag.output.disconnect( osc.amplitude );
        synth.remove(osc);

        // Reconnect
        synth.add(osc = shapeWave.getInstance());
        osc.output.connect(0, lineOut.input, 0);
        lag.output.connect(osc.amplitude);
        osc.frequency.setup(30, this.f0 * Math.pow(2, octave), 10000);
        this.start();


    }



    /**
     *
     * @param octave between -5 and +5
     */
    public void changeOctave(int octave){
        this.octave = octave;

        this.osc.frequency.set(this.f0*Math.pow(2,octave));

    }

    /**
     *
     * @param hertz 0 and
     */
    public void changeFineHertz(double hertz ){

        float r =  1.05946f;
        System.out.println((440*Math.pow(2,octave))*Math.pow(r,hertz));

        this.osc.frequency.set((440*Math.pow(2,octave))*Math.pow(r,hertz));

    }

    public void stop(){
        synth.stop();
    }

    public void start(){
        synth.start();
        lineOut.start();

    }

}
