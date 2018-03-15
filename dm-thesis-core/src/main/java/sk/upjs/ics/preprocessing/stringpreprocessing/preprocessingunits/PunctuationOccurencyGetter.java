package sk.upjs.ics.preprocessing.stringpreprocessing.preprocessingunits;

import sk.upjs.ics.preprocessing.stringpreprocessing.StringPreprocessingUnit;

import java.util.HashMap;
import java.util.Map;

public class PunctuationOccurencyGetter extends StringPreprocessingUnit {

	protected String punc = " .,;:'\"()?!";

	public static PunctuationOccurencyGetter create() {
		return new PunctuationOccurencyGetter();
	}

	@Override public Map<String, Integer> preprocess(String string) {
		result = new HashMap<>();

		for (int i = 0; i < string.length(); i++) {
			char charAt = string.charAt(i);
			String stringCharAt = Character.toString(charAt);
			if (!Character.isDigit(charAt) && !Character.isLetter(charAt)) {
				int count = result.getOrDefault(stringCharAt, 0);
				result.put(stringCharAt, count + 1);
			}
		}

		return result;
	}
}
