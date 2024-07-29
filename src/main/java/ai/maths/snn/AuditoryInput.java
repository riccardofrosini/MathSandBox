package ai.maths.snn;

import static ai.maths.snn.ConfigAndUtils.AUDIO_FORMAT;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AuditoryInput {

    private TargetDataLine line;
    private InputStream in;

    public AuditoryInput() throws LineUnavailableException {
        this.line = AudioSystem.getTargetDataLine(AUDIO_FORMAT);
        in = new AudioInputStream(line);
    }

    public InputStream start() throws LineUnavailableException {
        line.open(AUDIO_FORMAT);
        line.start();   // start capturing
        return in;
    }

    void finish() {
        line.stop();
        line.close();
    }

}