package sk.upjs.ics.tokenizers;

import weka.core.tokenizers.NGramTokenizer;
import weka.core.tokenizers.Tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Weka implementation of ngram getter
 */
public class WekaNGramGetter implements NGramGetter {

    private final NGramTokenizer ngramTokenizer;

    public static NGramGetter create() {
        return new WekaNGramGetter();
    }

    public WekaNGramGetter() {
        this.ngramTokenizer = new NGramTokenizer();
    }

    @Override
    public Collection<String> getNGrams(String sentence, int ngramSize) {
        final Collection<String> ngrams = new ArrayList<>();

        ngramTokenizer.setNGramMinSize(ngramSize);
        ngramTokenizer.setNGramMaxSize(ngramSize);

        // Tokenize sentence
        ngramTokenizer.tokenize(sentence);

        // Add results to list
        while (ngramTokenizer.hasMoreElements()) {
            ngrams.add(ngramTokenizer.nextElement());
        }

        return ngrams;
    }
}
