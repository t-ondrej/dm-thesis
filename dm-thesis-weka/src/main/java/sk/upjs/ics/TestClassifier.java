package sk.upjs.ics;

public class TestClassifier {

	// 0 - 2. number
	// 1 - 1. number
	// 2 - 0. number
	// 3 - punctuation count
	// 4 - . count
	// 5 - / count
	public static double classify(Object[] i) throws Exception {
		double p = Double.NaN;
		p = TestClassifier.N5d22bbb70(i);
		return p;
	}
	private static double N5d22bbb70(Object[] i) {
		double p = Double.NaN;
		if (i[4] == null) {
			p = 2;
		} else if ((Double) i[4] <= 0.0) {
			p = TestClassifier.N41a4555e1(i);
		} else if ((Double) i[4] > 0.0) {
			p = TestClassifier.N123772c45(i);
		}
		return p;
	}
	private static double N41a4555e1(Object[] i) {
		double p = Double.NaN;
		if (i[2] == null) {
			p = 3;
		} else if ((Double) i[2] <= 12.0) {
			p = TestClassifier.N3830f1c02(i);
		} else if ((Double) i[2] > 12.0) {
			p = 2;
		}
		return p;
	}
	private static double N3830f1c02(Object[] i) {
		double p = Double.NaN;
		if (i[0] == null) {
			p = 3;
		} else if ((Double) i[0] <= 28.0) {
			p = TestClassifier.N39ed3c8d3(i);
		} else if ((Double) i[0] > 28.0) {
			p = 3;
		}
		return p;
	}
	private static double N39ed3c8d3(Object[] i) {
		double p = Double.NaN;
		if (i[1] == null) {
			p = 2;
		} else if ((Double) i[1] <= 12.0) {
			p = TestClassifier.N71dac7044(i);
		} else if ((Double) i[1] > 12.0) {
			p = 3;
		}
		return p;
	}
	private static double N71dac7044(Object[] i) {
		double p = Double.NaN;
		if (i[0] == null) {
			p = 3;
		} else if ((Double) i[0] <= 2.0) {
			p = 3;
		} else if ((Double) i[0] > 2.0) {
			p = 2;
		}
		return p;
	}
	private static double N123772c45(Object[] i) {
		double p = Double.NaN;
		if (i[0] == null) {
			p = 1;
		} else if ((Double) i[0] <= 99.0) {
			p = 1;
		} else if ((Double) i[0] > 99.0) {
			p = 0;
		}
		return p;
	}
}
