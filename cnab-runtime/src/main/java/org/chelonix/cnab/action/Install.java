package org.chelonix.cnab.action;

import org.chelonix.cnab.core.Claim;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;

public class Install extends BaseAction {

    public static final String NAME = "install";

    public Install(Claim claim, Driver driver) {
        super(claim, driver);
    }

    @Override
    public String getName() {
        return Install.NAME;
    }

    @Override
    public void run() {
        try {
            DriverOperation op = newOperation();
            getDriver().run(op);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
