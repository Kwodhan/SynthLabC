package com.istic.fileformat;

import it.sauronsoftware.jave.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AudioFile {
String path;
byte [] raw_data;


public AudioFile (String path) {
	this.path=path;
}


public void convertToMP3 (String destination) {
	File source = new File(this.path);
	File target = new File(destination);
	AudioAttributes audio = new AudioAttributes();
	audio.setCodec("libmp3lame");
	audio.setBitRate(new Integer(128000));
	audio.setChannels(new Integer(2));
	audio.setSamplingRate(new Integer(44100));
	EncodingAttributes attrs = new EncodingAttributes();
	attrs.setFormat("mp3");
	attrs.setAudioAttributes(audio);
	Encoder encoder = new Encoder();
	try {
		encoder.encode(source, target, attrs);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InputFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (EncoderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

/*public void convertToWav ( String destination) {
	File source = new File(this.path);
	File target = new File(destination);
	AudioAttributes audio = new AudioAttributes();
//	audio.setCodec("pcm_s16le");
//	audio.setBitRate(new Integer(128000));
//	audio.setChannels(new Integer(2));
//	audio.setSamplingRate(new Integer(44100));
	EncodingAttributes attrs = new EncodingAttributes();
	attrs.setFormat("wav");
	attrs.setAudioAttributes(audio);
	Encoder encoder = new Encoder();
	try {
		encoder.encode(source, target, attrs);
	} catch (IllegalArgumentException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InputFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (EncoderException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}}*/
 
}
