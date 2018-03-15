package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class CharsRangeGetter extends StringPreprocessingUnit {

    private final String left = "MinChar";
    private final String right = "MaxChar";

    public static CharsRangeGetter create() {
        return new CharsRangeGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        result.put(left, 0);
        result.put(right, 0);

        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);

            if (Character.isLetter(character) && isInRange(character)) {
                updateRange(character);
            }
        }

        return result;
    }

    private boolean isInRange(char character) {
        int charValue = (int) character;

        return result.get(left) <= charValue && charValue <= result.get(right);
    }

    private void updateRange(char character) {
        int charValue = (int) character;

        int min = Math.min(result.get(left), charValue);
        int max = Math.max(result.get(right), charValue);

        result.put(left, min);
        result.put(right, max);
    }
}
