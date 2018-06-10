package sk.upjs.ics.preprocessing.quantification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ByteArrayGetter implements Quantificator<String> {

    private final Set<String> labels = new HashSet<>();

    public static ByteArrayGetter create() {
        return new ByteArrayGetter();
    }

    @Override
    public Set<String> getLabels() {
        return labels;
    }

    @Override
    public Map<String, Integer> getQuantificators(String s) {
        Map<String, Integer> result = new HashMap<>();
        byte[] stringByteArray = s.getBytes();

        for (int i = 0; i < stringByteArray.length; i++) {
            String label = (i + 1) + ". Char";
            result.put(label, (int)stringByteArray[i]);
            labels.add(label);
        }

        return result;
    }
}
