package sk.upjs.ics.preprocessing.quantification;

import sk.upjs.ics.helpers.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class WordCountGetter implements Quantificator<String> {

    private final String label = "WordCount";
    private final Set<String> labels = Utils.newHashSet(label);

    public static WordCountGetter create() {
        return new WordCountGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> getQuantificators(String string) {
        Map<String, Integer> result = new HashMap<>();
        result.put(label, 0);

        String[] strings = string.split(" ");
        result.put(label, strings.length);

        return result;
    }
}
