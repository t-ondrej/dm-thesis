package sk.upjs.ics.dataaccess;

public interface DataSetConverter<T> {
	T loadDataSet();
	void saveDataSet(T t);
}
