package sk.upjs.ics.task;

import sk.upjs.ics.helpers.InstancesUtils;
import sk.upjs.ics.helpers.QuantificatorUtils;
import sk.upjs.ics.preprocessing.quantification.Quantificator;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.Collection;

public class WekaDataRepresentativeExtractor implements DataRepresentativeExtractor {

    private Instances input;
    private Collection<Quantificator<String>> stringQuantificators;

    private WekaDataRepresentativeExtractor(Instances input, Collection<Quantificator<String>> stringQuantificators) {
        this.input = input;
        this.stringQuantificators = stringQuantificators;
    }

    public static WekaDataRepresentativeExtractor create(Instances input, Collection<Quantificator<String>> stringQuantificators) {
        return new WekaDataRepresentativeExtractor(input, stringQuantificators);
    }

    @Override
    public void extractDataRepresentative() {
        SimpleKMeans kmeans = new SimpleKMeans();

        try {
            kmeans.buildClusterer(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(kmeans.getClusterCentroids());
        // TODO
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

        // Replace missing values with zeros
        this.input = InstancesUtils.replaceMissingWithZero(resultInstances);
    }
}
