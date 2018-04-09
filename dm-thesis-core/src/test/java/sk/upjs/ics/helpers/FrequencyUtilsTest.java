package sk.upjs.ics.helpers;

import org.junit.Test;
import weka.core.SerializedObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FrequencyUtilsTest {

    @Test
    public void getWordFrequenciesTest() throws Exception {
       /* Object s = new SerializedObject("Hello", true);
        System.out.println(s.getClass());
        Collection<String> strings = Arrays.asList("3", "2", "1", "3", "3", "2");
        Map<String, Integer> stringToFrequency = Utils.getWordFrequencies(strings);

        assertEquals(3, stringToFrequency.size());
        assertEquals(Integer.valueOf(1), stringToFrequency.get("1"));
        assertEquals(Integer.valueOf(2), stringToFrequency.get("2"));
        assertEquals(Integer.valueOf(3), stringToFrequency.get("3"));*/
    }

    @Test
    public void getWordFrequenciesInSentencesTest() {
        /*Collection<String> sentences = Arrays.asList(
                "Hello world.",
                "This is my new car!",
                "What a sunny day!",
                "Hello, how are you?"
        );

        Collection<String> targetStrings = Arrays.asList(
                "Are", "Hello", "Test", "car"
        );

        Map<String, Integer> stringToFrequency = Utils.getFrequenciesInSentences(sentences, targetStrings);

        assertEquals(4, stringToFrequency.size());
        assertEquals(stringToFrequency.get("Are"), new Integer(1));
        assertEquals(stringToFrequency.get("Hello"), new Integer(2));
        assertEquals(stringToFrequency.get("car"), new Integer(1));
        assertEquals(stringToFrequency.get("Test"), new Integer(0));*/
    }
}