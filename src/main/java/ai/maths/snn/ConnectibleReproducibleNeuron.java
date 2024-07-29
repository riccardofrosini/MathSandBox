package ai.maths.snn;

import java.io.InputStream;

public abstract class ConnectibleReproducibleNeuron<I extends InputStream> extends AbstractNeuron<I, MyPipedOutputStream> {


    public ConnectibleReproducibleNeuron(I in, MyPipedOutputStream out) {
        super(in, out);
    }

    public void reproduce() {
        MyPipedInputStream pipedInputStream = new MyPipedInputStream();
        getOut().connect(pipedInputStream);
        Neuron neuron = new Neuron(pipedInputStream, new MyPipedOutputStream());
        System.out.println("Reproduce " + getOut().sinks.size());
        new Thread(neuron).start();
    }

    public void connect(AbstractNeuron<MyPipedInputStream, ?> n) {
        getOut().connect(n.getIn());
        System.out.println("Connect " + getOut().sinks.size());
    }

}
