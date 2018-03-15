package sk.upjs.ics.autocomplete;

import java.util.Collection;

/**
 * Giving suggestions that contain given sentence
 */
public interface Autocompleter {
    /**
     * Means where should given sentence be located within suggestion
     */
    enum PartOfSentence {
        START, MID, END
    }

    Collection<String> getSuggestions(String sentence);

    /**
     * Sets number of suggestions to be returned
     * @param suggestionsCount number of suggestions to be returned
     */
    void setSuggestionsCount(int suggestionsCount);

    /**
     * Sets part of sentence
     * @param partOfSentence part of sentence where given sentence should be located
     */
    void setPartOfSentence(PartOfSentence partOfSentence);
}
