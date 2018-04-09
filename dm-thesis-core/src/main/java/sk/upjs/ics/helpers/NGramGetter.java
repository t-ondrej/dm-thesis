package sk.upjs.ics.helpers;

import java.util.Collection;

/**
 * Functional interface for getting ngrams
 */
public interface NGramGetter {
    Collection<String> getNGrams(String sentence, int ngramSize);
}
