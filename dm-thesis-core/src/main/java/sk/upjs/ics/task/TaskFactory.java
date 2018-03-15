package sk.upjs.ics.task;

/**
 * Task factory so we can control which implementation of specific task is used
 */
public interface TaskFactory {
	<T,S> Task<T, S> createFilterOutliersTask(S input);

	<T,S> Task<T, S> createGetStringBoundariesTask(S input);

	<T,S> Task<T, S> createUnifyStringFormatTask(S input);

	<T,S> Task<T, S> createGetSuggestionTask(S input);
}
