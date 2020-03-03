package org.chelonix.cnab.core;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class Outputs extends MapOf<Output, Outputs> {

    public Outputs() {
        super(new HashMap<String, Output>());
    }

    public Outputs(Output... outputs) {
        this();
        add(outputs);
    }

    Outputs(Map<String, Output> map) {
        super(map);
    }

    public static class OutputsDeserializer implements JsonbDeserializer<Outputs> {

        public OutputsDeserializer() {
        }

        @Override
        public Outputs deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
            Type t = new HashMap<String, Output>(){}.getClass().getGenericSuperclass();
            Map<String, Output> map = deserializationContext.deserialize(t, jsonParser);
            return new Outputs(map);
        }
    }
}
