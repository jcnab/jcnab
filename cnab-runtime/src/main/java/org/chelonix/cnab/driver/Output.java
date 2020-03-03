package org.chelonix.cnab.driver;

import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Output {

    private Map<String, InputStream> outputs;

    public Iterable<String> outputs() {
        if (outputs == null) {
            return Collections.emptySet();
        }
        return outputs.keySet();
    }

    public InputStream getData(String name) {
        return outputs.get(name);
    }

    public static class Builder {

        private Output output = new Output();

        public Builder withOutput(String name, InputStream data) {
            if (output.outputs == null) {
                output.outputs = new HashMap<>();
            }
            output.outputs.put(name, data);
            return this;
        }

        public Output build() {
            return output;
        }
    }
}
