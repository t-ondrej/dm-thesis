package sk.upjs.ics.task;

import sk.upjs.ics.documentcategorization.DocumentCategorizer;
import sk.upjs.ics.preprocessing.nlp.RootWordHandler;
import sk.upjs.ics.preprocessing.nlp.StopwordHandler;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Attribute;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WekaDocumentCategorizer extends DocumentCategorizer {

    private Instances labeledStrings;
    private Classifier naive = new NaiveBayes();

    public static WekaDocumentCategorizer create(Instances labeledStrings,
                                                 RootWordHandler rootWordHandler,
                                                 StopwordHandler stopwordHandler) {
        WekaDocumentCategorizer documentCategorizer = new WekaDocumentCategorizer(
            labeledStrings, rootWordHandler, stopwordHandler);

        documentCategorizer.init();
        return documentCategorizer;
    }

    private WekaDocumentCategorizer(Instances labeledStrings,
                                   RootWordHandler rootWordHandler,
                                   StopwordHandler stopwordHandler) {
        super(rootWordHandler, stopwordHandler);
        this.labeledStrings = labeledStrings;
    }

    private void init() {
        try {
            naive.buildClassifier(labeledStrings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected String coreGetCategory(List<String> document) {
        String joinedString = String.join(" ", document);

        // create instances and classify it
        Attribute attr = new Attribute("document");
        Instances instances = new Instances("instances", (ArrayList)Arrays.asList(attr), 1);
        try {
            double idx = naive.classifyInstance(instances.firstInstance());
            return labeledStrings.classAttribute().value((int) idx);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // classify instance, so document should be one single instance
        return "Not found";
    }
}
