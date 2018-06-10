package sk.upjs.ics.preprocessing.nlp;

import org.apache.commons.collections4.trie.PatriciaTrie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// https://github.com/michmech/lemmatization-lists/
public class TrieRootWordHandler implements RootWordHandler {

    private PatriciaTrie<String> trie;

    // Makes assumption that key is lemma, value is word
    public static TrieRootWordHandler create(String dictionaryPath) {
        TrieRootWordHandler trieRootWordHandler = new TrieRootWordHandler();
        trieRootWordHandler.initTrie(dictionaryPath);

        return trieRootWordHandler;
    }

    private void initTrie(String dictionaryPath) {
        trie = new PatriciaTrie<>();
        FileReader fr = null;

        try {
            fr = new FileReader(dictionaryPath);
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't find file at " + dictionaryPath);
        }

        BufferedReader br = new BufferedReader(fr);
        String line;

        try {
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\t");
                trie.put(words[1], words[0]);
            }
        } catch (IOException exc) {
            System.out.println("Couldn't read line at " + dictionaryPath);
        }
    }

    @Override
    public String getRootWord(String word) {
        return trie.get(word);
    }

    @Override
    public List<String> getRootWords(List<String> words) {
        return words.stream()
            .map(this::getRootWord)
            .collect(Collectors.toList());
    }
}
