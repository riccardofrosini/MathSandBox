package ai.maths.snn;

import java.nio.ByteBuffer;

import javax.sound.sampled.AudioFormat;

public class ConfigAndUtils {

    public static float INCREASE = 1.1f;
    public static float DECREASE = 0.9f;
    public static int REPRODUCTION_PROBABILITY_1_OUT_OF = 10000;
    public static int CONNECTION_PROBABILITY_1_OUT_OF = 100;
    public static float SAMPLE_RATE = 24000;
    public static int SAMPLE_BIT_SIZE = 16;
    public static int CHANNELS = 2;
    public static boolean SIGNED = true;
    public static boolean BIGENDIAN = true;
    public static AudioFormat AUDIO_FORMAT = new AudioFormat(SAMPLE_RATE, SAMPLE_BIT_SIZE,
            CHANNELS, SIGNED, BIGENDIAN);


    public static byte[] float2ByteArray(float value) {
        return ByteBuffer.allocate(4).putFloat(value).array();
    }

    public static float byteArrayToFloat(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getFloat();
    }
}
