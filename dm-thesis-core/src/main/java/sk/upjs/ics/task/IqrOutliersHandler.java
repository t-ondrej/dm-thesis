package sk.upjs.ics.task;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class IqrOutliersHandler implements OutliersHandler {

    private int outlierFactor = 1;

    public void setOutlierFactor(int outlierFactor) {
        this.outlierFactor = outlierFactor;
    }

    @Override
    public List<Double> filterOutliers(List<Double> numbers) {
        Collections.sort(numbers);

        double q1;
        double q3;

        int half = numbers.size() / 2;
        int quarter = half / 2;

        if (half % 2 == 1) {
            q1 = numbers.get(quarter);
            q3 = numbers.get(numbers.size() - quarter - 1);
        } else {
            q1 = (numbers.get(quarter) + numbers.get(quarter + 1)) / 2;
            q3 = (numbers.get(numbers.size() - quarter - 1) + numbers.get(numbers.size() - quarter)) / 2;
        }

        double iqr = q3 - q1;
        double lowerOutlier = q1 - outlierFactor * iqr;
        double upperOutlier = q3 + outlierFactor * iqr;

        return numbers.stream()
            .filter(number -> number >= upperOutlier || number <= lowerOutlier)
            .collect(Collectors.toList());
    }
}
