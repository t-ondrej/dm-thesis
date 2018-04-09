package sk.upjs.ics.spellchecker;

import java.util.Collection;

public interface SpellChecker {
    boolean isCorrect(String word);
    Collection<String> getCandidates(String word);
}
