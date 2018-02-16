package com.istic.mixer;

import com.istic.port.PortInput;
import com.istic.port.PortOutput;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.Circuit;

/**
 * Module mixer
 */
public class MIXER  extends Circuit {

    /**
     * Port de sortie
     */
    private PortOutput portOutput;
    /**
     * Les quatre port d'entrées
     */
    private PortInput portInput1,portInput2,portInput3,portInput4;

    /**
     * Réglage des atténuateurs
     */
    private ReglageMIXER mixer;

    public MIXER() {
        UnitInputPort in1,in2,in3,in4;
        UnitOutputPort out;
        add(mixer= new ReglageMIXER());
        addPortAlias(in1 = mixer.getIn1(),"in1");
        addPortAlias(in2 = mixer.getIn2(),"in2");
        addPortAlias(in3 = mixer.getIn3(),"in3");
        addPortAlias(in4 = mixer.getIn4(),"in4");
        addPortAlias(out = mixer.getOut(),"out");
        mixer.getIn1Att().set(0);
        mixer.getIn2Att().set(0);
        mixer.getIn3Att().set(0);
        mixer.getIn4Att().set(0);

        portInput1 = new PortInput(in1);
        portInput2 = new PortInput(in2);
        portInput3 = new PortInput(in3);
        portInput4 = new PortInput(in4);
        portOutput = new PortOutput(out);
    }

    public void changeAtt1(double att)
    {
        this.mixer.getIn1Att().set(att);
    }

    public void changeAtt2(double att)
    {
        this.mixer.getIn2Att().set(att);
    }

    public void changeAtt3(double att)
    {
        this.mixer.getIn3Att().set(att);
    }

    public void changeAtt4(double att)
    {
        this.mixer.getIn4Att().set(att);
    }

    //Setters & Getters
    public PortOutput getOutput() {

        return  portOutput;
    }
    public PortInput getInput1() {

        return portInput1;
    }

    public PortInput getInput2() {

        return portInput2;
    }

    public PortInput getInput3() {

        return portInput3;
    }

    public PortInput getInput4() {

        return portInput4;
    }
}
