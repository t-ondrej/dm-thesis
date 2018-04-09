package sk.upjs.ics.task;

import sk.upjs.ics.preprocessing.Processor;
import sk.upjs.ics.helpers.InstancesUtils;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.Instances;

/**
 * Weka implementation of string boundaries task
 */
public class GetStringConstraintsTask extends AbstractTask<Instances> {

	private Instances input;
	private Processor<Attribute, Instances> stringQuantificatorDecorator;

	private GetStringConstraintsTask(Instances input, Processor<Attribute, Instances> stringQuantificatorDecorator) {
		this.stringQuantificatorDecorator = stringQuantificatorDecorator;
		this.input = input;
	}

	public static GetStringConstraintsTask create(Instances input, Processor<Attribute, Instances> stringQuantificatorDecorator) {
		return new GetStringConstraintsTask(input, stringQuantificatorDecorator);
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
	protected Instances coreExecute() {
		// Create J48 classifier and build it upon the instances of preprocessing results
		J48 classifier = new J48();

		try {
			classifier.buildClassifier(input);
			System.out.println(classifier.toSource("TestClassifier"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return input;
	}

}