package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class NumbersRangeGetter extends StringPreprocessingUnit {

    private final String left = "MinChar";
    private final String right = "MaxChar";

    public static NumbersRangeGetter create() {
        return new NumbersRangeGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        result.put(left, 0);
        result.put(right, 0);

        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);

            if (Character.isDigit(character) && isInRange(character)) {
                updateRange(character);
            }
        }

        return result;
    }

    private boolean isInRange(int number) {
        return result.get(left) <= number && number <= result.get(right);
    }

    private void updateRange(int number) {
        int min = Math.min(result.get(left), number);
        int max = Math.max(result.get(right), number);

        result.put(left, min);
        result.put(right, max);
    }

}
