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
public class FilterOutliersTaskIQR implements WekaTask {

    public static FilterOutliersTaskIQR create() {
        return new FilterOutliersTaskIQR();
    }

    @Override
    public Instances execute(Instances input) {
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
