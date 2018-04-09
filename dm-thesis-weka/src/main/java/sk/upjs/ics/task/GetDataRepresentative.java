package sk.upjs.ics.task;

import sk.upjs.ics.preprocessing.Processor;
import sk.upjs.ics.preprocessing.StringQuantificatorDecorator;
import sk.upjs.ics.helpers.InstancesUtils;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.Instances;

public class GetDataRepresentative extends AbstractTask<Instances> {

    private Instances input;
    private Processor<Attribute, Instances> stringQuantificatorDecorator;

    private GetDataRepresentative(Instances input, Processor<Attribute, Instances> stringQuantificatorDecorator) {
        this.input = input;
        this.stringQuantificatorDecorator = stringQuantificatorDecorator;
    }

    public static GetDataRepresentative create(Instances input, Processor<Attribute, Instances> stringQuantificatorDecorator) {
        return new GetDataRepresentative(input, stringQuantificatorDecorator);
    }

    @Override
    protected void preprocessInput() {
        Attribute stringAttribute = null;

        for (int i = 0; i < input.numAttributes(); i++) {
            if (!input.attribute(i).isString())
                continue;

            stringAttribute = input.attribute(i);
            break;
        }

        Instances resultInstances = stringQuantificatorDecorator.process(stringAttribute);

        // Replace missing values with zeros
        this.input = InstancesUtils.replaceMissingWithZero(resultInstances);
    }

    @Override
    public Instances coreExecute() {
        SimpleKMeans kmeans = new SimpleKMeans();

        try {
            kmeans.buildClusterer(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return kmeans.getClusterCentroids();
    }
}
