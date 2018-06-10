package sk.upjs.ics.task;

import sk.upjs.ics.helpers.ClassifierTreeUtils;
import sk.upjs.ics.helpers.InstancesUtils;
import sk.upjs.ics.helpers.QuantificatorUtils;
import sk.upjs.ics.preprocessing.quantification.Quantificator;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

import java.util.Collection;
import java.util.List;

// TODO: rework, dont insert instances but just Map<String, String> where key is instance, value is class
public class WekaStringFormExtractor implements StringFormExtractor {

    private Instances input;
    private Collection<Quantificator<String>> stringQuantificators;
    private J48 classifier;

    public static WekaStringFormExtractor create(Instances input, Collection<Quantificator<String>> stringQuantificators) {
        WekaStringFormExtractor stringFormExtractor = new WekaStringFormExtractor(input, stringQuantificators);
        stringFormExtractor.preprocess();
        stringFormExtractor.buildClassifier();

        return stringFormExtractor;
    }

    public WekaStringFormExtractor(Instances input, Collection<Quantificator<String>> stringQuantificators) {
        this.input = input;
        this.stringQuantificators = stringQuantificators;
    }

    @Override
    public List<String> extractStringForm() {
        // TODO: string = feature, int = quantificator
        ClassifierTreeUtils.string(classifier);
        return null;
    }

    private void preprocess() {
        Attribute stringAttribute = null;

        for (int i = 0; i < input.numAttributes(); i++) {
            if (!input.attribute(i).isString())
                continue;

            stringAttribute = input.attribute(i);
            break;
        }

        Instances resultInstances = QuantificatorUtils.getQuantificators(input, stringAttribute, stringQuantificators);
        for (int i = 0; i < resultInstances.numInstances(); i++) {
            Instance instance = resultInstances.get(i);
            instance.setValue(resultInstances.classIndex(), input.get(i).value(input.classIndex()));
        }

        // Replace missing values with zeros
        this.input = InstancesUtils.replaceMissingWithZero(resultInstances);
    }

    private void buildClassifier() {
        this.classifier = new J48();

        try {
            classifier.buildClassifier(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
