package sk.upjs.ics.dataaccess;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class DefaultFileSourcedConverter
    extends AbstractFileSourcedConverter
    implements DataSetConverter<Collection<String>> {

    private Class<Collection<String>> collectionClass;

    public DefaultFileSourcedConverter(String filePath, Class<Collection<String>> collectionClass) {
        super(filePath);
        this.collectionClass = collectionClass;
    }

    @Override
    public Collection<String> loadDataSet() {
        Collection<String> result = null;

        try {
            result = collectionClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        try (Scanner sc = new Scanner(new File(filePath))) {
            while (sc.hasNextLine()) {
                result.add(sc.nextLine());
            }
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }

        return result;
    }

    @Override
    public void saveDataSet(Collection<String> objectCollection) {
        try (PrintWriter pw = new PrintWriter(new File(filePath))) {
            objectCollection
                .forEach(pw::print);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

}
