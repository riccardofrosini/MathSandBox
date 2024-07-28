package ai.maths.snn;

import static ai.maths.snn.ConfigAndUtils.AUDIO_FORMAT;
import static ai.maths.snn.ConfigAndUtils.SAMPLE_RATE;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * A sample program is to demonstrate how to record sound in Java author: www.codejava.net
 */
public class AuditoryOutput {

    private SourceDataLine line;

    public AuditoryOutput() throws LineUnavailableException {
        this.line = AudioSystem.getSourceDataLine(AUDIO_FORMAT);
    }

    /**
     * Captures the sound and record into a WAV file
     *
     * @return
     */
    public OutputStream start() throws LineUnavailableException {
        line.open(AUDIO_FORMAT);
        line.start();
        return new BufferedOutputStream(new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                throw new IOException("Unexpected error Buffered Stream should not use this method");
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                line.write(b, off, len);
            }

            @Override
            public void close() {
                finish();
            }

        });
    }

    void finish() {
        line.stop();
        line.close();
    }

    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        OutputStream start = new AuditoryOutput().start();

        for (double freq = 400; freq <= 800; ) {
            byte[] toneBuffer = createSinWaveBuffer(freq, 50);
            start.write(toneBuffer, 0, toneBuffer.length);
            freq++;
        }
    }

    public static byte[] createSinWaveBuffer(double freq, int ms) {
        int samples = (int) ((ms * SAMPLE_RATE) / 1000);
        byte[] output = new byte[samples];
        double period = (double) SAMPLE_RATE / freq;
        for (int i = 0; i < output.length; i++) {
            double angle = 2.0 * Math.PI * i / period;
            output[i] = (byte) (Math.sin(angle) * 127f);
        }

        return output;
    }

}