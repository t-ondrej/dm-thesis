package sk.upjs.ics.preprocessing.stringpreprocessing;

import sk.upjs.ics.preprocessing.PreprocessingUnit;

import java.util.Map;

public abstract class StringPreprocessingUnit implements PreprocessingUnit<String, Map<String, Integer>> {
    protected Map<String, Integer> result;
}
