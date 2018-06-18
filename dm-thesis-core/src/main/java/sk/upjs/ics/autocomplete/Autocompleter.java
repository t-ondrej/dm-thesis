package sk.upjs.ics.autocomplete;

import java.util.Collection;

/**
 * Giving suggestions that contain given sentence
 */
public interface Autocompleter {
    Collection<String> getSuggestions(String sentence);

    Collection<String> getCandidates(String sentence);

    /**
     * Sets number of suggestions to be returned
     * @param maxSuggestions number of suggestions to be returned
     */
    void setMaxSuggestions(int maxSuggestions);

    void setLanguageModel(LanguageModel languageModel);

    void setMaxSuggestedWordsInSentence(int maxSuggestedWordsInSentence);
}
