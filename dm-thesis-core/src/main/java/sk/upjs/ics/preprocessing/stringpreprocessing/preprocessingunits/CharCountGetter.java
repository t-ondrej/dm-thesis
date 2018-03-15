package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class CharCountGetter extends StringPreprocessingUnit {

    private final String mapKey = "CharCount";

    public static CharCountGetter create() {
        return new CharCountGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();

        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                int count = result.get(mapKey);
                result.put(mapKey, ++count);
            }
        }

        return result;
    }
}
