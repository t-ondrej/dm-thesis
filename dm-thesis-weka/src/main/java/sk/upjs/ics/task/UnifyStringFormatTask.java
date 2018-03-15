package sk.upjs.ics.task;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessor;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Tomas on 12.2.2018.
 */
public class UnifyStringFormatTask implements WekaTask {

    // Perhaps pre preprocess -> delete strings wit h missing values

    public static UnifyStringFormatTask create() {
        return new UnifyStringFormatTask();
    }

    @Override
    public Instances execute(Instances inputDataSet) {
        Map<Integer, String> attridxToUnifiedstring = new HashMap<>();

        for (int idx = 0; idx < inputDataSet.numAttributes(); idx++) {
            Attribute attribute = inputDataSet.attribute(idx);
            if (attribute.isString()) {
                Map<String, Integer> stringToFrequency = getStringFrequencyMap(attribute);
                Integer frequency = stringToFrequency.values()
                        .stream()
                        .max(Integer::compareTo)
                        .get();
                String maxOccurredString = stringToFrequency.entrySet()
                        .stream()
                        .filter(entry -> Objects.equals(entry.getValue(), frequency))
                        .findFirst().get().getKey();

                attridxToUnifiedstring.put(idx, maxOccurredString);
            }
        }

        for (int i = 0; i < inputDataSet.numInstances(); i++)
            for (Map.Entry<Integer, String> entry : attridxToUnifiedstring.entrySet())
                inputDataSet.get(i).setValue(entry.getKey(), entry.getValue());

        return inputDataSet;
    }

    private boolean containsDifferentFormats(Attribute attribute) {
        if (!attribute.isString())
            return false;

        Map<String, Integer> tokenToOccurrences = new HashMap<>();

        for (int idx = 0; idx < attribute.numValues(); idx++) {
            if (!tokenToOccurrences.containsKey(attribute.value(idx))) {
                tokenToOccurrences.put(attribute.value(idx), 1);
            } else {
                return true;
            }
        }

        return false;
    }

    private Map<String, Integer> getStringFrequencyMap(Attribute attribute) {
        if (!attribute.isString())
            return new HashMap<>();

        Map<String, Integer> tokenToOccurrences = new HashMap<>();

        for (int idx = 0; idx < attribute.numValues(); idx++) {
            if (!tokenToOccurrences.containsKey(attribute.value(idx))) {
                tokenToOccurrences.put(attribute.value(idx), 1);
            } else {
                Integer occurrence = tokenToOccurrences.get(attribute.value(idx));
                tokenToOccurrences.put(attribute.value(idx), ++occurrence);
            }
        }

        return tokenToOccurrences;
    }
}
