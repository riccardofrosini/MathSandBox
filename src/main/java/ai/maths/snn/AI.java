package ai.maths.snn;

import ai.maths.snn.Neuron.InputNeuron;

public class AI {

    public static void main(String[] args) throws Exception {
        AuditoryInput auditoryInput = new AuditoryInput();
        AuditoryOutput auditoryOutput = new AuditoryOutput();

        InputNeuron nonConnectibleNeuron = new InputNeuron(auditoryInput.start(), auditoryOutput.start());

    }


}
