package sk.upjs.ics;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomas on 11.2.2018.
 */
public class DateGenerator {
    private static String filePath = "dates.csv";

    public static void main(String[] args) {
        generateDatesWithClass();
    }

    public static void generateDatesWithClass() {
        List<String> dates = new ArrayList<>();
        List<String> formats = new ArrayList<>();

        for (int i = 0; i < 10_000; i++) {
            double randomness = Math.random();

            String year = getYear();
            String month = getMonth();
            String day = getDay();

            String result = "";
            String format = "";

            if (randomness < 0.25) {
                result = day + "." + month + "." + year;
                format = "dd.mm.yyyy";
            }

            else if (randomness < 0.5) {
                result = day + "." + month + "." + year.substring(2);
                format = "dd.mm.yy";
            }

            else if (randomness < 0.75) {
                result = year.substring(2) + "/" + month + "/" + day;
                format = "yy/mm/dd";
            }

            else if (randomness < 1) {
                result = month + "/" + day + "/" + year.substring(2);
                format = "mm/dd/yy";
            }

            dates.add(result); //+ (i == 9999 ? "" : ","));
            formats.add(format);
        }

        try (PrintWriter pw = new PrintWriter(new File(filePath))) {

            //dates.forEach(date -> pw.println(date));

            for (int i = 0; i < dates.size(); i++) {
                pw.println(dates.get(i) + "," + formats.get(i));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getYear() {
        return Integer.toString(1900 + ((int)(Math.random() * 100)));
    }

    private static String getMonth() {
        String result = Integer.toString(1 + ((int)(Math.random() * 12)));

        return result.length() < 2 ? "0" + result : result;
    }

    private static String getDay() {
        String result =  Integer.toString(1 + ((int)(Math.random() * 28)));

        return result.length() < 2 ? "0" + result : result;
    }
}
