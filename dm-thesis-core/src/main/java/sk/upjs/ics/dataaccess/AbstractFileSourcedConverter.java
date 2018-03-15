package sk.upjs.ics.dataaccess;

public abstract class AbstractFileSourcedConverter implements FileSourcedConverter {

	String filePath;

	AbstractFileSourcedConverter(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
}
