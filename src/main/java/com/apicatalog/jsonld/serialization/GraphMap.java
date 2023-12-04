/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.jsonld.serialization;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.apicatalog.jsonld.lang.Keywords;
import com.apicatalog.jsonld.serialization.RdfToJsonld.Reference;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import jakarta.json.JsonValue;

final class GraphMap {

    //                 graph,     subject,  predicate,  object
    private final Object2ObjectMap<String, Object2ObjectMap<String, Map<String, JsonValue>>> index;

    private final Object2ObjectMap<String, Object2ObjectMap<String, ObjectList<Reference>>> usages;

    public GraphMap() {
        this.index = new Object2ObjectLinkedOpenHashMap<>();
        this.index.put(Keywords.DEFAULT, new Object2ObjectLinkedOpenHashMap<>());

        this.usages = new Object2ObjectLinkedOpenHashMap<>();
    }

    public boolean contains(final String graphName, final String subject) {
        return index.containsKey(graphName) && index.get(graphName).containsKey(subject);
    }

    public void set(final String graphName, final String subject, final String property, final JsonValue value) {
        index
            .computeIfAbsent(graphName, e -> new Object2ObjectLinkedOpenHashMap<>())
            .computeIfAbsent(subject, e -> new Object2ObjectLinkedOpenHashMap<>())
            .put(property, value);
    }

    public Optional<Map<String, JsonValue>> get(final String graphName, final String subject) {

        final Map<String, Map<String, JsonValue>> graphMap = index.get(graphName);

        if (graphMap == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(graphMap.get(subject));
    }

    public Optional<JsonValue> get(final String graphName, final String subject, final String property) {

        final Map<String, Map<String, JsonValue>> graphMap = index.get(graphName);

        if (graphMap == null) {
            return Optional.empty();
        }

        final Map<String, JsonValue> subjectMap = graphMap.get(subject);

        if (subjectMap == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(subjectMap.get(property));
    }

    public ObjectSet<String> keys(String graphName) {
        return index.get(graphName).keySet();
    }

    public boolean contains(String graphName) {
        return index.containsKey(graphName);
    }

    public ObjectSet<String> keys() {
        return index.keySet();
    }

    public ObjectList<Reference> getUsages(String graphName, String subject) {
        return usages.containsKey(graphName) && usages.get(graphName).containsKey(subject)
                    ? usages.get(graphName).get(subject)
                    : new ObjectArrayList<>();
    }

    public void addUsage(String graphName, String subject, Reference reference) {
        usages.computeIfAbsent(graphName, e -> new Object2ObjectLinkedOpenHashMap<>())
            .computeIfAbsent(subject, e -> new ObjectArrayList<>())
            .add(reference)
            ;
    }

    public void remove(String graphName, String subject) {
        index.get(graphName).remove(subject);
    }
}
