package sk.upjs.ics.preprocessing.quantification;

import sk.upjs.ics.helpers.Range;
import sk.upjs.ics.helpers.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NumbersRangeGetter implements Quantificator<String> {

    private final String min = "MinNumber";
    private final String max = "MaxNumber";
    private final Set<String> labels = Utils.newHashSet(min, max);

    public static NumbersRangeGetter create() {
        return new NumbersRangeGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> getQuantificators(String string) {
        Map<String, Integer> result = new HashMap<>();
        Range range = new Range();

        for (int i = 0; i < string.length(); i++) {
            char character = string.charAt(i);

            if (Character.isDigit(character) && !range.isInRange(character)) {
                range.update(character);
            }
        }

        result.put(min, range.getMin());
        result.put(max, range.getMax());

        return result;
    }

}
