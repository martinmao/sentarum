/**
 * Copyright 2001-2005 The Apache Software Foundation.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.item.category.repo;

import com.google.common.collect.Maps;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import io.scleropages.sentarum.item.category.entity.AbstractCategoryEntity;

import java.util.Map;
import java.util.Set;

/**
 * Wrapper guava {@link MutableGraph} used for navigate category entities.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CategoryEntityGraph<E extends AbstractCategoryEntity> implements MutableGraph<E> {


    private final Map<Long, AbstractCategoryEntity> mappings;

    private final MutableGraph nativeGraph;

    public CategoryEntityGraph(MutableGraph nativeGraph) {
        this.nativeGraph = nativeGraph;
        mappings = Maps.newHashMap();
    }


    public E getById(Long id) {
        return (E) mappings.get(id);
    }

    @Override
    public boolean addNode(E node) {
        mappings.putIfAbsent(node.getId(), node);
        return nativeGraph.addNode(node);
    }

    @Override
    public boolean putEdge(E nodeU, E nodeV) {
        mappings.putIfAbsent(nodeU.getId(), nodeU);
        mappings.putIfAbsent(nodeV.getId(), nodeV);
        return nativeGraph.putEdge(nodeU, nodeV);
    }

    @Override
    public boolean removeNode(E node) {
        mappings.remove(node.getId(), node);
        return nativeGraph.removeNode(node);
    }

    @Override
    public boolean removeEdge(E nodeU, E nodeV) {
        return nativeGraph.removeEdge(nodeU, nodeV);
    }

    @Override
    public Set<E> nodes() {
        return nativeGraph.nodes();
    }

    @Override
    public Set<EndpointPair<E>> edges() {
        return nativeGraph.edges();
    }

    @Override
    public boolean isDirected() {
        return nativeGraph.isDirected();
    }

    @Override
    public boolean allowsSelfLoops() {
        return nativeGraph.allowsSelfLoops();
    }

    @Override
    public ElementOrder<E> nodeOrder() {
        return nativeGraph.nodeOrder();
    }

    @Override
    public Set<E> adjacentNodes(E node) {
        return nativeGraph.adjacentNodes(node);
    }

    @Override
    public Set<E> predecessors(E node) {
        return nativeGraph.predecessors(node);
    }

    @Override
    public Set<E> successors(E node) {
        return nativeGraph.successors(node);
    }

    @Override
    public Set<EndpointPair<E>> incidentEdges(E node) {
        return nativeGraph.incidentEdges(node);
    }

    @Override
    public int degree(E node) {
        return nativeGraph.degree(node);
    }

    @Override
    public int inDegree(E node) {
        return nativeGraph.inDegree(node);
    }

    @Override
    public int outDegree(E node) {
        return nativeGraph.outDegree(node);
    }

    @Override
    public boolean hasEdgeConnecting(E nodeU, E nodeV) {
        return nativeGraph.hasEdgeConnecting(nodeU, nodeV);
    }

    public boolean hasEdgeConnecting(Long idU, Long idV) {
        return nativeGraph.hasEdgeConnecting(getById(idU), getById(idV));
    }


    public boolean hasCycle() {
        return Graphs.hasCycle(nativeGraph);
    }

    public Set<E> reachableNodes(E node) {
        return Graphs.reachableNodes(nativeGraph, node);
    }
}