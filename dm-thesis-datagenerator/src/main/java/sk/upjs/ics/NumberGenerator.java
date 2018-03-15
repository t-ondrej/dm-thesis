package sk.upjs.ics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 11.2.2018.
 */
public class NumberGenerator {

    private static String filePath = "anomalies.csv";
    private static int left = 1;
    private static int right = 20;

    public static void main(String[] args) {
        generateNumbersWithOutliers();
    }

    public static void generateNumbersWithOutliers() {
        List<Integer> numbers = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < 100_000; i++) {
            double randomness = Math.random();
            Integer value = (int) (left + (Math.random() * (right - left)));

            if (randomness < 0.05) {
                counter++;
                value += left + ((int) (Math.random() * 30))  * 10;
                System.out.println(value);
            }

            numbers.add(value);
        }
        System.out.println("CNT:" + counter);
        try (PrintWriter pw = new PrintWriter(new File(filePath))) {
            numbers.forEach(pw::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
