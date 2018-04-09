package sk.upjs.ics.helpers;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class Utils {

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static Set<String> newHashSet(String... strings) {
        HashSet<String> set = new HashSet<>();

        Collections.addAll(set, strings);

        return set;
    }
}
