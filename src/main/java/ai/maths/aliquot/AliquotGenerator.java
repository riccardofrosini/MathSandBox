package ai.maths.aliquot;


import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


public class AliquotGenerator {


    public static void main(String[] args) {
        BigInteger val = BigInteger.valueOf(138);
        LinkedHashSet<BigInteger> prev = new LinkedHashSet<>();
        while (!prev.contains(val)) {
            prev.add(val);
            System.out.println(val);
            val = calculateAliquot(val);
        }
        plotGraph(new ArrayList<>(prev));
    }

    public static BigInteger calculateAliquot(BigInteger val) {
        BigInteger ret = BigInteger.ONE;
        BigInteger sqrt = val.sqrt();
        for (BigInteger i = BigInteger.TWO; i.compareTo(sqrt) <= 0; i = i.add(BigInteger.ONE)) {
            BigInteger[] bigIntegerDivRem = val.divideAndRemainder(i);
            if (bigIntegerDivRem[1].equals(BigInteger.ZERO)) {
                ret = ret.add(i).add(bigIntegerDivRem[0]);
            }
        }
        return ret;
    }

    private static void plotGraph(List<BigInteger> aliquotSteps) {
        final XYSeriesDemo demo = new XYSeriesDemo();

        for (int i = 0; i < aliquotSteps.size(); i++) {
            demo.addDot(i, aliquotSteps.get(i));
        }
        demo.postProcess();
        demo.pack();
        demo.setVisible(true);
    }
}
