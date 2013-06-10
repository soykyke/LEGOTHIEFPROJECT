package view;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

public class Sound {

	public static Sound win = loadSound("sounds/win.wav");
	public static Sound dogbark = loadSound("sounds/dogbark.wav");
	public static Sound lose = loadSound("sounds/lose.wav");
	public static Sound pressKey = loadSound("sounds/press_key.wav");
	public static Sound safeFail = loadSound("sounds/safe_fail.wav");
	public static Sound safeOpen = loadSound("sounds/safe_open.wav");

	private Clip clip;

	protected Sound () {}
	
	protected static Sound loadSound(String fileName)  {
			try {
				Sound s = new Sound();
				AudioInputStream ais = AudioSystem.getAudioInputStream(new File(
						fileName));
				Clip clip = (Clip) AudioSystem.getLine(new DataLine.Info(Clip.class, ais
						.getFormat()));
				clip.open(ais);
				s.clip = clip;
				return s;
			} catch (Exception e) {
				System.err.println("Error loading sound " + fileName);
				e.printStackTrace();
			}
			return null;
	}

	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.err.println("Error when playing sound.");
			e.printStackTrace();
		}
	}
}
