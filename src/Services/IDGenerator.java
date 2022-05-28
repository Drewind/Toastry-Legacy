package Services;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class IDGenerator {
    /**
     * Creates a new UID.
     * @return String formatting a UID.
     */
    public static String generateGUID() {
        StringBuilder guid = new StringBuilder();
        
        for (int i = 0; i < 32; i++) {
            guid.append((useChar() ? randomChar() : randomNumber()));
            if (i == 7 || i == 11 || i == 15 || i == 19) {
                guid.append('-');
            }
        }

        return guid.toString();
    }

    /**
     * Verifies whether or not a string is indeed a GUID.
     * @param str
     * @return
     */
    public static boolean isValidGUID(String str) {
        long dashCount = str.chars().filter(c -> c == '-').count();
        if (str != null && str.length() == 36 && dashCount == 4) {
            return true;
        }
        return false;
    }

    private static boolean useChar() {
        return (ThreadLocalRandom.current().nextInt(0, 2) == 0 ? false : true);
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