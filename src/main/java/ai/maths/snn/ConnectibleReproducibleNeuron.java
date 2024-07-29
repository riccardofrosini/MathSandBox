package ai.maths.snn;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import ai.maths.neat.utils.RandomUtils;

public abstract class ConnectibleReproducibleNeuron<I extends InputStream> extends AbstractNeuron<I, MyPipedOutputStream> {

    protected HashSet<AbstractNeuron<MyPipedInputStream, ?>> connected;

    public ConnectibleReproducibleNeuron(I in, MyPipedOutputStream out) {
        super(in, out);
        connected = new HashSet<>();
    }

    public void reproduce() {
        MyPipedInputStream pipedInputStream = new MyPipedInputStream();
        out.connect(pipedInputStream);
        Neuron neuron = new Neuron(pipedInputStream, new MyPipedOutputStream());
        connected.add(neuron);
        System.out.println("Reproduce " + out.sinks.size());
    }

    public void connect(AbstractNeuron<MyPipedInputStream, ?> n) {
        if (!connected.contains(n) && (!(n instanceof ConnectibleReproducibleNeuron) || !((ConnectibleReproducibleNeuron<?>) n).connected.contains(this))) {
            out.connect(n.in);
            connected.add(n);
            System.out.println("Connect " + out.sinks.size());
        }
    }

    public void makeConnectionOfTwoOutbounds() {
        ArrayList<AbstractNeuron<MyPipedInputStream, ?>> neurons = new ArrayList<>(connected);
        int randomInt = RandomUtils.getRandomInt(neurons.size());
        AbstractNeuron<MyPipedInputStream, ?> n1 = neurons.get(randomInt);
        AbstractNeuron<MyPipedInputStream, ?> n2 = neurons.get((randomInt + RandomUtils.getRandomInt(neurons.size() - 1) + 1) % neurons.size());
        if (n1 instanceof ConnectibleReproducibleNeuron) {
            ((ConnectibleReproducibleNeuron<?>) n1).connect(n2);
        }
    }

}
