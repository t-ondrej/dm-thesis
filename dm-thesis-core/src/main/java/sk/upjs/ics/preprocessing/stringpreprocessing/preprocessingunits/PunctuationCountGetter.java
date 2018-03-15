package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunctuationCountGetter extends StringPreprocessingUnit {

    private final String mapKey = "PunctuationCount";
    private final Pattern puncPattern = Pattern.compile("\\p{Punct}");

    public static PunctuationCountGetter create() {
        return new PunctuationCountGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        result.put(mapKey, 0);

        for (int i = 0; i < string.length(); i++) {
            Matcher puncMatcher = puncPattern.matcher(string.substring(i, i + 1));

            if (puncMatcher.matches()) {
                int count = result.get(mapKey);
                result.put(mapKey, ++count);
            }
        }

        return result;
    }
}
