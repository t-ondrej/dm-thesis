package sk.upjs.ics.preprocessing.stringpreprocessing;

import sk.upjs.ics.helpers.Utils;
import sk.upjs.ics.preprocessing.Quantificator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PunctuationCountGetter implements Quantificator<String> {

    private final Pattern puncPattern = Pattern.compile("\\p{Punct}");
    private final String label = "PunctuationCount";
    private final Set<String> labels = Utils.newHashSet(label);

    public static PunctuationCountGetter create() {
        return new PunctuationCountGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> process(String string) {
        Map<String, Integer> result = new HashMap<>();
        result.put(label, 0);

        for (int i = 0; i < string.length(); i++) {
            Matcher puncMatcher = puncPattern.matcher(string.substring(i, i + 1));

            if (puncMatcher.matches()) {
                int count = result.get(label);
                result.put(label, ++count);
            }
        }

        return result;
    }
}
