package org.chelonix.cnab.core;

import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import java.util.*;
import java.util.function.Consumer;

public class MapOf<T extends MapOf.Named, U extends MapOf<T, U>> implements Iterable<T> {

    public interface Named {
        String getName();

        void setName(String name);
    }

    protected Map<String, T> map;

    MapOf(Map<String, T> map) {
        this.map = map;
        map.forEach((k,v) -> v.setName(k));
    }

    U add(T... ts) {
        for (T t: ts) {
            map.put(t.getName(), t);
        }
        return (U) this;
    }

    public T get(String name) {
        return map.get(name);
    }

    public boolean contains(String name) {
        return map.containsKey(name);
    }

    @Override
    public Iterator<T> iterator() {
        return map.values().iterator();
    }

    @Override
    public void forEach(Consumer<? super T> consumer) {
        map.values().forEach(consumer);
    }

    @Override
    public Spliterator<T> spliterator() {
        return map.values().spliterator();
    }

    @Override
    public String toString() {
        return "MapOf{" +
                "map=" + map +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapOf<?,?> mapOf = (MapOf<?,?>) o;
        return Objects.equals(map, mapOf.map);
    }

    @Override
    public int hashCode() {
        return Objects.hash(map);
    }

    public static class MapOfSerializer implements JsonbSerializer<MapOf> {

        @Override
        public void serialize(MapOf mapOf, JsonGenerator jsonGenerator, SerializationContext serializationContext) {
            serializationContext.serialize(mapOf.map, jsonGenerator);
        }
    }

}
