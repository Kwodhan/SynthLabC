package com.istic.fileformat;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AudioFileWAV fw = new AudioFileWAV("/home/kristof/tcw.wav");
		AudioFileMP3 fm = new AudioFileMP3("/home/kristof/tm.mp3");
		AudioFile.convert(fm,fw);
			
	}

}
