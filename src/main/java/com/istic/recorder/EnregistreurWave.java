package com.istic.recorder;


import com.jsyn.Synthesizer;

import java.io.File;
import java.io.FileNotFoundException;

public class EnregistreurWave extends com.jsyn.util.WaveRecorder{

    private boolean record = false;

    public EnregistreurWave(Synthesizer synthesizer, File file) throws FileNotFoundException {
        super(synthesizer, file);
    }


    public void toggleRecord() {
        record = !record;
        if (record) {
            super.start();
        } else {
            super.stop();
        }
    }


}