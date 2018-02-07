package com.istic.fileformat;

public class AudioFileMP3  extends AudioFile{
float sampling_freq= 44.1f; //kHz
int taux_compression = 128; //kbit/s 

	public AudioFileMP3(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

 


@Override
public void get_float_data() {
	// istancie un audiofilewav, converti en wav, puis appel get_float_data du fichier .wav
	
}
}
