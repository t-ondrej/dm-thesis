package sk.upjs.ics.helpers;

import sk.upjs.ics.preprocessing.quantification.Quantificator;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;
import java.util.stream.Collectors;

public final class QuantificatorUtils {

    private QuantificatorUtils() {
        // Placeholder
    }

    public static Instances getQuantificators(Instances input, Attribute attribute, Collection<Quantificator<String>> quantificators) {
        List<Map<String, Integer>> results = new ArrayList<>();

        input.forEach(instance -> {
            String value = instance.stringValue(attribute);
            Map<String, Integer> result = new HashMap<>();
            quantificators.forEach(quantificator -> result.putAll(quantificator.getQuantificators(value)));
            results.add(result);
        });

        Set<String> labels = quantificators.stream()
            .map(Quantificator::getLabels)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        return InstancesUtils.toInstances(results, labels, input.attribute(input.classIndex()).copy("class"));
    }
}
