package sk.upjs.ics.tokenizers;

import java.util.Collection;

/**
 * Functional interface for getting ngrams
 */
public interface NGramGetter {
    Collection<String> getNGrams(String sentence, int ngramSize);
}
