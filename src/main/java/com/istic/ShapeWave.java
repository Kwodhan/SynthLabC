package com.istic;

import com.jsyn.unitgen.SawtoothOscillator;
import com.jsyn.unitgen.SquareOscillator;
import com.jsyn.unitgen.TriangleOscillator;
import com.jsyn.unitgen.UnitOscillator;

public enum ShapeWave {
    Square(new SquareOscillator()),
    Triangle(new TriangleOscillator()),
    Saw(new SawtoothOscillator());

    UnitOscillator oscillator;


    ShapeWave(UnitOscillator oscillator){
        this.oscillator = oscillator;
    }

    public UnitOscillator getInstance(){
        return oscillator;
    }


}
