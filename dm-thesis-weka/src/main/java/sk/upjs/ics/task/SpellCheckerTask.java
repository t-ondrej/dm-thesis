package sk.upjs.ics.task;

import sk.upjs.ics.spellchecker.SpellChecker;

import java.util.Collection;
import java.util.Collections;

public class SpellCheckerTask extends AbstractTask<Collection<String>> {

    private SpellChecker spellChecker;
    private String keyword;

    private SpellCheckerTask(SpellChecker spellChecker, String keyword) {
        this.spellChecker = spellChecker;
        this.keyword = keyword;
    }

    public static SpellCheckerTask create(SpellChecker spellChecker, String keyword) {
        return new SpellCheckerTask(spellChecker, keyword);
    }

    @Override
    protected void preprocessInput() {
        // Placeholder
    }

    @Override
    protected Collection<String> coreExecute() {
        if (!spellChecker.isCorrect(keyword)) {
            return spellChecker.getCandidates(keyword);
        }

        return Collections.singleton(keyword);
    }
}
