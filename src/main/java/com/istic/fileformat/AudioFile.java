package com.istic.fileformat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncoderException;
import it.sauronsoftware.jave.EncodingAttributes;
import it.sauronsoftware.jave.InputFormatException;

public abstract class AudioFile {
String path;
byte [] raw_data;


public AudioFile (String path) {
	this.path=path;
}
public void load() {
	try {
		raw_data = Files.readAllBytes(Paths.get(path));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public void save() {
	try {
		Files.write(  Paths.get(path), raw_data);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
static void convert ( AudioFileWAV afw, AudioFileMP3 afm) {
	File source = new File(afw.path);
	File target = new File(afm.path);
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

static void convert ( AudioFileMP3 afm, AudioFileWAV afw) {
	File source = new File(afm.path);
	File target = new File(afw.path);
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
	}}
 
abstract public    void play();
}
