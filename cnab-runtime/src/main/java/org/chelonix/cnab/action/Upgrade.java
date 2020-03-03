package org.chelonix.cnab.action;

import org.chelonix.cnab.core.Claim;
import org.chelonix.cnab.driver.Driver;
import org.chelonix.cnab.driver.DriverOperation;
import org.chelonix.cnab.driver.Output;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

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
    public Claim run() {
        try {
            DriverOperation op = newOperation();
            Output output = getDriver().run(op);
            for (String name: output.outputs()) {
                InputStream in = output.getData(name);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                int nRead;
                byte[] data = new byte[1024];
                while ((nRead = in.read(data, 0, data.length)) != -1) {
                    buffer.write(data, 0, nRead);
                }
                getClaim().withOutput(name, new String(buffer.toByteArray(), StandardCharsets.UTF_8));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getClaim();
    }

}
