package sk.upjs.ics.preprocessing;

/**
 * Preprocessor upon specific type
 * @param <T> Input parameter type
 * @param <S> Return parameter type
 */
public interface Preprocessor<T, S> {
    void preprocess(T t);
    S getResult();
}
