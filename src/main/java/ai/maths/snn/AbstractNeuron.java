package ai.maths.snn;

import java.io.InputStream;
import java.io.OutputStream;

public class AbstractNeuron<I extends InputStream, O extends OutputStream> {

    protected I in;
    protected O out;

    public AbstractNeuron(I in, O out) {
        this.in = in;
        this.out = out;
    }
}
