package ai.maths.snn;

import static ai.maths.snn.Config.DECREASE;
import static ai.maths.snn.Config.DECREASE_MIN;
import static ai.maths.snn.Config.INCREASE;
import static ai.maths.snn.Config.INCREASE_MAX;
import static ai.maths.snn.Utils.BYTES_TO_DOUBLE;
import static ai.maths.snn.Utils.DOUBLE_TO_BYTES;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import ai.maths.neat.utils.RandomUtils;
import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;

public abstract class ConnectibleReproducibleNeuron<I extends InputStream> extends AbstractNeuron<I, MyPipedOutputStream, Double> {

    protected HashSet<AbstractNeuron<MyPipedInputStream, ?, ?>> connected;
    protected double growth;

    public ConnectibleReproducibleNeuron(I in, MyPipedOutputStream out, Decoder<Double> decoder, Encoder<Double> encoder) {
        super(in, out, decoder, encoder);
        connected = new HashSet<>();
        growth = 1;
    }

    public ConnectibleReproducibleNeuron(I in, MyPipedOutputStream out) {
        this(in, out, BYTES_TO_DOUBLE, DOUBLE_TO_BYTES);
    }

    public void reproduce() {
        MyPipedInputStream pipedInputStream = new MyPipedInputStream();
        out.connect(pipedInputStream);
        Neuron neuron = new Neuron(pipedInputStream, new MyPipedOutputStream());
        connected.add(neuron);
        System.out.println("Reproduce " + out.sinks.size());
    }

    public void connect(AbstractNeuron<MyPipedInputStream, ?, ?> n) {
        if (!connected.contains(n) && (!(n instanceof ConnectibleReproducibleNeuron) || !((ConnectibleReproducibleNeuron<?>) n).connected.contains(this))) {
            out.connect(n.in);
            connected.add(n);
            System.out.println("Connect " + out.sinks.size());
        }
    }

    public void makeConnectionOfTwoOutbounds() {
        if (connected.size() >= 2) {
            ArrayList<AbstractNeuron<MyPipedInputStream, ?, ?>> neurons = new ArrayList<>(connected);
            int randomInt = RandomUtils.getRandomInt(neurons.size());
            AbstractNeuron<MyPipedInputStream, ?, ?> n1 = neurons.get(randomInt);
            AbstractNeuron<MyPipedInputStream, ?, ?> n2 = neurons.get((randomInt + RandomUtils.getRandomInt(neurons.size() - 1) + 1) % neurons.size());
            if (n1 instanceof ConnectibleReproducibleNeuron) {
                ((ConnectibleReproducibleNeuron<?>) n1).connect(n2);
            }
        }
    }

    @Override
    public synchronized void pipeline() {
        try {
            if (in.available() > decoder.nBytesToRead) {
                byte[] signal = in.readNBytes(decoder.nBytesToRead);
                Double newSignal = decoder.transform(signal) * growth;
                expand(newSignal);
                System.out.println(signal + " " + newSignal);
                out.write(encoder.transform(newSignal));
            }
        } catch (IOException e) {
        }
    }

    private void expand(double newSignal) {
        if ((connected.size() < 2 || newSignal > 1) && RandomUtils.getRandomInt(Config.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
            reproduce();
        }
        if (connected.size() > 2 && RandomUtils.getRandomInt(Config.CONNECTION_PROBABILITY_1_OUT_OF) < 1) {
            makeConnectionOfTwoOutbounds();
        }
        if (Math.abs(newSignal) >= growth * connected.size()) {
            growth = Math.min(growth * INCREASE, INCREASE_MAX);
        } else {
            growth = Math.max(growth * DECREASE, DECREASE_MIN);
        }
    }
}
