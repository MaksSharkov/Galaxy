package model;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	private boolean released = false;
	private Clip clip = null;
	private FloatControl volumeC = null;
	private boolean playing = false;

	protected static final Object LOCK = new Object();

	public Sound(String path) {
		try {
			InputStream audioSrc = getClass().getResourceAsStream(path);
			InputStream bufferedIn = new BufferedInputStream(audioSrc);
			AudioInputStream stream = AudioSystem
					.getAudioInputStream(bufferedIn);
			clip = AudioSystem.getClip();
			clip.open(stream);
			clip.addLineListener(new ListenerLine());
			volumeC = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			released = true;
		} catch (IOException exc) {
			exc.printStackTrace();
			released = false;
		} catch (UnsupportedAudioFileException exc) {
			exc.printStackTrace();
		} catch (LineUnavailableException exc) {
			exc.printStackTrace();
		}
	}

	public boolean isReleased() {
		return released;
	}

	public boolean isPlaying() {
		return playing;
	}

	public void play(boolean breakOld) {
		if (released) {
			if (breakOld) {
				clip.stop();
				clip.setFramePosition(0);
				clip.start();
				playing = true;
			} else if (!isPlaying()) {
				clip.setFramePosition(0);
				clip.start();
				playing = true;
			}
		}
	}

	public void play() {
		play(true);
	}

	public void stop() {
		if (playing) {
			clip.stop();
		}
	}

	public void setVolume(float x) {
		if (x < 0) {
			x = 0;
		}
		if (x > 1) {
			x = 1;
		}
		float min = volumeC.getMinimum();
		float max = volumeC.getMaximum();
		volumeC.setValue((max - min) * x + min);
	}

	public float getVolume() {
		float v = volumeC.getValue();
		float min = volumeC.getMinimum();
		float max = volumeC.getMaximum();
		return (v - min) / (max - min);
	}

	public void join() {
		if (!released) {
			return;
		}
		synchronized (clip) {
			try {
				while (playing) {
					clip.wait();
				}
			} catch (InterruptedException exc) {
				exc.printStackTrace();
			}
		}
	}

	private class ListenerLine implements LineListener {
		public void update(LineEvent ev) {
			if (ev.getType() == LineEvent.Type.STOP) {
				playing = false;
				synchronized (clip) {
					clip.notify();
				}
			}
		}
	}
}
