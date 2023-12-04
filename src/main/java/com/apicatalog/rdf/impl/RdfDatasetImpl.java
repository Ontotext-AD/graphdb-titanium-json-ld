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

import java.util.Optional;

import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTriple;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import it.unimi.dsi.fastutil.objects.ObjectSet;

final class RdfDatasetImpl implements RdfDataset {

    private final Object2ObjectMap<RdfResource, RdfGraphImpl> graphs;

    private final ObjectList<RdfNQuad> nquads;

    private final RdfGraphImpl defaultGraph;

    protected RdfDatasetImpl() {
        this.graphs = new Object2ObjectOpenHashMap<>(1);
        this.nquads = new ObjectArrayList<>();
        this.defaultGraph = new RdfGraphImpl();
    }

    @Override
    public RdfGraph getDefaultGraph() {
        return defaultGraph;
    }

    @Override
    public ObjectList<RdfNQuad> toList() {
        return nquads;
    }

    public RdfDataset add(final RdfNQuad nquad) {

        if (nquad == null) {
            throw new IllegalArgumentException();
        }

        final Optional<RdfResource> graphName = nquad.getGraphName();

        if (graphName.isPresent()) {

            RdfGraphImpl graph = graphs.get(graphName.get());

            if (graph == null) {

                graph = new RdfGraphImpl();
                graphs.put(graphName.get(), graph);
                graph.add(nquad);
                nquads.add(nquad);

            } else if (!graph.contains(nquad)) {

                graph.add(nquad);
                nquads.add(nquad);
            }

        } else {

            // add to default graph
            if (!defaultGraph.contains(nquad)) {
                defaultGraph.add(nquad);
                nquads.add(nquad);
            }
        }
        return this;
    }

    @Override
    public ObjectSet<RdfResource> getGraphNames() {
        return graphs.keySet();
    }

    @Override
    public Optional<RdfGraph> getGraph(final RdfResource graphName) {
        return Optional.ofNullable(graphs.get(graphName));
    }

    @Override
    public int size() {
        return nquads.size();
    }

    @Override
    public RdfDataset add(RdfTriple triple) {

        RdfNQuad nquad = new RdfNQuadImpl(triple.getSubject(), triple.getPredicate(), triple.getObject(), null);

        if (!defaultGraph.contains(nquad)) {
            defaultGraph.add(nquad);
            nquads.add(nquad);
        }

        return this;
    }
}
