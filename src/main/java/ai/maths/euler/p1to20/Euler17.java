package ai.maths.euler.p1to20;

import java.util.HashMap;

public class Euler17 {

    private final static HashMap<Integer, NumString> map;

    static {
        map = new HashMap<>(29);
        map.put(1, NumString.one);
        map.put(2, NumString.two);
        map.put(3, NumString.three);
        map.put(4, NumString.four);
        map.put(5, NumString.five);
        map.put(6, NumString.six);
        map.put(7, NumString.seven);
        map.put(8, NumString.eight);
        map.put(9, NumString.nine);
        map.put(10, NumString.ten);
        map.put(11, NumString.eleven);
        map.put(12, NumString.twelve);
        map.put(13, NumString.thirteen);
        map.put(14, NumString.fourteen);
        map.put(15, NumString.fifteen);
        map.put(16, NumString.sixteen);
        map.put(17, NumString.seventeen);
        map.put(18, NumString.eighteen);
        map.put(19, NumString.nineteen);
        map.put(20, NumString.twenty);
        map.put(30, NumString.thirty);
        map.put(40, NumString.forty);
        map.put(50, NumString.fifty);
        map.put(60, NumString.sixty);
        map.put(70, NumString.seventy);
        map.put(80, NumString.eighty);
        map.put(90, NumString.ninety);
        map.put(100, NumString.hundred);
        map.put(1000, NumString.thousand);
    }

    public static void main(String[] args) {
        int numberOfCharacters = 0;
        for (int i = 1; i <= 1000; i++) {
            int number = i;
            if (number >= 1000) {
                int hundreds = number % 1000;
                int thousands = (number - hundreds) / 1000;
                numberOfCharacters += map.get(thousands).getStringSize() + NumString.thousand.getStringSize();
                number = hundreds;
            }
            if (number >= 100) {
                int tens = number % 100;
                int hundreds = (number - tens) / 100;
                numberOfCharacters += map.get(hundreds).getStringSize() + NumString.hundred.getStringSize();
                number = tens;

            }

            if (number > 0) {
                if (i > 100) {
                    numberOfCharacters += NumString.and.getStringSize();
                }
                if (number <= 20) {
                    numberOfCharacters += map.get(number).getStringSize();
                } else {
                    int ones = number % 10;
                    int tens = number - ones;
                    numberOfCharacters += map.get(tens).getStringSize();
                    number = ones;
                    if (number > 0) {
                        numberOfCharacters += map.get(number).getStringSize();
                    }
                }
            }


        }
        System.out.println(numberOfCharacters);
    }

    public enum NumString {
        one,
        two,
        three,
        four,
        five,
        six,
        seven,
        eight,
        nine,
        ten,
        eleven,
        twelve,
        thirteen,
        fourteen,
        fifteen,
        sixteen,
        seventeen,
        eighteen,
        nineteen,
        twenty,
        thirty,
        forty,
        fifty,
        sixty,
        seventy,
        eighty,
        ninety,
        hundred,
        thousand,
        and;

        private final int stringSize;

        NumString() {
            stringSize = name().length();
        }

        int getStringSize() {
            return stringSize;
        }
    }
}

