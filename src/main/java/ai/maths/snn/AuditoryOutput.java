package ai.maths.snn;

import static ai.maths.snn.Config.AUDIO_FORMAT;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AuditoryOutput {

    private SourceDataLine line;

    public AuditoryOutput() throws LineUnavailableException {
        this.line = AudioSystem.getSourceDataLine(AUDIO_FORMAT);
    }

    public OutputStream start() throws LineUnavailableException {
        line.open(AUDIO_FORMAT);
        line.start(); // start playing
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
}