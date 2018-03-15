package sk.upjs.ics.autocomplete;

/**
 * Functional interface to compute language model
 * @param <T>
 */
public interface LanguageModelComputer<T extends LanguageModel> {
    T getLanguageModel();
}
