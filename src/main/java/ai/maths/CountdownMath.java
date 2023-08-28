package ai.maths;

import java.util.HashSet;
import java.util.Stack;
import java.util.stream.Collectors;

public class CountDownMath {

    public static void main(String[] args) {
        System.out.println(closest(572, 7, 5, 10, 9, 2, 6));
        System.out.println(solutions.stream().map(strings -> strings.toString() + "\n").collect(Collectors.toList()));
        System.out.println(solutions.size());
    }

    private static Stack<String> operations = new Stack<>();
    private static HashSet<Stack<String>> solutions = new HashSet<>();
    private static long max = Long.MAX_VALUE;

    private static long closest(long total, long... values) {
        if (values[0] == total) {
            if (max != 0) {
                solutions.clear();
            }
            max = 0;
            solutions.add((Stack<String>) operations.clone());
            return 0;
        }
        if (values.length == 1) {
            long abs = Math.abs(values[0] - total);
            if (max > abs) {
                max = abs;
                solutions.clear();
            }
            if (max == abs) {
                solutions.add((Stack<String>) operations.clone());
            }
            return abs;
        }
        long closest = Long.MAX_VALUE;
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < i; j++) {

                long[] newValues = new long[values.length - 1];
                int newValuesIndex = 1;
                for (int k = 0; k < values.length; k++) {
                    if (k != i && k != j) {
                        newValues[newValuesIndex] = values[k];
                        newValuesIndex++;
                    }
                }
                newValues[0] = values[i] + values[j];
                operations.push(values[i] + "+" + values[j]);
                long temp = Math.min(closest(total, newValues), closest);
                operations.pop();
                if (temp != closest) {
                    closest = temp;
                }
                newValues[0] = values[i] * values[j];
                operations.push(values[i] + "*" + values[j]);
                temp = Math.min(closest(total, newValues), closest);
                operations.pop();
                if (temp != closest) {
                    closest = temp;
                }
                if (values[i] - values[j] >= 0) {
                    newValues[0] = values[i] - values[j];
                    operations.push(values[i] + "-" + values[j]);
                    temp = Math.min(closest(total, newValues), closest);
                    operations.pop();
                    if (temp != closest) {
                        closest = temp;
                    }
                }
                if (values[j] != 0 && values[i] % values[j] == 0) {
                    newValues[0] = values[i] / values[j];
                    operations.push(values[i] + "/" + values[j]);
                    temp = Math.min(closest(total, newValues), closest);
                    operations.pop();
                    if (temp != closest) {
                        closest = temp;
                    }
                }

                if (values[j] - values[i] >= 0) {
                    newValues[0] = values[j] - values[i];
                    operations.push(values[j] + "-" + values[i]);
                    temp = Math.min(closest(total, newValues), closest);
                    operations.pop();
                    if (temp != closest) {
                        closest = temp;
                    }
                }
                if (values[i] != 0 && values[j] % values[i] == 0) {
                    newValues[0] = values[j] / values[i];
                    operations.push(values[j] + "/" + values[i]);
                    temp = Math.min(closest(total, newValues), closest);
                    operations.pop();
                    if (temp != closest) {
                        closest = temp;
                    }
                }
            }
        }
        return closest;
    }

}
