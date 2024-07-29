package ai.maths.snn;

import javax.sound.sampled.AudioFormat;


public class Config {

    public static float INCREASE = 1.1f;
    public static float INCREASE_MAX = 2f;
    public static float DECREASE = 0.9f;
    public static float DECREASE_MIN = 0.01f;
    public static int REPRODUCTION_PROBABILITY_1_OUT_OF = 1000;
    public static int CONNECTION_PROBABILITY_1_OUT_OF = 1000;
    public static float SAMPLE_RATE = 24000;
    public static int SAMPLE_BIT_SIZE = 16;
    public static int CHANNELS = 1;
    public static boolean SIGNED = true;
    public static boolean BIGENDIAN = true;
    public static AudioFormat AUDIO_FORMAT = new AudioFormat(SAMPLE_RATE, SAMPLE_BIT_SIZE,
            CHANNELS, SIGNED, BIGENDIAN);
    public static int FRAME_BYTE_SIZE = AUDIO_FORMAT.getFrameSize();

}
