package sk.upjs.ics.helpers;

public class Range {

    private int min;
    private int max;

    public Range() {
        // Placeholder
    }

    public Range(int left, int right) {
        this.min = left;
        this.max = right;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public boolean isInRange(int number) {
        return min <= number && number <= max;
    }

    public void update(int number) {
        int min = Math.min(this.min, number);
        int max = Math.max(this.max, number);

        this.min = min;
        this.max = max;
    }
}
