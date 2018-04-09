package sk.upjs.ics.spellchecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

public class DefaultSpellChecker implements SpellChecker {

    private WordEditor wordEditor;
    private HashSet<String> wordList;

    public DefaultSpellChecker(WordEditor wordEditor, HashSet<String> wordList) {
        this.wordEditor = wordEditor;
        this.wordList = wordList;
    }

    @Override
    public boolean isCorrect(String word) {
        return wordList.contains(word);
    }

    @Override
    public Collection<String> getCandidates(String word) {
        Collection<String> result = new ArrayList<>();

        if (isCorrect(word))
            return result;

        return wordEditor.getAllEdits(word).stream()
            .filter(candidate -> wordList.contains(candidate))
            .collect(Collectors.toCollection(ArrayList::new));
    }
}
