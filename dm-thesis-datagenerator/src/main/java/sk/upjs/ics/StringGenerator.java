package sk.upjs.ics;

import org.apache.commons.lang3.StringUtils;
import sk.upjs.ics.dataaccess.FileInstancesConverter;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.ProtectedProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringGenerator {

    private static String filePath = "strings.arff";
    private static String modelString = "Red Hat";
    private static int instanceCount = 100;
    private static int numAttributes = 1;
    private static Random random = new Random();

    public static void main(String[] args) {
        generateStringsWithMutation();
    }

    public static void generateStringsWithMutation() {
        Attribute attribute = new Attribute("String", (List<String>)null, (ProtectedProperties)null);
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(attribute);
        Instances instances = new Instances("StringGenerator", attributes, instanceCount);

        for (int i = 0; i < instanceCount; i++) {
            DenseInstance instance = new DenseInstance(numAttributes);
            String string = random.nextDouble() < 0.2
                ? mutateString(modelString)
                : modelString;

            instance.setDataset(instances);
            instance.setValue(0, string);
            instances.add(instance);
        }

        System.out.println(instances);
        new FileInstancesConverter(filePath).saveDataSet(instances);
    }

    private static String mutateString(String string) {
        double randomValue = random.nextInt(3);

        if (randomValue == 0)
            return StringUtils.capitalize(string);
        else if (randomValue == 1)
            return StringUtils.swapCase(string);
        else
            return StringUtils.lowerCase(string);
    }
}
