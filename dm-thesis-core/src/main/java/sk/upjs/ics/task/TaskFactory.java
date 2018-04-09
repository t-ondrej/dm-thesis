package sk.upjs.ics.task;

/**
 * Task factory so we can control which implementation of specific task is used
 */
public interface TaskFactory {
	Task<?> createFilterOutliersTask();

	Task<?> createGetStringConstraintsTask();

	Task<?> createAutocompleteTask();

	Task<?> createGetDataRepresentativeTask();

	Task<?> createSpellCheckerTask();
}
