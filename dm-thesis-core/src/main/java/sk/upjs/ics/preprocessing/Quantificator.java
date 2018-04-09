package sk.upjs.ics.preprocessing;

import java.util.Map;
import java.util.Set;

public interface Quantificator<T> extends Processor<T, Map<String, Integer>>{
    Set<String> getLabels();
}
