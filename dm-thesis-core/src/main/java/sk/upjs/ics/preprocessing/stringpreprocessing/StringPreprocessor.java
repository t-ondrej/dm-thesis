package sk.upjs.ics.preprocessing.stringpreprocessing;

import sk.upjs.ics.preprocessing.Preprocessor;

import java.util.*;

/**
 * String preprocessor
 */
// Perhaps add abstraction, make this class abstract and add something like StringQuantifier which inherits from this
public class StringPreprocessor implements Preprocessor<String, Map<String, Integer>> {

    private Collection<StringPreprocessingUnit> preprocessingUnits;
    private Map<String, Integer> result;

    public StringPreprocessor(Collection<StringPreprocessingUnit> preprocessingUnits) {
        this.preprocessingUnits = preprocessingUnits;
        result = new HashMap<>();
    }

    public void setProcessingUnits(Collection<StringPreprocessingUnit> processingUnits) {
        this.preprocessingUnits = processingUnits;
    }

    @Override public void preprocess(String string) {
        Map<String, Integer> map = new HashMap<>();

        // Call every preprocessing unit upon given string
        preprocessingUnits.forEach(processingUnit ->
            map.putAll(processingUnit.preprocess(string))
        );

        result = map;
    }

    @Override public Map<String, Integer> getResult() {
        return result;
    }
}
