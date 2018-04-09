package sk.upjs.ics.helpers;

import weka.core.Attribute;

import java.util.ArrayList;
import java.util.List;

public final class AttributeUtils {

    public static List<String> getStringValuesList(Attribute attribute) {
        List<String> stringValuesList = new ArrayList<>();

        if (attribute.isString())
            return stringValuesList;

        for (int i = 0; i < attribute.numValues(); i++) {
            stringValuesList.add(attribute.value(i));
        }

        return stringValuesList;
    }
}
