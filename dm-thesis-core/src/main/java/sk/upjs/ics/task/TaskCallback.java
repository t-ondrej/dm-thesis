package sk.upjs.ics.task;

/**
 * Callback method which can be called at the end of task execution
 * @param <T> Input parameter type
 */
public interface TaskCallback<T> {
    void processResultDataSet(T t);
}
