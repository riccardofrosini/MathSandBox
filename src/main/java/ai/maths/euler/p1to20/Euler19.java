package ai.maths.euler.p1to20;

public class Euler19 {

    public static void main(String[] args) {
        int sundaysAtBeginningOfMonth = 0;
        int dayOfWeek = 0;
        for (int year = 1900; year < 2001; year++) {
            for (int month = 1; month <= 12; month++) {
                if (year > 1900 && dayOfWeek == 6) {
                    sundaysAtBeginningOfMonth++;
                }
                dayOfWeek = (dayOfWeek + daysOfMonth(year, month)) % 7;
            }
        }
        System.out.println(sundaysAtBeginningOfMonth);
    }


    private static int daysOfMonth(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        }
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        }
        if ((year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) {
            return 29;
        }
        return 28;
    }

}
