package com.istic;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.SquareOscillatorBL;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;

public class VCO implements Module{

    /**
     * default : LA
     */
    private Double f0 = 440.0;
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
    private UnitOscillator oscSquare;
    private UnitOscillator oscTriangle;
    private UnitOscillator oscSawtooth;
    private UnitOscillator[] oscs;


    private Synthesizer synth;
    // Port
    private PortOutput portOutput;

    private PortInput portfm;


    public VCO(Synthesizer synth) {

        this.synth = synth;
        this.oscs = new UnitOscillator[3];
        this.synth.add( oscs[0] = new SquareOscillator());
        this.synth.add( oscs[1] = new TriangleOscillator());
        this.synth.add( oscs[2] = new SawtoothOscillator());
        osc = oscs[0];
        this.portOutput = new PortOutput(this,this.osc.output);

        this.portfm = new PortInput(this, new UnitInputPort("Inputfm"));

        osc.frequency.setup( 30, this.f0*Math.pow(2,octave), 10000);

    }

    public void changeShapeWave(int i, OutMod outmod) {

        // Disconnect
       outmod.input.disconnectAll();
       oscs[i].output.connect(outmod.input);
       osc = oscs[i];
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
    
    public void setPortOutput(){
    	this.portOutput = new PortOutput(this,this.osc.output);
    }
}
