package sk.upjs.ics.task;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessor;
import weka.classifiers.trees.J48;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Weka implementation of string boundaries task
 */
public class GetStringBoundariesTask implements WekaTask {

	private StringPreprocessor stringPreprocessor;

	public GetStringBoundariesTask(StringPreprocessor stringPreprocessor) {
		this.stringPreprocessor = stringPreprocessor;
	}

	public static GetStringBoundariesTask create(StringPreprocessor stringPreprocessor) {
		return new GetStringBoundariesTask(stringPreprocessor);
	}

	@Override
	public Instances execute(Instances inputDataSet) {
		List<Map<String, Integer>> results = new ArrayList<>(inputDataSet.numInstances());

		// Iterate through every instance
		for (int i = 0; i < inputDataSet.numInstances(); i++) {
			Instance ins = inputDataSet.get(i);
			// Preprocess every instance value(if it's string value)
			for (int j = 0; j < ins.numAttributes(); j++) {
				Attribute attr = ins.attribute(j);
				if (attr.isString()) {
					int valueIdx = (int) ins.value(j);
					String s = attr.value(valueIdx);
					stringPreprocessor.preprocess(s);
					Map<String, Integer> result = stringPreprocessor.getResult();
					results.add(result);
				}
			}
		}

		// Get all possible attributes from preprocessing results
		Set<String> header = new HashSet<>();
		results.forEach(result -> header.addAll(result.keySet()));
		ArrayList<Attribute> attributes = header.stream()
				.map(Attribute::new)
				.collect(Collectors.toCollection(ArrayList::new));

		int classIndex = inputDataSet.classIndex();
		attributes.add(inputDataSet.attribute(classIndex));

		// Create new instances which will contain preprocessing results
		Instances resultInstances = new Instances("ResultInstances",
				attributes,
				results.size());
		resultInstances.setClassIndex(resultInstances.numAttributes() - 1);

		// Fill instances with preprocessing results
		IntStream.range(0, results.size())
			.forEach(idx -> {
				Map<String, Integer> result = results.get(idx);
				Instance instance = new DenseInstance(header.size() + 1); // +1 is the class attribute
				result.forEach((key, value) -> instance.setValue(resultInstances.attribute(key), value));
				instance.setValue(resultInstances.classIndex(), inputDataSet.get(idx).value(classIndex));
				resultInstances.add(instance);
			});

		// Replace missing values with zeros
		Instances newInstances = replaceMissingWithZero(resultInstances);
		System.out.println(newInstances);
		// Create J48 classifier and build it upon the instances of preprocessing results
		J48 classifier = new J48();
		try {
			classifier.buildClassifier(newInstances);
			System.out.println(classifier.toSource("TestClassifier"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultInstances;
	}

	private Instances replaceMissingWithZero(Instances instances) {
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