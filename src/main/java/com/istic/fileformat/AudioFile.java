package com.istic.fileformat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
abstract public    void play();
}
