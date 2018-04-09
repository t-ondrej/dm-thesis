package sk.upjs.ics.helpers;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

public class FrequencyCounterImpl implements FrequencyCounter {

    @Override
    public Map<String, Integer> getWordFrequencies(Collection<String> tokens) {
        return tokens
            .parallelStream()
            .collect(Collectors
                .toConcurrentMap(w -> w, w -> 1, Integer::sum));
    }

    @Override
    public Map<String, Integer> getWordFrequenciesInDocument(Collection<String> sentences, Collection<String> targetStrings) {
        String joinedSentences = String.join("", sentences);
        int sourceSentenceLength = joinedSentences.length();

        return targetStrings.stream()
            .collect(Collectors
                .toMap(s -> s, s -> {
                    int sentenceLengthWithoutWord = joinedSentences.replaceAll("(?i)" + s, "").length();
                    return (sourceSentenceLength - sentenceLengthWithoutWord) / s.length();
                }));
    }
}
