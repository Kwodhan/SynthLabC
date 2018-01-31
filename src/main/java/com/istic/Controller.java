package com.istic;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.swing.ExponentialRangeModel;
import com.jsyn.swing.PortModelFactory;
import com.jsyn.swing.RotaryTextController;
import com.jsyn.unitgen.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private static final long serialVersionUID = -2704222221111608377L;
    private Synthesizer synth;
    private UnitOscillator osc;
    private LinearRamp lag;
    private LineOut lineOut;
    final ToggleGroup group = new ToggleGroup();
    VCO vco;
    @FXML
    Button startVCOButton,stopVCOButton;
    @FXML
    Slider frequencySlider;
    @FXML
    RadioButton sawRadio, traingleRadio,SquareRadio;
    public double changeFrequency(){
        return vco.getF0()*Math.pow(2,frequencySlider.getValue());


    }

    public void initialize(URL location, ResourceBundle resources) {
        vco=new VCO();
        sawRadio.setToggleGroup(group);
        traingleRadio.setToggleGroup(group);
        SquareRadio.setToggleGroup(group);
        SquareRadio.setSelected(true);
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

        osc.frequency.setup( vco.getF0()/32, vco.getF0(), vco.getF0()*32);


    }


    public void setFrequency(){
        double i=frequencySlider.getValue();
        System.out.println("+++++"+i);


    }

    public void startSoundVCO() throws InterruptedException {
    // Start synthesizer using default stereo output at 44100 Hz.
        synth.start();
        // We only need to start the LineOut. It will pull data from the
        lineOut.start();
       //Listener for the frequency change
        frequencySlider.valueProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov,
                                Number old_val, Number new_val) {
                System.out.println(osc.frequency.get());
                osc.frequency.set(changeFrequency());


            }
        });

            }
    public void squareSound(){
        synth.stop();

        synth.add( osc = new SquareOscillatorBL() );
        synth.start();
        lineOut.start();



    }
    public void sawSound(){
        synth.stop();

        synth.add( osc = new SawtoothOscillatorBL() );
        synth.start();
        lineOut.start();



    }
    public void triangleSound(){
        synth.stop();

        synth.add( osc = new TriangleOscillator() );
        synth.start();
        lineOut.start();



    }
    public void stopSoundVCO() throws InterruptedException {
        synth.stop();
        //synth.sleepFor(2);


    }

}
