package sk.upjs.ics.dataaccess;

import weka.core.Instances;

// Is it good practice to just extend to specify type?

/**
 * Dataset converter with type of dataset that weka use
 */
public interface InstancesConverter extends DataSetConverter<Instances> {
}
