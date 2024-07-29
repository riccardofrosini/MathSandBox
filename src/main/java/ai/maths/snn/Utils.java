package ai.maths.snn;

import java.nio.ByteBuffer;
import java.util.function.Function;

public class Utils {

    protected static final Decoder<Float> BYTES_TO_FLOAT = new Decoder<>(Float.BYTES, bytes -> ByteBuffer.wrap(bytes).getFloat());
    protected static final Encoder<Float> FLOAT_TO_BYTES_ = new Encoder<>(Float.BYTES, value -> ByteBuffer.allocate(Float.BYTES).putFloat(value).array());
    protected static final Decoder<Double> BYTES_TO_DOUBLE = new Decoder<>(Double.BYTES, bytes -> ByteBuffer.wrap(bytes).getDouble());
    protected static final Encoder<Double> DOUBLE_TO_BYTES = new Encoder<>(Double.BYTES, value -> ByteBuffer.allocate(Double.BYTES).putDouble(value).array());
    protected static final Decoder<Integer> BYTES_TO_INTEGER = new Decoder<>(Integer.BYTES, bytes -> ByteBuffer.wrap(bytes).getInt());
    protected static final Encoder<Integer> INTEGER_TO_BYTES = new Encoder<>(Integer.BYTES, value -> ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
    protected static final Decoder<Long> BYTES_TO_LONG = new Decoder<>(Long.BYTES, bytes -> ByteBuffer.wrap(bytes).getLong());
    protected static final Encoder<Long> LONG_TO_BYTES = new Encoder<>(Long.BYTES, value -> ByteBuffer.allocate(Long.BYTES).putLong(value).array());
    protected static final Decoder<Byte> BYTES_TO_BYTE = new Decoder<>(Byte.BYTES, bytes -> bytes[0]);
    protected static final Encoder<Byte> BYTE_TO_BYTES = new Encoder<>(Byte.BYTES, value -> new byte[]{value});

    protected abstract static class Transformer<I, O> {

        Function<I, O> transform;

        Transformer(Function<I, O> transform) {
            this.transform = transform;
        }

        public O transform(I i) {
            return transform.apply(i);
        }
    }

    protected static class Decoder<O> extends Transformer<byte[], O> {

        int nBytesToRead;

        Decoder(int nBytesToRead, Function<byte[], O> transform) {
            super(transform);
            this.nBytesToRead = nBytesToRead;
        }
    }

    protected static class Encoder<I> extends Transformer<I, byte[]> {

        int nBytesToAllocate;

        Encoder(int nBytesToAllocate, Function<I, byte[]> transform) {
            super(transform);
            this.nBytesToAllocate = nBytesToAllocate;
        }
    }
}
