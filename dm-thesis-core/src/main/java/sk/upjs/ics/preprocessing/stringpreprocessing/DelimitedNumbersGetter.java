package sk.upjs.ics.preprocessing.stringpreprocessing;

import sk.upjs.ics.helpers.Utils;
import sk.upjs.ics.preprocessing.Quantificator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DelimitedNumbersGetter implements Quantificator<String> {

    private final String delimiters = "[/\r\n\t.,;:'\"()?!]";
    private final Set<String> labels = new HashSet<>();

    public static DelimitedNumbersGetter create() {
        return new DelimitedNumbersGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> process(String string) {
        Map<String, Integer> result = new HashMap<>();
        String[] delimitedStrings = string.split(delimiters);

        for (int i = 0; i < delimitedStrings.length; i++) {
            if (Utils.isNumeric(delimitedStrings[i])) {
                String label = i + ". Number";
                result.put(label, Integer.parseInt(delimitedStrings[i]));
                labels.add(label);
            }
        }

        return result;
    }
}
