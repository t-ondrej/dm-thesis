package sk.upjs.ics.preprocessing.quantification;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PunctuationOccurencyGetter implements Quantificator<String> {

	private final String punc = " .,;:'\"()?!";
	private final Set<String> labels = new HashSet<>();

	public static PunctuationOccurencyGetter create() {
		return new PunctuationOccurencyGetter();
	}

	@Override
	public Set<String> getLabels() {
		return labels;
	}

	@Override
	public Map<String, Integer> getQuantificators(String string) {
		Map<String, Integer> result = new HashMap<>();

		for (int i = 0; i < string.length(); i++) {
			char charAt = string.charAt(i);
			String stringCharAt = Character.toString(charAt);
			if (!Character.isDigit(charAt) && !Character.isLetter(charAt)) {
				int count = result.getOrDefault(stringCharAt, 0);
				result.put(stringCharAt, count + 1);
				labels.add(stringCharAt);
			}
		}

		return result;
	}
}
