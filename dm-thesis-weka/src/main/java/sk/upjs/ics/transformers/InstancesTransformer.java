package sk.upjs.ics.transformers;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class InstancesTransformer {

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
}
