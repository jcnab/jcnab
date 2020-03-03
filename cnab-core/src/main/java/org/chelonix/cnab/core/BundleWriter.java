package org.chelonix.cnab.core;

import com.stratumn.canonicaljson.CanonicalJson;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbException;
import java.io.*;

public class BundleWriter {

    public static void write(Bundle bundle, Writer writer) throws BundleIOException {
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new JsonSchemaAdapter())
                .withDeserializers(
                        new Outputs.OutputsDeserializer(),
                        new Actions.ActionsDeserializer(),
                        new Credentials.CredentialsDeserializer())
                .withSerializers(new MapOf.MapOfSerializer());
        Jsonb jsonb = JsonbBuilder.create(config);
        try {
            jsonb.toJson(bundle, writer);
        } catch (JsonbException jbe) {
            throw new BundleIOException(jbe);
        }
    }

    public static String write(Bundle bundle) throws BundleIOException {
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new JsonSchemaAdapter())
                .withDeserializers(
                        new Outputs.OutputsDeserializer(),
                        new Actions.ActionsDeserializer(),
                        new Credentials.CredentialsDeserializer())
                .withSerializers(new MapOf.MapOfSerializer());

        Jsonb jsonb = JsonbBuilder.create(config);
        try {
            return jsonb.toJson(bundle);
        } catch (JsonbException jbe) {
            throw new BundleIOException(jbe);
        }
    }

    public static String writeCanonical(Bundle bundle) throws BundleIOException {
        JsonbConfig config = new JsonbConfig()
                .withAdapters(new JsonSchemaAdapter())
                .withDeserializers(
                        new Outputs.OutputsDeserializer(),
                        new Actions.ActionsDeserializer(),
                        new Credentials.CredentialsDeserializer())
                .withSerializers(new MapOf.MapOfSerializer());
        Jsonb jsonb = JsonbBuilder.create(config);
        try {
            return CanonicalJson.stringify(CanonicalJson.parse(jsonb.toJson(bundle)));
        } catch (JsonbException jbe) {
            throw new BundleIOException(jbe);
        } catch (IOException ioe) {
            throw new BundleIOException(ioe);
        }
    }
}
