package sk.upjs.ics.preprocessing.quantification;

import sk.upjs.ics.helpers.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CharCountGetter implements Quantificator<String> {

    private final String label = "CharCount";
    private final Set<String> labels = Utils.newHashSet(label);

    public static CharCountGetter create() {
        return new CharCountGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> getQuantificators(String string) {
        Map<String, Integer> result = new HashMap<>();

        for (int i = 0; i < string.length(); i++) {
            if (Character.isLetter(string.charAt(i))) {
                int count = result.get(label);
                result.put(label, ++count);
            }
        }

        return result;
    }
}
