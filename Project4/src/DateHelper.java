// Author: Isaac Kim
// DateHelper has small methods for working with dates.
// All dates in ChocAn are Strings in the format MM-DD-YYYY.

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

    private static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");

    // Returns today's date, like "11-20-2026"
    public static String today() {
        return LocalDate.now().format(dateFormat);
    }

    // Returns the date from some number of days ago
    public static String daysAgo(int days) {
        return LocalDate.now().minusDays(days).format(dateFormat);
    }

    // Returns the current date and time, like "11-20-2026 14:30:05"
    public static String now() {
        return LocalDateTime.now().format(dateTimeFormat);
    }

    // Checks that a date is a real date in the format MM-DD-YYYY
    public static boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, dateFormat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Checks that a date is not after today
    public static boolean isNotInFuture(String date) {
        LocalDate theDate = LocalDate.parse(date, dateFormat);
        return !theDate.isAfter(LocalDate.now());
    }

    // Checks if a date is within the last 7 days (today counts)
    public static boolean isWithinLastWeek(String date) {
        if (!isValidDate(date)) {
            return false;
        }
        LocalDate theDate = LocalDate.parse(date, dateFormat);
        LocalDate weekAgo = LocalDate.now().minusDays(7);
        return theDate.isAfter(weekAgo) && !theDate.isAfter(LocalDate.now());
    }
}
