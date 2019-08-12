package org.chelonix.cnab.core;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class BundleTest {

    @Test
    public void readBundle() throws Exception {
        Bundle bundle = BundleReader.read(this.getClass().getResourceAsStream("simple-bundle.json"));
        System.out.println(bundle);
        for (Map.Entry<String, Parameter> e: bundle.getParameters().entrySet()) {
            System.out.printf("%s -> %s\n", e.getKey(), e.getValue());
        }

        for (Map.Entry<String, Output> e: bundle.getOutputs().entrySet()) {
            System.out.printf("%s -> %s\n", e.getKey(), e.getValue());
        }
    }
}
