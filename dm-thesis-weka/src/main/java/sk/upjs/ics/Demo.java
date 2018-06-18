package sk.upjs.ics;

import sk.upjs.ics.autocomplete.Autocompleter;
import sk.upjs.ics.autocomplete.MarkovAutocompleter;
import sk.upjs.ics.autocomplete.NgramLanguageModel;
import sk.upjs.ics.helpers.WekaNGramGetter;
import sk.upjs.ics.preprocessing.quantification.Quantificator;
import sk.upjs.ics.preprocessing.quantification.DelimitedNumbersGetter;
import sk.upjs.ics.preprocessing.quantification.PunctuationCountGetter;
import sk.upjs.ics.preprocessing.quantification.PunctuationOccurencyGetter;
import sk.upjs.ics.task.*;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Demo {

    public static void main(String[] args) throws FileNotFoundException {
        autocomplete();
       // filterOutliers();
       // unifyStrings();
       // getStringBoundaries();
    }

    private static void autocomplete() {

        StringJoiner data = new StringJoiner(" ");

        try (Scanner sc = new Scanner(new File("./dm-thesis-weka/src/main/resources/autocomplete/autocomplete_data2.txt"))) {
            while (sc.hasNextLine())
                data.add(sc.nextLine());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        String joinedData = data.toString();
        String[] sentences = joinedData.split("\\.");

        NgramLanguageModel languageModel = NgramLanguageModel.create(
            Arrays.asList(sentences), WekaNGramGetter.create());

        Autocompleter autocompleter = MarkovAutocompleter.create(languageModel);
        autocompleter.getSuggestions("jeden")
            .forEach(System.out::println);

    }

    // Interval <1, 20>
    private static void filterOutliers() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("./dm-thesis-weka/src/main/resources/ages.txt"));
        List<Double> ages = new ArrayList<>();

        while (sc.hasNextLine()) {
            ages.add(Double.parseDouble(sc.nextLine()));
        }

        OutliersHandler outliersHandler1 = new IqrOutliersHandler();
        OutliersHandler outliersHandler2 = new MadOutliersHandler();

        List<Double> filteredIQR = outliersHandler1.filterOutliers(ages);
        List<Double> filteredMAD= outliersHandler2.filterOutliers(ages);

        /*
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(
                "./dm-thesis-weka/src/main/resources/anomalies_bigoutliers.arff");
            Instances input = source.getDataSet();

            //Task<List<? extends Number>> outliersTask = FilterOutliersTaskIQR.create();
            //Instances outliers = outliersTask.execute(input);

            //System.out.println(outliers);
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }

    private static void getStringBoundaries() {
       /* try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(
                "./dm-thesis-weka/src/main/resources/dates.arff");
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            Collection<Quantificator<String>> stringQuantificationUnits =
                Arrays.asList(DelimitedNumbersGetter.create(),
                              PunctuationCountGetter.create(),
                              PunctuationOccurencyGetter.create());

            StringQuantificatorDecorator stringPreprocessor = new StringQuantificatorDecorator(instances, stringQuantificationUnits);
            Task<Instances> task = GetStringConstraintsTask.create(instances, stringPreprocessor);
            task.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        ConverterUtils.DataSource source = null;
        try {
            source = new ConverterUtils.DataSource(
                "./dm-thesis-weka/src/main/resources/dates.arff");

            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            Collection<Quantificator<String>> stringQuantificationUnits =
                Arrays.asList(DelimitedNumbersGetter.create(),
                    PunctuationCountGetter.create(),
                    PunctuationOccurencyGetter.create());

            StringFormExtractor formExtractor = WekaStringFormExtractor.create(instances, stringQuantificationUnits);
            formExtractor.extractStringForm();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
