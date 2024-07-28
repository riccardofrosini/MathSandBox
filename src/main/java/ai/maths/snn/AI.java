package ai.maths.snn;

import ai.maths.snn.Neuron.NonConnectibleNeuron;

public class AI {

    public static void main(String[] args) throws Exception {
        AuditoryInput auditoryInput = new AuditoryInput();
        AuditoryOutput auditoryOutput = new AuditoryOutput();

        NonConnectibleNeuron nonConnectibleNeuron = new NonConnectibleNeuron(auditoryInput.start(), auditoryOutput.start());
        new Thread(nonConnectibleNeuron).start();

    }


}
