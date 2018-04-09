package sk.upjs.ics.spellchecker;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultWordEditor implements WordEditor {

    private String[] letters = new String[] {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k"
        , "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    private Map<String, String> splits = new HashMap<>();

    @Override
    public Collection<String> getAllEdits(String word) {
        int insertionsCount = (word.length() + 1) * letters.length;
        int deletionsCount = word.length();
        int transposeCount = word.length();
        int replacesCount = word.length() * letters.length;

        Map<String, String> splits = splits(word);
        Collection<String> results = new ArrayList<>(insertionsCount + deletionsCount + transposeCount + replacesCount);

        results.addAll(insertions(splits));
        results.addAll(deletes(splits));
        results.addAll(transposes(splits));
        results.addAll(replaces(splits));

        return results;
    }

    @Override
    public Map<String, String> splits(String word) {
        return IntStream.range(0, word.length() + 1)
            .mapToObj(idx -> new AbstractMap.SimpleEntry<>(word.substring(0, idx),
                                                           word.substring(idx, word.length())))
            .collect(Collectors.toMap(Map.Entry::getKey,
                                      Map.Entry::getValue));
    }

    @Override
    public Collection<String> insertions(Map<String, String> splits) {
        return Arrays.stream(letters)
            .map(letter -> insert(splits, letter))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<String> deletes(Map<String, String> splits) {
        return splits.entrySet().stream()
            .filter(entry -> entry.getValue().length() > 0)
            .map(entry -> entry.getKey() + entry.getValue().substring(1))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<String> transposes(Map<String, String> splits) {
        return splits.entrySet().stream()
            .filter(entry -> entry.getValue().length() > 1)
            .map(entry -> entry.getKey() + entry.getValue().charAt(1) + entry.getValue().charAt(0)
                    + entry.getValue().substring(2))
            .collect(Collectors.toList());
    }

    @Override
    public Collection<String> replaces(Map<String, String> splits) {
        Map<String, String> splitss = splits.entrySet().stream()
            .filter(entry -> entry.getValue().length() > 0)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return Arrays.stream(letters)
            .map(letter -> replace(splitss, letter))
            .flatMap(Collection::stream)
            .sorted()
            .collect(Collectors.toList());
    }

    private Collection<String> replace(Map<String, String> splits, String letter) {
        return splits.entrySet().stream()
            .map(entry -> entry.getKey() + letter + entry.getValue().substring(1))
            .collect(Collectors.toList());
    }

    private Collection<String> insert(Map<String, String> splits, String letter) {
        return splits.entrySet().stream()
            .map(entry -> entry.getKey() + letter + entry.getValue())
            .collect(Collectors.toList());
    }
}
