package sk.upjs.ics;

import sk.upjs.ics.autocomplete.Autocompleter;
import sk.upjs.ics.autocomplete.LanguageModel;
import sk.upjs.ics.autocomplete.LanguageModelComputer;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovAutocompleter;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovLanguageModel;
import sk.upjs.ics.autocomplete.markovautocompleter.MarkovLanguageModelComputer;
import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;
import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessor;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.DelimitedNumbersGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.PunctuationCountGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.PunctuationOccurencyGetter;
import sk.upjs.ics.task.FilterOutliersTaskIQR;
import sk.upjs.ics.task.GetStringBoundariesTask;
import sk.upjs.ics.task.UnifyStringFormatTask;
import sk.upjs.ics.task.WekaTask;
import sk.upjs.ics.tokenizers.WekaNGramGetter;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.StringJoiner;

public class Demo {

    public static void main(String[] args) {
       // autocomplete();
        filterOutliers();
       // unifyStrings();
       // getStringBoundaries();
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
            Arrays.asList(sentences), WekaNGramGetter.create());

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

            WekaTask outliersTask = FilterOutliersTaskIQR.create();
            Instances outliers = outliersTask.execute(input);

            System.out.println(outliers);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void unifyStrings() {
        ConverterUtils.DataSource source = null;
        Instances input = null;
        try {
            source = new ConverterUtils.DataSource(
                "./dm-thesis-weka/src/main/resources/strings.arff");
            input = source.getDataSet();
        } catch (Exception e) {
            e.printStackTrace();
        }


        WekaTask task = UnifyStringFormatTask.create();
        Instances output = task.execute(input);
        System.out.println(output);
    }

    private static void getStringBoundaries() {
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource(
                "./dm-thesis-weka/src/main/resources/dates.arff");
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            Collection<StringPreprocessingUnit> stringPreprocessingUnits =
                Arrays.asList(DelimitedNumbersGetter.create(),
                              PunctuationCountGetter.create(),
                              PunctuationOccurencyGetter.create());

            StringPreprocessor stringPreprocessor = new StringPreprocessor(stringPreprocessingUnits);
            WekaTask task = GetStringBoundariesTask.create(stringPreprocessor);
            task.execute(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
