package ai.maths.snn;

import java.io.InputStream;
import java.io.OutputStream;

public class Neuron extends ConnectibleReproducibleNeuron<MyPipedInputStream> implements Runnable {

    protected float growth;

    public Neuron(MyPipedInputStream in, MyPipedOutputStream out) {
        super(in, out);
        this.growth = 1;
    }

    public void run() {
        //while (true) {
        //    try {
        //        if (in.available() > SAMPLE_BYTE_SIZE) {
        //            int intSignal = ConfigAndUtils.byteArrayToInt(in.readNBytes(SAMPLE_BYTE_SIZE));
        //            int newIntSignal = (int) (intSignal * growth);
        //            System.out.println(intSignal + " " + newIntSignal);
        //            if ((connected.size() < 2 || growth > 1) && RandomUtils.getRandomInt(ConfigAndUtils.REPRODUCTION_PROBABILITY_1_OUT_OF) < 1) {
        //                reproduce();
        //            }
        //            if (connected.size() > 2 && RandomUtils.getRandomInt(ConfigAndUtils.CONNECTION_PROBABILITY_1_OUT_OF) < 1) {
        //                ArrayList<Neuron<MyPipedInputStream>> neurons = new ArrayList<>(connected.keySet());
        //                int randomInt = RandomUtils.getRandomInt(neurons.size());
        //                Neuron<MyPipedInputStream> n1 = neurons.get(randomInt);
        //                Neuron<MyPipedInputStream> n2 = neurons.get((randomInt + RandomUtils.getRandomInt(neurons.size() - 1) + 1) % neurons.size());
        //                if (!n1.connected.containsKey(n2) && !n2.connected.containsKey(n1)) {
        //                    n1.connect(n2);
        //                }
        //            }
        //            if (Math.abs(newIntSignal) >= growth * connected.size()) {
        //                growth = Math.min(growth * ConfigAndUtils.INCREASE, 2);
        //            } else {
        //                growth = Math.max(growth * ConfigAndUtils.DECREASE, 0.01f);
        //            }
        //            for (OutputStream pipedOutputStream : connected.values()) {
        //                pipedOutputStream.write(ConfigAndUtils.intToByteArray(newIntSignal));
        //                pipedOutputStream.flush();
        //            }
        //        } else {
        //            Thread.sleep(10);
        //        }
        //    } catch (IOException | InterruptedException e) {
        //    }
        //}
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
