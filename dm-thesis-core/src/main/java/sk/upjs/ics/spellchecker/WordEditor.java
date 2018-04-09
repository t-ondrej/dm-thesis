package sk.upjs.ics.spellchecker;

import java.util.Collection;
import java.util.Map;

public interface WordEditor {
    Collection<String> getAllEdits(String word);
    Map<String, String> splits(String word);

    Collection<String> insertions(Map<String, String> splits);
    Collection<String> deletes(Map<String, String> splits);
    Collection<String> transposes(Map<String, String> splits);
    Collection<String> replaces(Map<String, String> splits);
}
