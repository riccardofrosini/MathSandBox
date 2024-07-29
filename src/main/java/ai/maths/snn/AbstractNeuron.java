package ai.maths.snn;

import java.io.InputStream;
import java.io.OutputStream;

import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;

public abstract class AbstractNeuron<I extends InputStream, O extends OutputStream, P extends Number> {

    protected I in;
    protected O out;
    protected Decoder<P> decoder;
    protected Encoder<P> encoder;

    public AbstractNeuron(I in, O out, Decoder<P> decoder, Encoder<P> encoder) {
        this.in = in;
        this.out = out;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    public abstract void pipeline();

}
