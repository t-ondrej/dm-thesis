package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class ByteArrayGetter extends StringPreprocessingUnit {

    public static ByteArrayGetter create() {
        return new ByteArrayGetter();
    }

    @Override public Map<String, Integer> preprocess(String s) {
        result = new HashMap<>();
        byte[] stringByteArray = s.getBytes();

        for (int i = 0; i < stringByteArray.length; i++) {
            result.put((i + 1) + ". Char", (int)stringByteArray[i]);
        }

        return result;
    }
}
