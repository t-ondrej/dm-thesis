package sk.upjs.ics;

import org.apache.commons.collections4.trie.PatriciaTrie;
import weka.core.Trie;

import java.io.*;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    // autocorrect
    // typicky zastupca
    // textove tagy

    // OK: autocomplete, integritne obmedzenie, filtering

    // https://stackoverflow.com/questions/29487186/named-entity-recognition-using-weka

    public static void main(String[] args) throws IOException {
        String data = /*"I WAS born in the year 1632, in the city of York, of a good family, " +
                "though not of that country, my father being a foreigner of Bremen, " +
                "who settled first at Hull. He got a good estate by merchandise, " +
                "and leaving off his trade, lived afterwards at York, " +
                "from whence he had married my mother, whose relations were named Robinson, " +
                "a very good family in that country, and from whom I was called Robinson Kreutznaer " +
                "but, by the usual corruption of words in England, we are now called - nay we call " +
                "ourselves and write our name - Crusoe; and so my companions always called me. " +*/
            "I had two elder brothers, one of whom was lieutenant-colonel to an English " +
                "regiment of foot in Flanders, formerly commanded by the famous Colonel Lockhart, " +
                "and was killed at the battle near Dunkirk against the Spaniards. What became of my " +
                "second brother I never knew, any more than my father or mother knew what became of me.";

        // String[] sentences = data.split("\\.");

        //  try (Scanner sc = new Scanner(new File("./dm-thesis-weka/src/main/resources/data_large1.txt"))) {
        //      data = sc.nextLine();
        //  } catch (Exception exc) {
        //      exc.printStackTrace();
        //  }
/*
        data = "1 2 3 4 5 6 7 8 9.";

        String[] sentences = data.split("\\.");

        LanguageModelComputer languageModelComputer = new LanguageModelComputerImpl(
                Arrays.asList(sentences), WekaNGramGetter.create());

        NgramLanguageModel languageModel = languageModelComputer.getLanguageModel();
        Autocompleter autocompleter = new MarkovAutocompleter((NgramLanguageModel)languageModel);
        autocompleter.getSuggestions("1")
            .forEach(System.out::println);

*/
/*
        try {
            ConverterUtils.DataSource source = new ConverterUtils.DataSource("./dm-thesis-weka/src/main/resources/dates.arff");
            Instances instances = source.getDataSet();
            instances.setClassIndex(instances.numAttributes() - 1);
            Collection<StringPreprocessingUnit> stringPreprocessingUnits = Arrays.asList(new DelimitedNumbersGetter(), new PunctuationCountGetter(), new PunctuationOccurencyGetter());

            StringPreprocessor stringPreprocessor = new StringPreprocessor(stringPreprocessingUnits);
            WekaTask task = new GetStringConstraintsTask(stringPreprocessor);
            task.execute(instances);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        // Trie trie = new Trie();
        PatriciaTrie<String> trie = new PatriciaTrie<>();
        //  Map<String, String> lemms = new HashMap<>();
      //  t.add("they");
     //   t.add("thew");
     //   t.add("thew");

        long start = System.nanoTime();

//        Scanner sc = new Scanner(new File("dm-thesis-weka/src/main/resources/lemmatization-sk.txt"));
        FileReader fr = new FileReader("dm-thesis-weka/src/main/resources/lemmatization-sk.txt");
        BufferedReader br = new BufferedReader(fr);
        String line;

        while ((line = br.readLine()) != null) {
            String[] words = line.split("\t");
            trie.put(words[1], words[0]);
            // do your stuff...
        }

        /*
        while (sc.hasNextLine()) {
            String[] words = sc.nextLine().split("\t");

            trie.put(words[1], words[0]);

        //    lemms.put(words[1], words[0]);
        //    for (int i = 0; i < words.length; i++) {
        //    }
        }
*/
        long end = System.nanoTime();

        System.out.println((end - start) / 1_000_000_000.0);

        String test = "arw is noon";
        System.out.println(trie.get("káblu"));

    }
}
