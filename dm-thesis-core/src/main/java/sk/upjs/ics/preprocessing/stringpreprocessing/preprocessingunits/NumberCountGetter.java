package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class NumberCountGetter extends StringPreprocessingUnit {

    private final String mapKey = "NumberCount";

    public static NumberCountGetter create() {
        return new NumberCountGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        result.put(mapKey, 0);

        for (int i = 0; i < string.length(); i++) {
            if (Character.isDigit(string.charAt(i))) {
                int count = result.get(mapKey);
                result.put(mapKey, ++count);
            }
        }

        return result;
    }
}
