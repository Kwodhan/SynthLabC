package com.istic;

/**
 * User Story #9 - Manipulation de signaux
 */
public class Constraints {

    private static Integer maxAudioAmplitudeInVolt = 5;
    private static Integer minAudioAmplitudeInVolt = -5;

    private static Integer maxModulationAmplitudeInVolt = 10;
    private static Integer minModulationAmplitudeInVolt = -10;

    private static Integer minBandwithInHertz = 22000;

    /**
     * Amplitude des signaux audio : −5 V à + 5V
     */
    static Boolean isAudioAmplitudeOk(Integer value) {
        return value > minAudioAmplitudeInVolt && value < maxAudioAmplitudeInVolt;
    }

    /**
     * Les signaux de modulation peuvent dépasser cette amplitude (de −10 V à +10 V)
     */
    static Boolean isModulationAmplitudeOk(Integer value) {
        return value > minModulationAmplitudeInVolt && value < maxModulationAmplitudeInVolt;
    }

    /**
     * Bande passante de 22 kHz minimum
     */
    static Boolean isBandwithOk(Integer value) {
        return value > minBandwithInHertz;
    }

}
