package ai.maths.snn;

import static ai.maths.snn.Config.SAMPLE_RATE;

import java.io.IOException;
import java.io.OutputStream;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ai.maths.snn.neuralmodel.MyPipedInputStream;
import ai.maths.snn.neuralmodel.MyPipedOutputStream;

public class test {

    public static void main(String[] args) throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        MyPipedInputStream pipedInputStream = new MyPipedInputStream();
        MyPipedOutputStream pipedOutputStream1 = new MyPipedOutputStream(pipedInputStream);
        MyPipedOutputStream pipedOutputStream2 = new MyPipedOutputStream(pipedInputStream);
        MyPipedOutputStream pipedOutputStream3 = new MyPipedOutputStream(pipedInputStream);
        MyPipedOutputStream pipedOutputStream4 = new MyPipedOutputStream(pipedInputStream);
        MyPipedOutputStream pipedOutputStream5 = new MyPipedOutputStream(pipedInputStream);
        MyPipedOutputStream pipedOutputStream6 = new MyPipedOutputStream(pipedInputStream);
        System.out.println("Loaded");
        pipedOutputStream1.write(1);
        pipedOutputStream1.flush();
        pipedOutputStream6.write(6);
        pipedOutputStream6.flush();
        pipedOutputStream2.write(2);
        pipedOutputStream2.flush();
        pipedOutputStream4.write(4);
        pipedOutputStream4.flush();
        pipedOutputStream5.write(5);
        pipedOutputStream5.flush();
        pipedOutputStream3.write(3);
        pipedOutputStream3.flush();
        System.out.println("Wrote");
        System.out.println(pipedInputStream.read());
        System.out.println(pipedInputStream.read());
        System.out.println(pipedInputStream.read());
        System.out.println(pipedInputStream.read());
        System.out.println(pipedInputStream.read());
        System.out.println(pipedInputStream.read());

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
