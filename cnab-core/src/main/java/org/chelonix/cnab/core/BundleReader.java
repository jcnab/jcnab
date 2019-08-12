package org.chelonix.cnab.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.JsonbException;

public class BundleReader {

    public static Bundle read(Reader reader) throws JsonbException, IOException {
        JsonbConfig config = new JsonbConfig().withAdapters(new JsonSchemaAdapter());
        Jsonb jsonb = JsonbBuilder.create(config);
        return jsonb.fromJson(reader, Bundle.class);
    }

    public static Bundle read(InputStream in) throws JsonbException, IOException {
        return read(new InputStreamReader(in));
    }
}
