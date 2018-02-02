package com.istic;

import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.UnitOscillator;

public class VCO implements Module{


    private final Double f0 = 440.0;
    private UnitOscillator osc;
    private Integer octave = 0;
    private Synthesizer synth;
    // Port
    private PortOutput portOutput;


    public VCO(Synthesizer synth){

        this.synth = synth;

        this.synth.add( osc = new SquareOscillatorBL() );

        this.portOutput = new PortOutput(this,this.osc.output);

        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);

    }

    public void changeShapeWave(ShapeWave shapeWave) {



        // Disconnect

        this.synth.remove(osc);

        // Reconnect
        this.synth.add(osc = shapeWave.getInstance());

        this.osc.frequency.setup(30, this.f0 * Math.pow(2, octave), 10000);
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


    public void start(){
        osc.start();

    }

    public void stop() throws InterruptedException {
        osc.stop();

    }

    public PortOutput getPortOutput() {
        return portOutput;
    }
}
