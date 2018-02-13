package com.istic.fileformat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.jsyn.Synthesizer;
import com.jsyn.data.DoubleTable;
import com.jsyn.data.FloatSample;
import com.jsyn.data.ShortSample;
import com.jsyn.unitgen.VariableRateMonoReader;
import com.jsyn.util.SampleLoader;
import com.jsyn.util.WaveRecorder;

public class AudioFileWAV extends AudioFile{
//	  int  FileTypeBlocID =0;// (4 octets) : Constante «RIFF»  (0x52,0x49,0x46,0x46)
//	  int  FileSize  =0 ;//     (4 octets) : Taille du fichier moins 8 octets
//	  int  FileFormatID  =0;//    (4 octets) : Format = «WAVE»  (0x57,0x41,0x56,0x45)
//
//			int FormatBlocID =0 ;//    (4 octets) : Identifiant «fmt »  (0x66,0x6D, 0x74,0x20)
//			int  BlocSize =0 ;//        (4 octets) : Nombre d'octets du bloc - 16  (0x10)
//
//	   short AudioFormat =1 ;//     (2 octets) : Format du stockage dans le fichier (1: PCM, ...)
//	   short NbrCanaux =0 ;//       (2 octets) : Nombre de canaux (de 1 à 6, cf. ci-dessous)
//	   int Frequence   =44100 ;//     (4 octets) : Fréquence d'échantillonnage (en hertz) [Valeurs standardisées : 11 025, 22 050, 44 100 et éventuellement 48 000 et 96 000]
//	   int  BytePerSec  =0 ;//     (4 octets) : Nombre d'octets à lire par seconde (c.-à-d., Frequence * BytePerBloc).
//	   short BytePerBloc  =0  ;//   (2 octets) : Nombre d'octets par bloc d'échantillonnage (c.-à-d., tous canaux confondus : NbrCanaux * BitsPerSample/8).
//	   short BitsPerSample  =16 ;//  (2 octets) : Nombre de bits utilisés pour le codage de chaque échantillon (8, 16, 24)
//
//			int  DataBlocID  =0 ;//     (4 octets) : Constante «data»  (0x64,0x61,0x74,0x61)
//			int  DataSize    =0 ;//     (4 octets) : Nombre d'octets des données (c.-à-d. 
//	   
//			byte [] header ;
//			byte [] data ;
			
			DoubleTable myTable;
			WaveRecorder wr;
			float frameRate =44100.0f;
			int nbChannels=2;
	public AudioFileWAV(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}
 
 
	@Override
	public void get_float_data() {
		// TODO Auto-generated method stub
		VariableRateMonoReader samplePlayer = new VariableRateMonoReader();
	}
	////////////////////////////
	public  void read_from_file () {
	try {
		FloatSample clarinetSample = SampleLoader.loadFloatSample(new File(this.path));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//this.myTable = new DoubleTable( clarinetSample );
	}
	 public void record_start( Synthesizer synth,   int samplesPerFrame, int bitsPerSample){
		 try {
			this.wr= new WaveRecorder(synth,new File(this.path),samplesPerFrame,bitsPerSample);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

//	 public void record_pause() {
//		 wr.stop();
//		 
//	 }

	 public void record_stop () {
		 try {
			wr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }


}
