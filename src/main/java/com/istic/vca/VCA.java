package com.istic.vca;

import com.istic.port.PortAm;
import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;
import com.jsyn.unitgen.PassThrough;

public class VCA extends Circuit {
    /**
     * Port de sortie du VCO
     */
    private UnitOutputPort out;

    private UnitInputPort in;



    /**
     * reglage ao, am
     */
    private ReglageVCA reglageVCA;
    /**
     * reglage wave
     */
    private PassThrough passThrough;

    public VCA() {

        add(reglageVCA = new ReglageVCA());
        add(passThrough = new PassThrough());
        addPortAlias(out = passThrough.getOutput(), "com/istic/out");
        addPortAlias(in = passThrough.getInput(), "com/istic/in");
        addPortAlias(passThrough.getInput(),"in");
        reglageVCA.getA0().set(0);



        add( passThrough = new PassThrough() );
        addPort( reglageVCA.amplitude = passThrough.input );


    }
    public PortOutput getOutput() {

        return new PortOutput(out);
    }
    public PortInput getInput() {

        return new PortInput(in);
    }
    public PortAm getAm(){
        return new PortAm(this.reglageVCA.getAm());
    }


    public double getAmplitude() {
        return reglageVCA.getAmplitude();
    }
    public void changeA0(double a0){
        this.reglageVCA.getA0().set(a0);
    }

    public void changeAm(double am){
        this.reglageVCA.getAm().set(am);
    }

}
