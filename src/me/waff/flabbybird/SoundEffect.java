package me.waff.flabbybird;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum SoundEffect {
	
	DIE("/sound/die.wav"),
	HIT("/sound/hit.wav"),
	POINT("/sound/point.wav"),
	WING("/sound/wing.wav");
	
	public static enum Volume {
		MUTE, LOW, MEDIUM, HIGH;
	}
	
	public static Volume volume = Volume.HIGH;
	
	private Clip clip;
	
	SoundEffect(String path) {
		try {
			URL url = SoundEffect.class.getResource(path);
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
			this.clip = AudioSystem.getClip();
			clip.open(audioInputStream);
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void play(){
		if (volume != Volume.MUTE){
			if (clip.isRunning())
				clip.stop();
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	public void stop(){
		clip.stop();
		clip.setFramePosition(0);
	}
	
	public void mute(){
		volume = Volume.MUTE;
	}
	
}
