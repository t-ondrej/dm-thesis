package sk.upjs.ics.preprocessing.stringpreprocessing;

import sk.upjs.ics.helpers.Utils;
import sk.upjs.ics.preprocessing.Quantificator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NumberCountGetter implements Quantificator<String> {

    private final String label = "NumberCount";
    private final Set<String> labels = Utils.newHashSet(label);

    public static NumberCountGetter create() {
        return new NumberCountGetter();
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
            if (Character.isDigit(string.charAt(i))) {
                int count = result.get(label);
                result.put(label, ++count);
            }
        }

        return result;
    }
}
