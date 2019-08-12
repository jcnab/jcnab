package org.chelonix.cnab.driver;

public interface Driver {

    Output run(DriverOperation operation) throws Exception;

    boolean support(String imageType);
}
