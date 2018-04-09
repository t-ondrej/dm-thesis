package sk.upjs.ics.task;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.InterquartileRange;

/**
 * Created by Tomas on 12.2.2018.
 *
 * Weka implementation of filter outliers task
 * Uses IQR method
 */
public class FilterOutliersTaskIQR extends AbstractTask<Instances> {

    private Instances input;

    private FilterOutliersTaskIQR(Instances input) {
        this.input = input;
    }

    public static FilterOutliersTaskIQR create(Instances input) {
        return new FilterOutliersTaskIQR(input);
    }

    @Override
    protected void preprocessInput() {
        // Placeholder
    }

    @Override
    public Instances coreExecute() {
        InterquartileRange filter = new InterquartileRange();
        filter.setOutlierFactor(1);

        try {
            filter.setInputFormat(input);
            return Filter.useFilter(input, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return input;
    }
}
