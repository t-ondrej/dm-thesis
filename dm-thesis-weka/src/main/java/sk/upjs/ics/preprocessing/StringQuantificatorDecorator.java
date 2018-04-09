package sk.upjs.ics.preprocessing;

import sk.upjs.ics.helpers.InstancesUtils;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.*;
import java.util.stream.Collectors;

public class StringQuantificatorDecorator implements Processor<Attribute, Instances> {

    private Instances input;
    private Collection<Quantificator<String>> quantificators;

    public StringQuantificatorDecorator(Instances input, Collection<Quantificator<String>> quantificators) {
        this.input = input;
        this.quantificators = quantificators;
    }

    @Override
    public Instances process(Attribute attribute) {
        List<Map<String, Integer>> results = new ArrayList<>();

        input.forEach(instance -> {
            String value = instance.stringValue(attribute);
            Map<String, Integer> result = new HashMap<>();
            quantificators.forEach(quantificator -> result.putAll(quantificator.process(value)));
            results.add(result);
        });

        Set<String> labels = quantificators.stream()
            .map(Quantificator::getLabels)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());

        return InstancesUtils.toInstances(results, labels, input.attribute(input.classIndex()));
    }
}
