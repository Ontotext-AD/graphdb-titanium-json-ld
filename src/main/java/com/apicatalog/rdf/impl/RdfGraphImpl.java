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
package com.apicatalog.rdf.impl;

import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;
import com.apicatalog.rdf.RdfValue;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;

final class RdfGraphImpl implements RdfGraph {

    private final Object2ObjectMap<RdfResource, Object2ObjectMap<RdfResource, ObjectSet<RdfValue>>> index;

    private final ObjectList<RdfTriple> triples;

    protected RdfGraphImpl() {
        this.index = new Object2ObjectOpenHashMap<>(1);
        this.triples = new ObjectArrayList<>();
    }

    public void add(final RdfTriple triple) {

        if (triple == null) {
            throw new IllegalArgumentException();
        }

        index
                .computeIfAbsent(triple.getSubject(), x -> new Object2ObjectOpenHashMap<>(1))
                .computeIfAbsent(triple.getPredicate(), x -> new ObjectOpenHashSet<>(1))
                .add(triple.getObject());

        triples.add(triple);
    }

    @Override
    public boolean contains(final RdfTriple triple) {

        if (triple == null) {
            throw new IllegalArgumentException();
        }

        return index.containsKey(triple.getSubject())
                && index.get(triple.getSubject()).containsKey(triple.getPredicate())
                && index.get(triple.getSubject()).get(triple.getPredicate()).contains(triple.getObject());
    }

    @Override
    public ObjectList<RdfTriple> toList() {
        return triples;
    }
}
