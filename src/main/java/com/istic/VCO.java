package com.istic;

import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;

public class VCO implements Module{


    private final Double f0 = 440.0;
    private UnitOscillator osc;
    private SquareOscillator square;
    private TriangleOscillator triangle;
    private SawtoothOscillator saw;
    private Integer octave = 0;
    private Synthesizer synth;
    // Port
    private PortOutput portOutput;
    private PortOutput portOutputsquare;
    private PortOutput portOutputsaw;
    private PortOutput portOutputtriangle;


    public VCO(Synthesizer synth){

        this.synth = synth;

        this.synth.add( osc = new SquareOscillator() );
        this.portOutput = new PortOutput(this,this.osc.output);

        this.synth.add( square = new SquareOscillator() );
        this.portOutputsquare = new PortOutput(this,this.square.output);

        this.synth.add( saw = new SawtoothOscillator() );
        this.portOutputsaw = new PortOutput(this,this.saw.output);

        this.synth.add( triangle = new TriangleOscillator() );
        this.portOutputtriangle = new PortOutput(this,this.triangle.output);

        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);
    }

    public void changeShapeWave(ShapeWave shapeWave) {
        synth.stop();
        //this.osc.output.disconnect();


        this.synth.remove(this.osc);
      switch (shapeWave){
          case Saw:
              System.out.println("case saw");
              this.osc = this.saw;
              this.portOutput = this.portOutputsaw;
              break;
          case Square:
              System.out.println("case square");
              this.osc = this.square;
              this.portOutput = this.portOutputsquare;
              break;
          case Triangle:
              System.out.println("case triangle");
              this.osc = this.triangle;
              this.portOutput = this.portOutputtriangle;
              break;

      }
        this.synth.add(osc);
        this.osc.frequency.setup(30, this.f0 * Math.pow(2, octave), 10000);
        this.start();



        // Disconnect
//
//        this.synth.remove(this.osc);
//
//        // Reconnect
//        this.synth.add(osc = shapeWave.getInstance());





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
