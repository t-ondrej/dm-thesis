package sk.upjs.ics.autocomplete.markovautocompleter;

import sk.upjs.ics.autocomplete.Autocompleter;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Markov implementation of autocompleter
 */
public class MarkovAutocompleter implements Autocompleter {

    private MarkovLanguageModel languageModel;
    private Autocompleter.PartOfSentence partOfSentence = PartOfSentence.START;
    private int suggestionNGramSize = 3;
    private int suggestionsCount = 5;

    public MarkovAutocompleter(MarkovLanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    public void setLanguageModel(MarkovLanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    public void setPartOfSentence(PartOfSentence partOfSentence) {
        if (partOfSentence == null)
            return;

        this.partOfSentence = partOfSentence;
    }

    public void setSuggestionNGramSize(int suggestionNGramSize) {
        if (suggestionNGramSize < 2)
            return;

        this.suggestionNGramSize = suggestionNGramSize;
    }

    public void setSuggestionsCount(int suggestionsCount) {
        if (suggestionsCount < 1)
            return;

        this.suggestionsCount = suggestionsCount;
    }

    @Override
    public Collection<String> getSuggestions(String sentence) {
        Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency = languageModel.getNgramsizeToNGramToFrequency();
        Collection<String> candidates;

        // Set that suggestion will be one word longer than given sentence
        suggestionNGramSize = sentence.split(" ").length + 1;

        Predicate<String> ngramPredicate;
        // Decide which predicate will be used due to selected enum
        switch (partOfSentence) {
            case START: ngramPredicate = (ngram) -> ngram.toLowerCase().startsWith(sentence.toLowerCase());
                        break;
            case MID: ngramPredicate = (ngram) -> ngram.toLowerCase().contains(sentence.toLowerCase());
                        break;
            case END: ngramPredicate = (ngram) -> ngram.toLowerCase().endsWith(sentence.toLowerCase());
                        break;
            default:
                    throw new IllegalArgumentException("Part of sentence where the phrase should be is out of possible values.");
        }

        // Get all candidates of selected length that contains given phrase in selected part of sentence
        candidates = ngramsizeToNGramToFrequency.get(suggestionNGramSize).keySet()
                .stream()
                .filter(ngramPredicate)
                .collect(Collectors.toCollection(ArrayList::new));

        // Map probabilities to gathered candidates
        Map<String, Double> sentenceToProbability = getProbabilities(candidates).entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        // Return certain number of gathered candidates (might be sorted by its probability)
        return sentenceToProbability.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .limit(suggestionsCount)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private Map<String, Double> getProbabilities(Collection<String> sentences) {
        Map<String, Double> sentenceToProbability = new HashMap<>(sentences.size());

        // Iterate through sentences
        sentences.forEach(sentence -> {
            // If sentence is invalid set its probability to -100
            if (sentence == null || sentence.isEmpty()) {
                sentenceToProbability.put(sentence, -100D);
                return;
            }

            // Get separated words from sentence
            String[] tokens = sentence.split(" ");
            AtomicReference<Double> totalProb = new AtomicReference<>((double) 0);

            // Iterate through separated words
            for (int n = 1; n < tokens.length + 1; n++) {
                String[] strings;
                if (n <= suggestionNGramSize)
                    strings = Arrays.copyOfRange(tokens, 0, n);
                else
                    strings = Arrays.copyOfRange(tokens, n - suggestionNGramSize, n);

                // Length of selected ngram
                int ngramLengthToUse = strings.length;
                // Join all the words to one ngram
                String token = String.join(" ", Arrays.asList(strings));

                double tokenProb;

                if (languageModel.getNgramsizeToNGramToFrequency().containsKey(ngramLengthToUse)) {
                    // Total number of ngrams of selected length
                    float den = languageModel.getTotalNGramFrequencies(ngramLengthToUse);
                    // Frequency of selected ngram
                    float num = languageModel.getNgramsizeToNGramToFrequency().get(ngramLengthToUse).entrySet()
                            .stream()
                            .filter(entry -> entry.getKey().toLowerCase().equals(token.toLowerCase()))
                            .mapToInt(Map.Entry::getValue)
                            .sum();
                    tokenProb = Math.log10(num / den);
                } else {
                    tokenProb = 0;
                }

                totalProb.updateAndGet(v -> v + tokenProb);
            }

            sentenceToProbability.put(sentence, totalProb.get());
        });

        return sentenceToProbability;
    }
}