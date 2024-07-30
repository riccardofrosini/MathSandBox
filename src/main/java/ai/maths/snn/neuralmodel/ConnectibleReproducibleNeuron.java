package ai.maths.snn.neuralmodel;

import static ai.maths.snn.Config.DECREASE;
import static ai.maths.snn.Config.DECREASE_MIN;
import static ai.maths.snn.Config.INCREASE;
import static ai.maths.snn.Config.INCREASE_MAX;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ai.maths.neat.utils.RandomUtils;
import ai.maths.snn.Config;
import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;

public abstract class ConnectibleReproducibleNeuron<I extends InputStream> extends AbstractNeuron<I, MyPipedOutputStream, Double> {

    protected Set<AbstractNeuron<MyPipedInputStream, ?, ?>> connected;
    protected double growth;

    public ConnectibleReproducibleNeuron(I in, MyPipedOutputStream out, Decoder<Double> decoder, Encoder<Double> encoder) {
        super(in, out, decoder, encoder);
        connected = Collections.synchronizedSet(new HashSet<>());
        growth = 1;
    }

    public void reproduceDisconnected() {
        MyPipedInputStream pipedInputStream = new MyPipedInputStream();
        out.connect(pipedInputStream);
        Neuron neuron = new Neuron(pipedInputStream, new MyPipedOutputStream());
        connected.add(neuron);
        neuron.pipeline();
        System.out.println("Reproduce " + out.sinks.size());
    }

    public void reproduceConnected() {
        ArrayList<AbstractNeuron<MyPipedInputStream, ?, ?>> neurons = new ArrayList<>(connected);
        int randomInt = RandomUtils.getRandomInt(neurons.size());
        AbstractNeuron<MyPipedInputStream, ?, ?> n = neurons.get(randomInt);

        Neuron neuron = new Neuron(new MyPipedInputStream(), new MyPipedOutputStream());
        connect(neuron);
        neuron.connect(n);
        disconnect(n);
        neuron.pipeline();
        System.out.println("Reproduce " + out.sinks.size());
    }

    private void disconnect(AbstractNeuron<MyPipedInputStream, ?, ?> n) {
        connected.remove(n);
        out.disconnect(n.in);
    }

    public void connect(AbstractNeuron<MyPipedInputStream, ?, ?> n) {
        if (!connected.contains(n) && (!(n instanceof ConnectibleReproducibleNeuron) || !((ConnectibleReproducibleNeuron<?>) n).connected.contains(this))) {
            out.connect(n.in);
            connected.add(n);
            System.out.println("Connect " + out.sinks.size());
        }
    }

    private void makeConnectionOfTwoOutbounds() {
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
        new Thread(() -> {
            while (true) {
                try {
                    if (in.available() > decoder.getnBytesToRead()) {
                        Double signal = decoder.transform(in.readNBytes(decoder.getnBytesToRead()));
                        double newSignal = signal * growth;
                        expand(newSignal);
                        out.write(encoder.transform(newSignal));
                        out.flush();
                    } else {
                        Thread.sleep(50);
                    }
                } catch (IOException | InterruptedException e) {
                }
            }
        }).start();
    }

    private void expand(double newSignal) {
        if ((connected.size() < 2 || newSignal > 1) && RandomUtils.getRandomInt(Config.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
            reproduceDisconnected();
        }
        if (connected.size() >= 2 && newSignal > 1 && RandomUtils.getRandomInt(Config.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
            reproduceConnected();
        }
        if (connected.size() > 2 && RandomUtils.getRandomInt(Config.CONNECTION_PROBABILITY_1_OUT_OF) < 1) {
            makeConnectionOfTwoOutbounds();
        }
        if (Math.abs(newSignal) <= growth * connected.size()) {
            growth = Math.min(growth * INCREASE, INCREASE_MAX);
        } else {
            growth = Math.max(growth * DECREASE, DECREASE_MIN);
        }
    }
}
