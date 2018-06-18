package sk.upjs.ics.autocomplete;

import weka.core.Trie;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.reverseOrder;

/**
 * Markov implementation of autocompleter
 */
public class MarkovAutocompleter implements Autocompleter {

    private LanguageModel languageModel;
    private Trie          trie;
    private int           maxSuggestions = 5;
    private int           maxSuggestedWordsInSentence = 2;

    public static MarkovAutocompleter create(LanguageModel languageModel) {
        MarkovAutocompleter markovAutocompleter = new MarkovAutocompleter(languageModel);
        markovAutocompleter.initTrie(languageModel.getInputSentences());

        return markovAutocompleter;
    }

    private MarkovAutocompleter(LanguageModel languageModel) {
        this.languageModel = languageModel;
    }

    private void initTrie(Collection<String> sentences) {
        trie = new Trie();

        Collection<String> trimmedAndLowercaseSentences = sentences.stream()
            .map(sentence -> sentence.trim().toLowerCase())
            .collect(Collectors.toList());

        trie.addAll(trimmedAndLowercaseSentences);
    }

    @Override
    public void setLanguageModel(LanguageModel languageModel) {
        this.languageModel = languageModel;
        initTrie(languageModel.getInputSentences());
    }

    @Override
    public void setMaxSuggestions(int maxSuggestions) {
        if (maxSuggestions < 1)
            return;

        this.maxSuggestions = maxSuggestions;
    }

    @Override
    public void setMaxSuggestedWordsInSentence(int maxSuggestedWordsInSentence) {
        this.maxSuggestedWordsInSentence = maxSuggestedWordsInSentence;
    }

    @Override
    public Collection<String> getSuggestions(String sentence) {
        Collection<String> candidates = getCandidates(sentence);

        // Map probabilities to gathered candidates
        Map<String, Double> sentenceToProbability = languageModel.getProbabilities(candidates).entrySet()
                .stream()
                .sorted(reverseOrder(Map.Entry.comparingByValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        // Return certain number of gathered candidates (might be sorted by its probability)
        return sentenceToProbability.entrySet()
                .stream()
                .limit(maxSuggestions)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<String> getCandidates(String inputSentence) {
        String[] inputTokens = inputSentence.split(" ");

        Vector<String> inputSentenceWithSuffixes = trie.getWithPrefix(inputSentence);
        List<String> suffixWords = new ArrayList<>();

        for (String sentence : inputSentenceWithSuffixes) {
            String[] tokens = sentence.split(" ");

            if (inputTokens.length == tokens.length) continue;

            StringBuilder sb = new StringBuilder();
            int min = Math.min(inputSentence.length() + maxSuggestedWordsInSentence, tokens.length - inputTokens.length);

            for (int i = 0; i < inputTokens.length + min; i++)
                sb.append(tokens[i]).append(" ");

            suffixWords.add(sb.toString());
        }

        return suffixWords;
    }

}