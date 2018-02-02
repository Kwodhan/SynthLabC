package com.istic;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;
import com.jsyn.ports.UnitInputPort;


public class VCO implements Module{

    /**
     * default : LA
     */
    private Double f0 = 440.0;

    private final Double f0 = 440.0;

    public UnitOscillator getOsc() {
        return osc;
    }

    private UnitOscillator osc;
    /**
     * octave beetween -3 and +3
     */
    private Integer octave = 0;
    /**
     * reglage fin
     */
    private Double fin = 0d;

    private Double fm = 0d;


    private Synthesizer synth;
    // Port
    private PortOutput portOutput;

    private PortInput portfm;


    public VCO(Synthesizer synth) {

        this.synth = synth;

        this.synth.add( osc = new SquareOscillatorBL() );

        this.portOutput = new PortOutput(this,this.osc.output);

        this.portfm = new PortInput(this, new UnitInputPort("Inputfm"));

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


        // Reconnect
        this.synth.add(osc = shapeWave.getInstance());

        this.osc.frequency.setup(30, this.f0 * Math.pow(2, octave), 10000);
        this.start();


    }

    /**
     *
     * @param octave between -3 and +3
     */
    public void changeOctave(int octave) {
        this.octave = octave;

        float r =  1.05946f;
        this.osc.frequency.set(this.f0 * Math.pow(2,octave) * Math.pow(r,fin)  * Math.pow(2,fm));
        System.out.println(this.getFrequence());

    }

    /**
     *
     * @param fin -9 and 2
     */
    public void changeFineHertz(double fin ) {
        if(fin < -9 && fin > 2 ){
            return;
        }
        this.fin = fin;
        float r =  1.05946f;

        this.osc.frequency.set(this.f0 * Math.pow(2,octave) * Math.pow(r,fin) * Math.pow(2,fm));
        System.out.println(this.getFrequence());


    }
    /**
     *
     * @param fm fm in hertz
     */
    public void changeFm (double fm) {

        float r =  1.05946f;
        this.fm =  Math.log(fm)/Math.log(2);

        this.osc.frequency.set(this.f0 * Math.pow(2,octave) * Math.pow(r,fin) * Math.pow(2,fm));
        System.out.println(this.getFrequence());
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

    public double getFrequence(){
        return this.osc.frequency.get();
    }
}
