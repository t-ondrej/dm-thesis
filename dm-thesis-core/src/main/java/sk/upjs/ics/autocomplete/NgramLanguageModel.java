package sk.upjs.ics.autocomplete;

import sk.upjs.ics.helpers.NGramGetter;
import sk.upjs.ics.helpers.Utils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NgramLanguageModel extends LanguageModel {

    private Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency;
    private Map<Integer, Integer>              ngramsizeToTotalFrequency;
    private int                                ngramOrderMin = 1;
    private int                                ngramOrderMax = 3;

    public static NgramLanguageModel create(Collection<String> sentences, NGramGetter ngramGetter) {
        NgramLanguageModel languageModel = new NgramLanguageModel(sentences);
        languageModel.init(ngramGetter);

        return languageModel;
    }

    private NgramLanguageModel(Collection<String> sentences) {
        super(sentences);
        // Placeholder
    }

    private void init(NGramGetter ngramGetter) {
        Map<Integer, Map<String, Integer>> ngramsizeToNGramToFrequency = new HashMap<>();

        // init ngramsizeToNGramToFrequency
        IntStream.rangeClosed(ngramOrderMin, ngramOrderMax)
            .forEach(ngramSize -> {
                List<Map.Entry<String, Integer>> test = this.inputSentences.stream()
                    .map(sentence ->
                        new ArrayList<>(Utils.getWordFrequencies(ngramGetter.getNGrams(sentence, ngramSize)).entrySet()))
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());

                ngramsizeToNGramToFrequency.put(ngramSize, test.stream()
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::sum)));
            });

        // init ngramsizeToTotalFrequency
        ngramsizeToTotalFrequency = ngramsizeToNGramToFrequency.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> entry.getValue().values().stream()
                    .mapToInt(value -> value)
                    .sum()));

        this.ngramsizeToNGramToFrequency = ngramsizeToNGramToFrequency;
    }

    @Override
    public Map<String, Double> getProbabilities(Collection<String> sentences) {
        Map<String, Double> sentenceToProbability = new HashMap<>(sentences.size());

        // Iterate through sentences
        sentences.forEach(sentence -> {
            // If sentence is invalid set its probability to -100
            if (sentence == null || sentence.isEmpty()) {
                sentenceToProbability.put(sentence, -100D);
                return;
            }

            // Get tokens from sentence
            String[] tokens = sentence.split(" ");
            AtomicReference<Double> totalProb = new AtomicReference<>((double) 0);

            // Iterate through tokens
            for (int n = 1; n < tokens.length + 1; n++) {
                String[] strings;
                // Number of tokens doesnt exceed maximum ngram order
                if (n <= ngramOrderMax)
                    strings = Arrays.copyOfRange(tokens, 0, n);
                else
                    strings = Arrays.copyOfRange(tokens, n - ngramOrderMax, n);

                // Length of selected ngram
                int ngramLengthToUse = strings.length;
                // Join all the words to one ngram
                String token = String.join(" ", Arrays.asList(strings));

                double tokenProb;

                if (ngramsizeToNGramToFrequency.containsKey(ngramLengthToUse)) {
                    // Total number of ngrams of selected length
                    float den = ngramsizeToTotalFrequency.get(ngramLengthToUse);
                    // Frequency of selected ngram
                    float num = ngramsizeToNGramToFrequency.get(ngramLengthToUse).entrySet()
                        .stream()
                        .filter(entry -> entry.getKey().toLowerCase().equals(token.toLowerCase()))
                        .mapToInt(Map.Entry::getValue)
                        .sum();

                    tokenProb = Math.log10(num / den);
                } else {
                    tokenProb = 1;
                }

                totalProb.updateAndGet(v -> v + tokenProb);
            }

            sentenceToProbability.put(sentence, totalProb.get());
        });

        return sentenceToProbability;
    }
}
