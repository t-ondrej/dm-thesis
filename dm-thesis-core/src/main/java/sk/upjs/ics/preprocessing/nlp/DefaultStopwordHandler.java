package sk.upjs.ics.preprocessing.nlp;

import java.util.HashSet;
import java.util.Scanner;

public class DefaultStopwordHandler implements StopwordHandler {

    private HashSet<String> stopwords = new HashSet<>();

    public static DefaultStopwordHandler create(String filePath) {
        DefaultStopwordHandler stopwordHandler = new DefaultStopwordHandler();
        stopwordHandler.fetchStopwords(filePath);

        return stopwordHandler;
    }

    @Override
    public HashSet<String> getStopwords() {
        return stopwords;
    }

    private void fetchStopwords(String filePath) {
        Scanner sc = new Scanner(filePath);

        while (sc.hasNextLine()) {
            stopwords.add(sc.nextLine());
        }

        sc.close();
    }
}
