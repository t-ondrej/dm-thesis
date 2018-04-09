package sk.upjs.ics.task;

import weka.core.Instances;

import java.util.Collection;

public class DefaultTaskFactory implements TaskFactory {

    @Override
    public Task<Instances> createFilterOutliersTask() {
        return FilterOutliersTaskIQR.create(null);
    }

    @Override
    public Task<Instances> createGetStringConstraintsTask() {
        return GetStringConstraintsTask.create(null, null);
    }

    @Override
    public Task<Collection<String>> createAutocompleteTask() {
        return AutocompleteTask.create(null, null);
    }

    @Override
    public Task<Instances> createGetDataRepresentativeTask() {
        return GetDataRepresentative.create(null, null);
    }

    @Override
    public Task<Collection<String>> createSpellCheckerTask() {
        return SpellCheckerTask.create(null, null);
    }
}
