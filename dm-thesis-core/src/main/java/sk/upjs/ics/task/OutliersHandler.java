package sk.upjs.ics.task;

import java.util.List;

public interface OutliersHandler {

    List<Double> filterOutliers(List<Double> numbers);
}
