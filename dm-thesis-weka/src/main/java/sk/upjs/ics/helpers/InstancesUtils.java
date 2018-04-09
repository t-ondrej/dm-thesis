package sk.upjs.ics.helpers;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class InstancesUtils {

    public static Instances toInstances(List<Map<String, Integer>> inputInstances) {
        String instancesName = "testInstance";
        ArrayList<Attribute> attributes = new ArrayList<>();

        inputInstances.get(0).forEach((colName, colValue) -> {
            Attribute attribute = new Attribute(colName);
            attributes.add(attribute);
        });

        Instances instances = new Instances(instancesName, attributes, inputInstances.size());

        inputInstances.forEach(inputInstance -> {
            Instance instance = new DenseInstance(inputInstance.size());

            int idx = 0;
            for (Map.Entry<String, Integer> entry : inputInstance.entrySet()) {
                instance.setValue(idx, entry.getValue());
                idx++;
            }

            instances.add(instance);
        });

        return instances;
    }

    public static void fillInstances(Instances instances, List<Map<String, Integer>> instanceMap) {
        // Fill instances with preprocessing results
        IntStream.range(0, instanceMap.size())
            .forEach(idx -> {
                Map<String, Integer> result = instanceMap.get(idx);
                Instance instance = new DenseInstance(instances.numAttributes()); // +1 is the class attribute
                result.forEach((key, value) -> instance.setValue(instances.attribute(key), value));
                instance.setValue(instances.classIndex(), instances.get(idx).value(instances.classIndex()));
                instances.add(instance);
            });
    }

    public static List<String> toList(Instances instances, Attribute stringAttribute) {
        if (stringAttribute == null)
            return new ArrayList<>();

        return instances.stream()
            .map(instance -> {
                int valueIdx = (int)instance.value(stringAttribute);
                return stringAttribute.value(valueIdx);
            })
            .collect(Collectors.toList());
    }

    public static Instances toInstances(
        List<Map<String, Integer>> instances,
        Set<String> attributeNames,
        Attribute classAttribute) {
        // Get all possible attributes from preprocessing results
        ArrayList<Attribute> attributes = attributeNames.stream()
            .map(Attribute::new)
            .collect(Collectors.toCollection(ArrayList::new));

        attributes.add(classAttribute);

        // Create new instances which will contain preprocessing results
        Instances resultInstances = new Instances("ResultInstances",
            attributes,
            instances.size());
        resultInstances.setClassIndex(resultInstances.numAttributes() - 1);

        InstancesUtils.fillInstances(resultInstances, instances);

        return resultInstances;
    }

    public static Instances replaceMissingWithZero(Instances instances) {
        try {
            ReplaceMissingWithUserConstant filter = new ReplaceMissingWithUserConstant();
            filter.setNumericReplacementValue("0");
            filter.setInputFormat(instances);
            return Filter.useFilter(instances, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
