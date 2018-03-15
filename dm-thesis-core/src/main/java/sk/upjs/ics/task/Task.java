package sk.upjs.ics.task;

/**
 * Functional interface which represents task
 * @param <T> Input parameter type
 * @param <S> Return parameter type
 */
public interface Task<T, S> {
    S execute(T input);
}
