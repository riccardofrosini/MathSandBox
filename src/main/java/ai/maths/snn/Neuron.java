package ai.maths.snn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.HashMap;

import ai.maths.neat.utils.RandomUtils;

public class Neuron<T extends InputStream> implements Runnable {

    protected float growth;
    protected T in;
    protected HashMap<Neuron<PipedInputStream>, OutputStream> connected;

    Neuron(T in) {
        this.growth = 1;
        this.in = in;
        this.connected = new HashMap<>();
    }

    protected void connect(Neuron<PipedInputStream> n) {
        try {
            connected.put(n, new PipedOutputStream(n.in));
        } catch (IOException e) {
        }
    }

    protected void reproduce() {
        try {
            if (!(this instanceof NonReproducibleNeuron)) {
                PipedInputStream pipedInputStream = new PipedInputStream();
                PipedOutputStream pipedOutputStream = new PipedOutputStream(pipedInputStream);
                Neuron<PipedInputStream> neuron = new Neuron<>(pipedInputStream);
                connected.put(neuron, pipedOutputStream);
                new Thread(neuron).start();
            }
        } catch (IOException e) {
        }
    }

    public void run() {
        while (true) {
            try {
                if (in.available() > 4) {
                    float floatSignal = ConfigAndUtils.byteArrayToFloat(in.readNBytes(4));
                    float newFloatSignal = floatSignal * growth;
                    if ((connected.size() < 2 || growth > 1) && RandomUtils.getRandomInt(ConfigAndUtils.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
                        reproduce();
                    }
                    if (growth > 2 && RandomUtils.getRandomInt(ConfigAndUtils.CONNECTION_PROBABILITY_1_OUT_OF) < 1) {
                        connected.keySet().stream().flatMap(neuron -> neuron.connected.keySet().stream())
                                .filter(neuron -> !connected.containsKey(neuron) && neuron != this).findAny()
                                .ifPresent(this::connect);
                    }
                    if (newFloatSignal >= growth * connected.size()) {
                        growth = growth * ConfigAndUtils.INCREASE;
                    } else {
                        growth = growth * ConfigAndUtils.DECREASE;
                    }
                    for (OutputStream pipedOutputStream : connected.values()) {
                        pipedOutputStream.write(ConfigAndUtils.float2ByteArray(newFloatSignal));
                        pipedOutputStream.flush();
                    }
                } else {
                    Thread.sleep(10);
                }
            } catch (IOException | InterruptedException e) {

            }
        }
    }

    private static class NonReproducibleNeuron extends Neuron<PipedInputStream> {

        public NonReproducibleNeuron(PipedInputStream in, OutputStream out) {
            super(in);
            connected.put(this, out);
        }
    }

    public static class NonConnectibleNeuron extends Neuron<InputStream> {

        public NonConnectibleNeuron(InputStream in, OutputStream out) {
            super(in);
            NonReproducibleNeuron nonReproducibleNeuron = new NonReproducibleNeuron(new PipedInputStream(), out);
            this.connect(nonReproducibleNeuron);
            new Thread(nonReproducibleNeuron).start();
        }
    }
}
