package ai.maths.snn;

import java.nio.ByteBuffer;
import java.util.function.Function;

public class Utils {

    public static final Decoder<Float> BYTES_TO_FLOAT = new Decoder<>(Float.BYTES, bytes -> ByteBuffer.wrap(bytes).getFloat());
    public static final Encoder<Float> FLOAT_TO_BYTES_ = new Encoder<>(Float.BYTES, value -> ByteBuffer.allocate(Float.BYTES).putFloat(value).array());
    public static final Decoder<Double> BYTES_TO_DOUBLE = new Decoder<>(Double.BYTES, bytes -> ByteBuffer.wrap(bytes).getDouble());
    public static final Encoder<Double> DOUBLE_TO_BYTES = new Encoder<>(Double.BYTES, value -> ByteBuffer.allocate(Double.BYTES).putDouble(value).array());
    public static final Decoder<Integer> BYTES_TO_INTEGER = new Decoder<>(Integer.BYTES, bytes -> ByteBuffer.wrap(bytes).getInt());
    public static final Encoder<Integer> INTEGER_TO_BYTES = new Encoder<>(Integer.BYTES, value -> ByteBuffer.allocate(Integer.BYTES).putInt(value).array());
    public static final Decoder<Long> BYTES_TO_LONG = new Decoder<>(Long.BYTES, bytes -> ByteBuffer.wrap(bytes).getLong());
    public static final Encoder<Long> LONG_TO_BYTES = new Encoder<>(Long.BYTES, value -> ByteBuffer.allocate(Long.BYTES).putLong(value).array());
    public static final Decoder<Byte> BYTES_TO_BYTE = new Decoder<>(Byte.BYTES, bytes -> bytes[0]);
    public static final Encoder<Byte> BYTE_TO_BYTES = new Encoder<>(Byte.BYTES, value -> new byte[]{value});

    protected abstract static class Transformer<I, O> {

        private Function<I, O> transform;

        Transformer(Function<I, O> transform) {
            this.transform = transform;
        }

        public O transform(I i) {
            return transform.apply(i);
        }
    }

    public static class Decoder<O> extends Transformer<byte[], O> {

        private int nBytesToRead;

        Decoder(int nBytesToRead, Function<byte[], O> transform) {
            super(transform);
            this.nBytesToRead = nBytesToRead;
        }

        public int getnBytesToRead() {
            return nBytesToRead;
        }
    }

    public static class Encoder<I> extends Transformer<I, byte[]> {

        private int nBytesToAllocate;

        Encoder(int nBytesToAllocate, Function<I, byte[]> transform) {
            super(transform);
            this.nBytesToAllocate = nBytesToAllocate;
        }

        public int getnBytesToAllocate() {
            return nBytesToAllocate;
        }
    }
}
