package sk.upjs.ics.autocomplete.markovautocompleter;

import sk.upjs.ics.helpers.FrequencyCounter;
import sk.upjs.ics.autocomplete.LanguageModelComputer;
import sk.upjs.ics.helpers.NGramGetter;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MarkovLanguageModelComputer implements LanguageModelComputer<MarkovLanguageModel> {

    private final NGramGetter ngramGetter;
    private final Collection<String> sentences;
    private final FrequencyCounter frequencyCounter;
    private int ngramSizeMin = 1;
    private int ngramSizeMax = 6;

    public MarkovLanguageModelComputer(Collection<String> sentences, NGramGetter ngramGetter, FrequencyCounter frequencyCounter) {
        this.sentences = sentences;
        this.ngramGetter = ngramGetter;
        this.frequencyCounter = frequencyCounter;
    }

    public void setNgramSizeMin(int ngramSizeMin) {
        if (ngramSizeMin > ngramSizeMax)
            return;

        this.ngramSizeMin = ngramSizeMin;
    }

    public void setNgramSizeMax(int ngramSizeMax) {
        if (ngramSizeMax < ngramSizeMin)
            return;

        this.ngramSizeMax = ngramSizeMax;
    }

    @Override
    public MarkovLanguageModel getLanguageModel() {
        Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency = new HashMap<>();

        IntStream.rangeClosed(ngramSizeMin, ngramSizeMax)
            .forEach(ngramSize -> {
                List<Map.Entry<String, Integer>> test = sentences.stream()
                    .map(sentence ->
                        new ArrayList<>(frequencyCounter.getWordFrequencies(ngramGetter.getNGrams(sentence, ngramSize)).entrySet()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

                ngramsizeToNGramToFrequency.put(ngramSize, test.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum)));
            });

        return new MarkovLanguageModel(ngramsizeToNGramToFrequency);
    }
}
