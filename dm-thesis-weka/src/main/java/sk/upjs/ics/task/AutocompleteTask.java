package sk.upjs.ics.task;

import sk.upjs.ics.autocomplete.Autocompleter;

import java.util.Collection;

public class AutocompleteTask extends AbstractTask<Collection<String>> {

    private Autocompleter autocompleter;
    private String phrase;

    private AutocompleteTask(Autocompleter autocompleter, String phrase) {
        this.autocompleter = autocompleter;
        this.phrase = phrase;
    }

    public static AutocompleteTask create(Autocompleter autocompleter, String phrase) {
        return new AutocompleteTask(autocompleter, phrase);
    }

    @Override
    protected void preprocessInput() {
        // Placeholder
    }

    @Override
    protected Collection<String> coreExecute() {
        return autocompleter.getSuggestions(phrase);
    }
}
