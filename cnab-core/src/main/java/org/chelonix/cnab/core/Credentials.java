package org.chelonix.cnab.core;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.*;

public class Credentials extends MapOf<Credential, Credentials> {

    public Credentials() {
        super(new HashMap<String, Credential>());
    }

    public Credentials(Credential... credentials) {
        this();
        add(credentials);
    }

    Credentials(Map<String, Credential> map) {
        super(map);
    }

    public static class CredentialsDeserializer implements JsonbDeserializer<Credentials> {

        @Override
        public Credentials deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
            Type t = new HashMap<String, Credential>(){}.getClass().getGenericSuperclass();
            Map<String, Credential> map = deserializationContext.deserialize(t, jsonParser);
            return new Credentials(map);
        }
    }
}
