package sk.upjs.ics.spellchecker;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class DefaultWordEditorTest {

    @Test
    public void splitTest() {
        WordEditor editor = new DefaultWordEditor();
        Map<String, String> splits = editor.splits("hello");
        Collection<String> deletes = editor.deletes(splits);
        Collection<String> transposes = editor.transposes(splits);
        Collection<String> replaces = editor.replaces(splits);
        Collection<String> insertions = editor.insertions(splits);
        System.out.println("Working Directory = " +
            System.getProperty("user.dir"));
        HashSet<String> wordList = new HashSet<>();

        try (Scanner sc = new Scanner(new File("./src/main/resources/words.txt"))) {
            while (sc.hasNextLine())
                wordList.add(sc.nextLine());
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        SpellChecker spellChecker = new DefaultSpellChecker(editor, wordList); // korrectud
        Collection<String> candidates = spellChecker.getCandidates("speling");
        candidates.forEach(System.out::println);
    }
}