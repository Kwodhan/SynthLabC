package com.istic;

import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class VCO implements Module{


    private final Double f0 = 440.0;

    public UnitOscillator getOsc() {
        return osc;
    }

    private UnitOscillator osc;
    private SquareOscillator square;
    private TriangleOscillator triangle;
    private SawtoothOscillator saw;
    private Integer octave = 0;
    private Synthesizer synth;
    // Port
    private PortOutput portOutput;



    public VCO(Synthesizer synth){

        this.synth = synth;

        this.synth.add( osc = new SquareOscillator() );
        this.portOutput = new PortOutput(this,this.osc.output);
        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);
    }

    public void changeShapeWave(ShapeWave shapeWave) {
        this.synth.stop();
        this.osc.stop();
        System.out.println("this.osc before get instance" + this.osc.getClass());
        this.synth.remove(this.osc);
        

        this.osc = shapeWave.getInstance();
        System.out.println("this.osc before after instance" + this.osc.getClass());
        this.synth.add(this.osc);
        this.portOutput = new PortOutput(this,this.osc.output);
        this.osc.frequency.setup(30, this.f0 * Math.pow(2, this.octave), 10000);
        //this.osc.frequency.set(this.f0*Math.pow(2,octave));

        //this.start();


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
