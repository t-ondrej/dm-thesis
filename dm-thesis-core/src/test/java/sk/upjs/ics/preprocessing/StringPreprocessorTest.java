package sk.upjs.ics.preprocessing;

import org.junit.Test;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.DelimitedNumbersGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.NumberCountGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits.PunctuationCountGetter;
import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;
import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StringPreprocessorTest {


    @Test
    public void test() {
        List<StringPreprocessingUnit> preprocessingUnits = new ArrayList<>();
        preprocessingUnits.add(new DelimitedNumbersGetter());
        preprocessingUnits.add(new NumberCountGetter());
        preprocessingUnits.add(new PunctuationCountGetter());

        List<String> strings = new ArrayList<>();
        strings.add("12.12.2016");
        strings.add("15.10.2005");
        strings.add("16/10/1990");
        strings.add("10/10/93");
        strings.add("22.1.2230");

        List<Map<String, Integer>> results = new ArrayList<>();
        StringPreprocessor preprocessor = new StringPreprocessor(preprocessingUnits);
        strings.forEach(preprocessor::preprocess);

        results.add(preprocessor.getResult());

        results.forEach(System.out::println);
    }
}