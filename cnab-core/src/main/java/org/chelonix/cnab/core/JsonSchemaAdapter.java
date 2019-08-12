package org.chelonix.cnab.core;

import java.io.StringReader;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import org.leadpony.justify.api.JsonSchema;
import org.leadpony.justify.api.JsonValidationService;

public class JsonSchemaAdapter implements JsonbAdapter<JsonSchema, JsonObject> {
    @Override
    public JsonObject adaptToJson(JsonSchema schema) throws Exception {
        return schema.toJson().asJsonObject();
    }

    @Override
    public JsonSchema adaptFromJson(JsonObject obj) throws Exception {
        JsonValidationService service = JsonValidationService.newInstance();
        StringReader reader = new StringReader(obj.toString());
        return service.readSchema(reader);
    }
}
