package sk.upjs.ics;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public final class Utils {

    public static boolean isNumeric(String str) {
        try {
            double d = Double.parseDouble(str);
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    public static Map<String, Integer> getWordFrequencies(Collection<String> tokens) {
        return tokens
            .parallelStream()
            .collect(Collectors
                .toConcurrentMap(w -> w, w -> 1, Integer::sum));
    }

    public static Map<String, Integer> getFrequenciesInSentences(Collection<String> sentences,
                                                                 Collection<String> targetStrings) {
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
