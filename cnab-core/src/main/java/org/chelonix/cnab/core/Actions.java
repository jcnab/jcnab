package org.chelonix.cnab.core;

import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.stream.JsonParser;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

public class Actions extends MapOf<Action, Actions> {

    public Actions() {
        super(new HashMap<String, Action>());
    }

    public Actions(Action... actions) {
        this();
        add(actions);
    }

    Actions(Map<String, Action> map) {
        super(map);
    }

    public static class ActionsDeserializer implements JsonbDeserializer<Actions> {

        @Override
        public Actions deserialize(JsonParser jsonParser, DeserializationContext deserializationContext, Type type) {
            Type t = new HashMap<String, Action>(){}.getClass().getGenericSuperclass();
            Map<String, Action> map = deserializationContext.deserialize(t, jsonParser);
            return new Actions(map);
        }
    }
}
