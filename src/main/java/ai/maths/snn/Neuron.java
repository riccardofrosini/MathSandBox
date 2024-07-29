package ai.maths.snn;

import static ai.maths.snn.Utils.BYTES_TO_DOUBLE;
import static ai.maths.snn.Utils.DOUBLE_TO_BYTES;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;


public class Neuron extends ConnectibleReproducibleNeuron<MyPipedInputStream> {

    public Neuron(MyPipedInputStream in, MyPipedOutputStream out) {
        super(in, out);
    }

    public static class OutputNeuron extends AbstractNeuron<MyPipedInputStream, OutputStream, Double> {

        public OutputNeuron(OutputStream out, Encoder<Double> encoder) {
            super(new MyPipedInputStream(), out, BYTES_TO_DOUBLE, encoder);
        }

        @Override
        public void pipeline() {
            try {
                if (in.available() > decoder.nBytesToRead) {
                    byte[] signal = in.readNBytes(decoder.nBytesToRead);
                    Double newSignal = decoder.transform(signal);
                    System.out.println(signal + " " + newSignal);
                    out.write(encoder.transform(newSignal));
                }
            } catch (IOException e) {
            }
        }
    }

    public static class InputNeuron extends ConnectibleReproducibleNeuron<InputStream> {

        public InputNeuron(InputStream in, OutputStream out, Decoder<Double> decoder, Encoder<Double> encoder) {
            super(in, new MyPipedOutputStream(), decoder, DOUBLE_TO_BYTES);
            OutputNeuron outputNeuron = new OutputNeuron(out, encoder);
            this.connect(outputNeuron);
        }
    }
}
