package sk.upjs.ics.preprocessing;

/**
 * Represents single unit which is called during preprocessing
 * @param <T> Input parameter type
 * @param <S> Return parameter type
 */
public interface PreprocessingUnit<T, S> {
    S preprocess(T t);
}
