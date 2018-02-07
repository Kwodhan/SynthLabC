package com.istic.fileformat;

public class AudioFileWAV extends AudioFile{
	  int  FileTypeBlocID =0;// (4 octets) : Constante «RIFF»  (0x52,0x49,0x46,0x46)
	  int  FileSize  =0 ;//     (4 octets) : Taille du fichier moins 8 octets
	  int  FileFormatID  =0;//    (4 octets) : Format = «WAVE»  (0x57,0x41,0x56,0x45)

			int FormatBlocID =0 ;//    (4 octets) : Identifiant «fmt »  (0x66,0x6D, 0x74,0x20)
			int  BlocSize =0 ;//        (4 octets) : Nombre d'octets du bloc - 16  (0x10)

	   short AudioFormat =1 ;//     (2 octets) : Format du stockage dans le fichier (1: PCM, ...)
	   short NbrCanaux =0 ;//       (2 octets) : Nombre de canaux (de 1 à 6, cf. ci-dessous)
	   int Frequence   =44100 ;//     (4 octets) : Fréquence d'échantillonnage (en hertz) [Valeurs standardisées : 11 025, 22 050, 44 100 et éventuellement 48 000 et 96 000]
	   int  BytePerSec  =0 ;//     (4 octets) : Nombre d'octets à lire par seconde (c.-à-d., Frequence * BytePerBloc).
	   short BytePerBloc  =0  ;//   (2 octets) : Nombre d'octets par bloc d'échantillonnage (c.-à-d., tous canaux confondus : NbrCanaux * BitsPerSample/8).
	   short BitsPerSample  =16 ;//  (2 octets) : Nombre de bits utilisés pour le codage de chaque échantillon (8, 16, 24)

			int  DataBlocID  =0 ;//     (4 octets) : Constante «data»  (0x64,0x61,0x74,0x61)
			int  DataSize    =0 ;//     (4 octets) : Nombre d'octets des données (c.-à-d. 
	   
			byte [] header ;
			byte [] data ;
 
	public AudioFileWAV(String path) {
		super(path);
		// TODO Auto-generated constructor stub
	}
 
	@Override
	public void play() {
		// TODO Auto-generated m
	}
 
	@Override
	public void play() {
	}
 
	@Override
	public void play() {ethod stub
		
	}
	 

}
