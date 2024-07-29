package ai.maths.snn;

import static ai.maths.snn.Config.FRAME_BYTE_SIZE;
import static ai.maths.snn.Utils.BYTES_TO_LONG;

import ai.maths.snn.Neuron.InputNeuron;
import ai.maths.snn.Utils.Decoder;
import ai.maths.snn.Utils.Encoder;

public class AI {

    public static void main(String[] args) throws Exception {
        AuditoryInput auditoryInput = new AuditoryInput();
        AuditoryOutput auditoryOutput = new AuditoryOutput();

        InputNeuron nonConnectibleNeuron = new InputNeuron(auditoryInput.start(), auditoryOutput.start(),
                new Decoder<>(FRAME_BYTE_SIZE, bytes -> {
                    Long value = BYTES_TO_LONG.transform.apply(bytes);
                    return (double) value / (1 << Config.SAMPLE_BIT_SIZE);
                }),
                new Encoder<>(FRAME_BYTE_SIZE, aDouble -> {
                    long value = ((long) (double) aDouble) >> (Long.BYTES * 8 - Config.SAMPLE_BIT_SIZE);
                    byte[] bytes = new byte[FRAME_BYTE_SIZE];
                    for (int i = 0; i < FRAME_BYTE_SIZE; i++) {
                        bytes[FRAME_BYTE_SIZE - 1 - i] = (byte) ((value >> (i * 8)) & 0xFF);
                    }
                    return bytes;
                }));

    }


}
