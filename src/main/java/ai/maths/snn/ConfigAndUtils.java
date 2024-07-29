package ai.maths.snn;

import javax.sound.sampled.AudioFormat;

public class ConfigAndUtils {

    public static float INCREASE = 1.1f;
    public static float DECREASE = 0.9f;
    public static int REPRODUCTION_PROBABILITY_1_OUT_OF = 1000;
    public static int CONNECTION_PROBABILITY_1_OUT_OF = 1000;
    public static float SAMPLE_RATE = 24000;
    public static int SAMPLE_BIT_SIZE = 8;
    public static int SAMPLE_BYTE_SIZE = 1;
    public static int CHANNELS = 1;
    public static boolean SIGNED = true;
    public static boolean BIGENDIAN = true;
    public static AudioFormat AUDIO_FORMAT = new AudioFormat(SAMPLE_RATE, SAMPLE_BIT_SIZE,
            CHANNELS, SIGNED, BIGENDIAN);


    public static byte[] intToByteArray(int value) {
        byte[] bytes = new byte[SAMPLE_BYTE_SIZE];
        for (int i = 0; i < SAMPLE_BYTE_SIZE; i++) {
            bytes[SAMPLE_BYTE_SIZE - i - 1] = (byte) (value >> (i * 8));
        }
        System.out.println("Bytes1 " + value + " " + bytes);
        return bytes;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int value = 0;
        for (int i = 0; i < bytes.length; i++) {
            value = (value << 8) | bytes[i];
        }
        System.out.println("Bytes2 " + value + " " + bytes);
        return value;
    }
}
