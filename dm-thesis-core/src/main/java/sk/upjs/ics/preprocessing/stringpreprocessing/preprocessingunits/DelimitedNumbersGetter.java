package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.Utils;
import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class DelimitedNumbersGetter extends StringPreprocessingUnit {

    private final String delimiters = "[/\r\n\t.,;:'\"()?!]";

    public static DelimitedNumbersGetter create() {
        return new DelimitedNumbersGetter();
    }

    @Override public Map<String, Integer> preprocess(String string) {
        result = new HashMap<>();
        String[] delimitedStrings = string.split(delimiters);

        for (int i = 0; i < delimitedStrings.length; i++) {
            if (Utils.isNumeric(delimitedStrings[i])) {
                result.put(i + ". Number", Integer.parseInt(delimitedStrings[i]));
            }
        }

        return result;
    }
}
