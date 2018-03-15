package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class WordCountGetter extends StringPreprocessingUnit {

    private final String mapKey = "WordCount";

    public static WordCountGetter create() {
        return new WordCountGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        result.put(mapKey, 0);

        String[] strings = string.split(" ");
        result.put(mapKey, strings.length);

        return result;
    }
}
