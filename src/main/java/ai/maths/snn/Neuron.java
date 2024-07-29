package ai.maths.snn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import ai.maths.neat.utils.RandomUtils;

public class Neuron extends ConnectibleReproducibleNeuron<MyPipedInputStream> {

    protected float growth;

    public Neuron(MyPipedInputStream in, MyPipedOutputStream out) {
        super(in, out);
        this.growth = 1;
    }


    public synchronized void pipeline() {
        while (true) {
            try {
                if (in.available() > 4) {
                    float signal = ByteBuffer.wrap(in.readNBytes(4)).getFloat();
                    float newSignal = signal * growth;
                    System.out.println(signal + " " + newSignal);
                    if ((connected.size() < 2 || growth > 1) && RandomUtils.getRandomInt(ConfigAndUtils.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
                        reproduce();
                    }
                    if (connected.size() > 2 && RandomUtils.getRandomInt(ConfigAndUtils.CONNECTION_PROBABILITY_1_OUT_OF) < 1) {
                        makeConnectionOfTwoOutbounds();
                    }
                    if (Math.abs(newSignal) >= growth * connected.size()) {
                        growth = Math.min(growth * ConfigAndUtils.INCREASE, 2);
                    } else {
                        growth = Math.max(growth * ConfigAndUtils.DECREASE, 0.01f);
                    }
                    for (AbstractNeuron<MyPipedInputStream, ?> pipedOutputStream : connected) {
                        pipedOutputStream.run();
                    }
                } else {
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {
            }
        }
    }

    public static class OutputNeuron extends AbstractNeuron<MyPipedInputStream, OutputStream> {

        public OutputNeuron(OutputStream out) {
            super(new MyPipedInputStream(), out);
        }
    }

    public static class InputNeuron extends ConnectibleReproducibleNeuron<InputStream> {

        public InputNeuron(InputStream in, OutputStream out) {
            super(in, new MyPipedOutputStream());
            OutputNeuron outputNeuron = new OutputNeuron(out);
            this.connect(outputNeuron);
        }
    }
}
