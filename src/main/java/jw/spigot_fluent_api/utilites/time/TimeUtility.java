package jw.spigot_fluent_api.utilites.time;

import java.util.concurrent.TimeUnit;

public class TimeUtility {

    public static long format(String input) {
        if (input == null || input.isEmpty()) { return -1L; }
        long result = 0L;
        StringBuilder number = new StringBuilder();
        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;
                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }
        return result;
    }

    public static long parse(String input, Long duration) {
        switch (input) {
            case "y": { return duration / TimeUnit.DAYS.toMillis(365L); }
            case "M": { return duration / TimeUnit.DAYS.toMillis(30L); }
            case "w": { return duration / TimeUnit.DAYS.toMillis(7L); }
            case "d": { return duration / TimeUnit.DAYS.toMillis(1L); }
            case "h": { return duration / TimeUnit.HOURS.toMillis(1L); }
            case "m": { return duration / TimeUnit.MINUTES.toMillis(1L); }
            case "s": { return duration / TimeUnit.SECONDS.toMillis(1L); }
        }
        return -1L;
    }

    protected static long convert(int value, char unit) {
        switch (unit) {
            case 'y': { return value * TimeUnit.DAYS.toMillis(365L); }
            case 'M': { return value * TimeUnit.DAYS.toMillis(30L); }
            case 'w': { return value * TimeUnit.DAYS.toMillis(7L); }
            case 'd': { return value * TimeUnit.DAYS.toMillis(1L); }
            case 'h': { return value * TimeUnit.HOURS.toMillis(1L); }
            case 'm': { return value * TimeUnit.MINUTES.toMillis(1L); }
            case 's': { return value * TimeUnit.SECONDS.toMillis(1L); }
            default: { return -1L; }
        }
    }
}
