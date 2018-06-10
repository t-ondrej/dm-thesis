package sk.upjs.ics.preprocessing.nlp;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public interface StopwordHandler {
    HashSet<String> getStopwords();

    default boolean isStopword(String word) {
        return getStopwords().contains(word);
    }

    default List<String> removeStopwords(List<String> document) {
        return document.stream()
            .filter(this::isStopword)
            .collect(Collectors.toList());
    }
}
