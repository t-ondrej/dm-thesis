package sk.upjs.ics.preprocessing;

public interface Processor<T, S> {
    S process(T input);
}
