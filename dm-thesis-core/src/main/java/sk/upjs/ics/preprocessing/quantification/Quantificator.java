package sk.upjs.ics.preprocessing.quantification;

import java.util.Map;
import java.util.Set;

public interface Quantificator<T>  { // extends Processor<T, Map<String, Integer>>
    Set<String> getLabels();
    Map<String, Integer> getQuantificators(T t);
}
