package sk.upjs.ics;

import sk.upjs.ics.autocomplete.Autocompleter;
import sk.upjs.ics.autocomplete.LanguageModel;
import sk.upjs.ics.autocomplete.LanguageModelComputer;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovAutocompleter;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovLanguageModel;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovLanguageModelComputer;
import sk.upjs.ics.helpers.FrequencyCounterImpl;
import sk.upjs.ics.preprocessing.Quantificator;
import sk.upjs.ics.preprocessing.StringQuantificatorDecorator;
import sk.upjs.ics.preprocessing.stringpreprocessing.DelimitedNumbersGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.PunctuationCountGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.PunctuationOccurencyGetter;
import sk.upjs.ics.task.*;
import sk.upjs.ics.helpers.WekaNGramGetter;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.*;

public class Demo {

    public static void main(String[] args) {
       // autocomplete();
       // filterOutliers();
       // unifyStrings();
        getStringBoundaries();
    }

    private static void autocomplete() {
        StringJoiner data = new StringJoiner(" ");

        try (Scanner sc = new Scanner(new File("./dm-thesis-weka/src/main/resources/autocomplete_data.txt"))) {
            while (sc.hasNextLine())
                data.add(sc.nextLine());
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        String joinedData = data.toString();
        String[] sentences = joinedData.split("\\.");

        LanguageModelComputer languageModelComputer = new MarkovLanguageModelComputer(
            Arrays.asList(sentences), WekaNGramGetter.create(), new FrequencyCounterImpl());

        LanguageModel languageModel = languageModelComputer.getLanguageModel();
        Autocompleter autocompleter = new MarkovAutocompleter((MarkovLanguageModel) languageModel);
        autocompleter.getSuggestions("Vauquer")
            .forEach(System.out::println);
    }

    // Interval <1, 20>
    private static void filterOutliers() {
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

    }

    private static void getStringBoundaries() {
        try {
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
        }
    }
}
