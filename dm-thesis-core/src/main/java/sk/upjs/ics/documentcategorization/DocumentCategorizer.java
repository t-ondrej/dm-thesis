package sk.upjs.ics.documentcategorization;

import sk.upjs.ics.preprocessing.nlp.RootWordHandler;
import sk.upjs.ics.preprocessing.nlp.StopwordHandler;

import java.util.List;

public abstract class DocumentCategorizer {

    private RootWordHandler rootWordHandler;
    private StopwordHandler stopwordHandler;

    public DocumentCategorizer(RootWordHandler rootWordHandler, StopwordHandler stopwordHandler) {
        this.rootWordHandler = rootWordHandler;
        this.stopwordHandler = stopwordHandler;
    }

    public String getCategory(final List<String> document) {
        List<String> rootWords = rootWordHandler.getRootWords(document);
        List<String> rootWordsWithoutStopwords = stopwordHandler.removeStopwords(rootWords);

        return coreGetCategory(rootWordsWithoutStopwords);
    }

    protected abstract String coreGetCategory(List<String> document);
}
