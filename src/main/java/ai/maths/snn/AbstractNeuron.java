package ai.maths.snn;

import java.io.InputStream;
import java.io.OutputStream;

public class AbstractNeuron<I extends InputStream, O extends OutputStream> implements InterfaceNeuron<I, O> {

    I in;
    O out;

    public AbstractNeuron(I in, O out) {
        this.in = in;
        this.out = out;
    }

    public I getIn() {
        return in;
    }

    public O getOut() {
        return out;
    }
}
