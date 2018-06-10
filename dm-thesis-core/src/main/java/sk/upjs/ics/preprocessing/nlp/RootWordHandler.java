package sk.upjs.ics.preprocessing.nlp;

import java.util.List;

public interface RootWordHandler {
    String getRootWord(String word);
    List<String> getRootWords(List<String> words);
}
