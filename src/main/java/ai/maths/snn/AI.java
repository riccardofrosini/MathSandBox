package ai.maths.snn;

import static ai.maths.snn.Config.FRAME_BYTE_SIZE;
import static ai.maths.snn.Config.SAMPLE_BIT_SIZE;

import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;
import ai.maths.snn.neuralmodel.Neuron.InputNeuron;

public class AI {

    public static void main(String[] args) throws Exception {
        AuditoryInput auditoryInput = new AuditoryInput();
        AuditoryOutput auditoryOutput = new AuditoryOutput();

        InputNeuron inputNeuron = new InputNeuron(auditoryInput.start(), auditoryOutput.start(),
                new Decoder<>(FRAME_BYTE_SIZE, bytes -> {
                    long value = 0L;
                    for (int i = 0; i < FRAME_BYTE_SIZE; i++) {
                        value = (value << Byte.SIZE) | bytes[i];
                    }
                    System.out.println("In: " + value);
                    return (double) (value << (Long.SIZE - SAMPLE_BIT_SIZE)) / (1L << (Long.SIZE - 1));
                }),
                new Encoder<>(FRAME_BYTE_SIZE, aDouble -> {
                    long value = (long) (aDouble * (1L << SAMPLE_BIT_SIZE));
                    System.out.println("Out " + value);
                    byte[] bytes = new byte[FRAME_BYTE_SIZE];
                    for (int i = 0; i < FRAME_BYTE_SIZE; i++) {
                        bytes[FRAME_BYTE_SIZE - 1 - i] = (byte) ((value >> (i * Byte.SIZE)) & 0xFF);
                    }
                    return bytes;
                }));
        inputNeuron.pipeline();
    }


}
