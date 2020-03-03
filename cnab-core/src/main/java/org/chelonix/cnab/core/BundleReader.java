package org.chelonix.cnab.core;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbException;

public class BundleReader {

    public static Bundle read(Reader reader) throws BundleIOException {
        try {
            JsonbConfig config = new JsonbConfig()
                    .withAdapters(new JsonSchemaAdapter())
                    .withDeserializers(
                            new Outputs.OutputsDeserializer(),
                            new Actions.ActionsDeserializer(),
                            new Credentials.CredentialsDeserializer())
                    .withSerializers(new MapOf.MapOfSerializer());
            Jsonb jsonb = JsonbBuilder.create(config);
            return jsonb.fromJson(reader, Bundle.class);
        } catch (JsonbException jbe) {
            throw new BundleIOException(jbe);
        }
    }

    public static Bundle read(InputStream in) throws BundleIOException {
        try {
            return read(new InputStreamReader(in));
        } catch (JsonbException jbe) {
            throw new BundleIOException(jbe);
        }
    }
}
