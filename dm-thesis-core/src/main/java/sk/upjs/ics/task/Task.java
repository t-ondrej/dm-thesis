package sk.upjs.ics.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * Functional interface which represents task
 * @param <T> Output parameter type
 */
public interface Task<T> {
    T execute();

    default Future<T> executeAsync() {
        return CompletableFuture.supplyAsync(this::execute);
    }
}
