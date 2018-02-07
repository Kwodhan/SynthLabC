package com.istic.fileformat;

public class AudioFileMP3  extends AudioFile{
float sampling_freq= 44.1f; //kHz
int taux_compression = 128; //kbit/s 
	public AudioFileMP3(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}

public void convert_from_wav (AudioFileWAV afw) {
	
}

@Override
public void play() {
	// TODO Auto-generated method stub
	
}
}
