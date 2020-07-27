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
package io.scleropages.sentarum.item.mgmt.impl;

import com.google.common.collect.Sets;
import com.google.common.graph.EndpointPair;
import io.scleropages.sentarum.item.category.entity.AbstractCategoryEntity;
import io.scleropages.sentarum.item.category.entity.MarketingCategoryEntity;
import io.scleropages.sentarum.item.category.entity.StandardCategoryEntity;
import io.scleropages.sentarum.item.category.entity.mapper.AbstractCategoryEntityMapper;
import io.scleropages.sentarum.item.category.entity.mapper.MarketingCategoryEntityMapper;
import io.scleropages.sentarum.item.category.entity.mapper.StandardCategoryEntityMapper;
import io.scleropages.sentarum.item.category.model.Category;
import io.scleropages.sentarum.item.category.repo.CategoryEntityGraph;
import io.scleropages.sentarum.item.mgmt.CategoryNavigator;
import org.scleropages.crud.ModelMapperRepository;
import org.springframework.util.Assert;

import java.util.Set;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CategoryNavigatorImpl implements CategoryNavigator {

    private final CategoryEntityGraph<AbstractCategoryEntity> graph;

    public CategoryNavigatorImpl(CategoryEntityGraph<? extends AbstractCategoryEntity> graph) {
        this.graph = (CategoryEntityGraph<AbstractCategoryEntity>) graph;
    }

    @Override
    public boolean hasConnecting(Category u, Category v) {
        return graph.hasEdgeConnecting(u.id(), v.id());
    }

    @Override
    public boolean hasCycle() {
        return graph.hasCycle();
    }

    @Override
    public Set<Category> adjacentCategory(Category n) {
        Long id = n.id();
        AbstractCategoryEntity byId = graph.getById(id);
        AbstractCategoryEntityMapper mapper = getMapper(graph.getById(id));
        return Sets.newHashSet(mapper.mapForReads(graph.adjacentNodes(byId)));
    }


    @Override
    public Set<Connection> connections(Category n) {
        Long id = n.id();
        AbstractCategoryEntity byId = graph.getById(id);
        AbstractCategoryEntityMapper mapper = getMapper(graph.getById(id));
        Set<EndpointPair<AbstractCategoryEntity>> source = graph.incidentEdges(byId);
        Set<Connection> connections = Sets.newHashSetWithExpectedSize(source.size());
        for (EndpointPair<AbstractCategoryEntity> sourceEp : source) {
            Connection connecting = new Connection((Category) mapper.mapForRead(sourceEp.source()), (Category) mapper.mapForRead(sourceEp.target()));
            connections.add(connecting);
        }
        return connections;
    }

    @Override
    public Set<Category> predecessors(Category n) {
        Long id = n.id();

        AbstractCategoryEntity byId = graph.getById(id);
        AbstractCategoryEntityMapper mapper = getMapper(graph.getById(id));
        return Sets.newHashSet(mapper.mapForReads(graph.predecessors(byId)));
    }

    @Override
    public Set<Category> successors(Category n) {
        Long id = n.id();

        AbstractCategoryEntity byId = graph.getById(id);
        AbstractCategoryEntityMapper mapper = getMapper(graph.getById(id));
        return Sets.newHashSet(mapper.mapForReads(graph.successors(byId)));
    }

    @Override
    public Set<Category> reachableCategories(Category n) {
        Long id = n.id();

        AbstractCategoryEntity byId = graph.getById(id);
        AbstractCategoryEntityMapper mapper = getMapper(graph.getById(id));
        return Sets.newHashSet(mapper.mapForReads(graph.reachableNodes(byId)));
    }


    protected AbstractCategoryEntityMapper getMapper(AbstractCategoryEntity entity) {
        Assert.notNull(entity, "given entity must not be null.");
        if (entity instanceof StandardCategoryEntity) {
            return (AbstractCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(StandardCategoryEntityMapper.class);
        } else if (entity instanceof MarketingCategoryEntity) {
            return (AbstractCategoryEntityMapper) ModelMapperRepository.getRequiredModelMapper(MarketingCategoryEntityMapper.class);
        }
        throw new IllegalStateException("unknown entity type: " + entity.getClass());
    }
}
