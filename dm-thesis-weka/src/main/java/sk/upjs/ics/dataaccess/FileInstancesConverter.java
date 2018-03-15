package sk.upjs.ics.dataaccess;

import weka.core.Instances;
import weka.core.converters.ConverterUtils;

/**
 * Loading/saving instances from/to file
 */
public class FileInstancesConverter extends AbstractFileSourcedConverter implements InstancesConverter {

	public FileInstancesConverter(String filePath) {
		super(filePath);
	}

	@Override
	public Instances loadDataSet() {
		try {
			ConverterUtils.DataSource dataSource = new ConverterUtils.DataSource(filePath);
			return dataSource.getDataSet();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void saveDataSet(Instances instances) {
		try {
			ConverterUtils.DataSink dataSink = new ConverterUtils.DataSink(filePath);
			dataSink.write(instances);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
