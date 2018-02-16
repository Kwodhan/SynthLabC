package com.istic.keyboard;

import java.sql.Timestamp;

import com.istic.Constraints;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitGatePort;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;
import com.jsyn.unitgen.UnitGenerator;

import javafx.scene.control.TextArea;

public class ReglageKB extends UnitGenerator {
    private final int octave_min = -5;
    private final int octave_max = 4;
    private int octave = 0;
    private final double Vo = 1.0476;
    private boolean notes[] = new boolean[13];
    private double notes_hz[] = new double[13];
    private String notes_descr[] = {
            "DO", "DOd", "RE", "REd", "MI", "FA", "FAd", "SOL", "SOLd", "LA", "LAd", "SI", "DO2"};
    private String notes_touch[] = {
            "Q", "Z", "S", "E", "D", "F", "T", "G", "Y", "H", "U", "J", "K"};
    private double cur_hz = 0;

    private boolean firsttime = true;

    private UnitOutputPort portGate;
    private UnitOutputPort portCv;


    boolean notes_playing[] = new boolean[13];
    long notes_timestamp[] = new long[13];

    public ReglageKB() {


        addPort(portGate = new UnitOutputPort());
        addPort(portCv = new UnitOutputPort());

    }


    /////////////////////////////////////////////////////////////
    @Override
    public void generate(int start, int limit) {
        double[] outputsGate = portGate.getValues();
        double[] outputsCv = portCv.getValues();


        for (int i = start; i < limit; ++i) {

            outputsGate[i] = get_gate() / Constraints.VOLT;

            outputsCv[i] = get_cv() / Constraints.VOLT;

        }


    }




    public double get_cv() {

        return octave + ((double) this.get_last_note() / 12);
    }

    public double get_gate() {
        for (int i = 0; i < 13; i++) {
            if (notes_playing[i]) {
                return Constraints.VOLT;
            }
        }
        return -Constraints.VOLT;
    }


    private void play_signal(double hz, boolean play) {
        if (firsttime) {
            firsttime = false;
            cur_hz = hz;
            return;
        }
        if (!play) {
            cur_hz = 0;
            return;
        }

        cur_hz = hz;


    }


    public String update_ouput_signal() {
        update_playing();

        return "";

    }

    private int get_last_note() {
        long max = -1;
        int index = -1;
        for (int i = 0; i < 13; i++) {
            if (max < notes_timestamp[i]) {
                max = notes_timestamp[i];
                index = i;
            }
        }
        return index;
    }


    private void update_playing() {
        int i = get_last_note();

        if (notes[i] == true && !notes_playing[i]) {
            start_playing(i);
            return;
        }


        if (notes[i] == false && notes_playing[i]) {
            stop_playing(i);
            return;

        }
    }


    private void stop_playing(int i) {
        notes_playing[i] = false;
        play_signal(notes_hz[i], false);


    }


    private void start_playing(int i) {
        stop_playing_all();
        notes_playing[i] = true;
        play_signal(notes_hz[i], true);

    }

    private void stop_playing_all() {
        for (int i = 0; i < 13; i++) {
            if (notes_playing[i]) {
                stop_playing(i);
                notes_playing[i] = false;
            }
        }
    }


    /////////////////////////////////////////////////////////////////:
    protected String print_frequencies() {
        String res = "Octave : "+octave+"\n";
        for (int i = 0; i < 13; i++) {
            double result = (double) Math.round(((octave + (i / 12d)) * 100d)) / 100d;
            if (notes[i] == true) {
                res += "[o] " + notes_descr[i] + " : " + this.notes_touch[i] + " : " + result + " V\n";
            } else {
                res += "[  ] " + notes_descr[i] + " : " + this.notes_touch[i] + " : " + result + " V\n";

            }
        }
        return res;
    }


    private void compute_frequency(int index) {
        //		notes_hz[note]=  octave*0; //la formule...
        notes_hz[index] = formule_frequency_note(this.octave, index);

    }

    private double formule_frequency_note(int octave, int index) {//r=1.05946f
        return this.Vo * Math.pow(2, octave - 5) * Math.pow(2, index / 12.0);

    }

    private long get_timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime();
    }

    protected void toggle_on(int note) {
        this.notes_timestamp[note] = get_timestamp();
        if (this.notes[note] == false) {
            this.notes[note] = true;
            this.compute_frequency(note);
            this.update_ouput_signal();
        } else {
            this.update_ouput_signal();

        }
    }

    protected void toggle_off(int note) {
        this.notes_timestamp[note] = get_timestamp();

        if (this.notes[note] == true) {

            this.notes[note] = false;
            this.update_ouput_signal();
        } else {
            this.update_ouput_signal();

        }
    }


    public void onpressOctaveUP() {
        if (this.octave < this.octave_max) octave++;
        this.update_ouput_signal();
    }

    public void onpressOctaveDOWN() {
        if (this.octave > this.octave_min) octave--;
        this.update_ouput_signal();

    }


    public UnitOutputPort getPortGate() {
        return portGate;
    }

    public UnitOutputPort getPortCv() {
        return portCv;
    }
}
