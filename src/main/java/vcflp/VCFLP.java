package vcflp;

import com.istic.port.PortAm;
import com.istic.port.PortFm;
import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;

public class VCFLP extends Circuit {
    /**
     * Port de sortie du VCF LP
     */
    private UnitOutputPort out;

    private UnitInputPort in;

    private PortOutput portOutput;

    private PortInput portInput;

    private PortFm portFm;
    /**
     * reglage fm, f0, filter
     */
    private ReglageVCFLP vcflp;

    public VCFLP() {

        add(vcflp = new ReglageVCFLP());

        addPortAlias(out = vcflp.getOut(), "out");
        addPortAlias(in = vcflp.getInput(), "in");


        vcflp.getF0().set(0);
        vcflp.getFilter().set(0);



        portOutput = new PortOutput(out);
        portInput = new PortInput(in);
        portFm = new PortFm(this.vcflp.getFm());

    }
    public PortOutput getOutput() {

        return portOutput;
    }
    public PortInput getInput() {

        return portInput;
    }
    public PortFm getFm(){
        return portFm;
    }


    public void changeF0(double f0){
        this.vcflp.getF0().set(f0);
    }

    public void changeFilter(double filter){
        this.vcflp.getFilter().set(filter);
    }

}
