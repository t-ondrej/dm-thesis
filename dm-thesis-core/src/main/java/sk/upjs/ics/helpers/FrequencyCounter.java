package sk.upjs.ics.helpers;

import java.util.Collection;
import java.util.Map;

public interface FrequencyCounter {
    Map<String, Integer> getWordFrequencies(Collection<String> tokens);
    Map<String, Integer> getWordFrequenciesInDocument(Collection<String> sentences,
                                                      Collection<String> targetStrings);
}
