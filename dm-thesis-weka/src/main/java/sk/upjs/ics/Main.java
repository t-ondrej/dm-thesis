package sk.upjs.ics;

import weka.core.Trie;

public class Main {

    // autocorrect
    // typicky zastupca
    // textove tagy

    // OK: autocomplete, integritne obmedzenie, filtering

    // https://stackoverflow.com/questions/29487186/named-entity-recognition-using-weka

    public static void main(String[] args) {
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

        LanguageModelComputer languageModelComputer = new MarkovLanguageModelComputer(
                Arrays.asList(sentences), WekaNGramGetter.create());

        LanguageModel languageModel = languageModelComputer.getLanguageModel();
        Autocompleter autocompleter = new MarkovAutocompleter((MarkovLanguageModel)languageModel);
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

        Trie t = new Trie();
        t.add("they");
        t.add("thew");
        t.add("thew");

        System.out.println(t.getWithPrefix("the"));

        String test = "arw is noon";

        String avarage = "Ther, there is my friends.";
    }
}
