package Services;

import java.util.Random;

public class IDGenerator {
    public static String generateGUID() {
        StringBuilder guid = new StringBuilder();
        for (int i = 0; i <= 12; i++) {
            if (i <= 4) {
                guid.append(randomChar());
            } else if (i >= 5 && i <= 10) {
                guid.append(randomNumber());
            } else {
                guid.append(randomChar());
            }
        }

        return guid.toString();
    }

    private static char randomChar() {
        Random r = new Random();
        return (char)(r.nextInt('Z' - 'A') + 'a');
    }

    private static char randomNumber() {
        Random r = new Random();
        return (char)(r.nextInt('9' - '0') + '0');
    }
}