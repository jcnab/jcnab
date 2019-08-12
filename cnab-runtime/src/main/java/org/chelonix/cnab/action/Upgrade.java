package org.chelonix.cnab.action;

import org.chelonix.cnab.core.Claim;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;

public class Upgrade extends BaseAction {

    public static final String NAME = "upgrade";

    public Upgrade(Claim claim, Driver driver) {
        super(claim, driver);
    }

    @Override
    public String getName() {
        return Upgrade.NAME;
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
