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

import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import io.scleropages.sentarum.item.category.entity.AbstractCategoryEntity;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Table;
import org.scleropages.crud.dao.orm.jpa.GenericRepository;
import org.scleropages.crud.dao.orm.jpa.complement.JooqRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.Assert;

import javax.persistence.metamodel.Attribute;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@NoRepositoryBean
public interface AbstractCategoryRepository<E extends AbstractCategoryEntity, T extends Table, R extends Record> extends GenericRepository<E, Long>, JooqRepository<T, R, E> {


    @Cacheable
    default Optional<R> readById(Long id) {
        T t = dslTable();
        return dslContext().selectFrom(t).where(t.field(AbstractCategoryEntity.COLUMN_ID.toUpperCase()).eq(id)).fetchOptional();
    }

    default void consumeByRecord(Optional<R> optionalRecord, Consumer<E> entityConsumer) {
        optionalRecord.ifPresent(r -> {
            E entity = createEntity();
            dslRecordInto(r, entity, new ReferenceEntityAssembler() {
                @Override
                public void applyReferenceIdToTargetEntity(Object targetEntity, Attribute refAttribute, Field field, Object fieldValue) {

                }
            });
            entityConsumer.accept(entity);
        });
    }

    default E createEntity() {
        throw new IllegalStateException("not implemented.");
    }

    /**
     * get by id with parent.
     *
     * @param id
     * @return
     */
    @EntityGraph(attributePaths = "parent")
    E getById(Long id);

    /**
     * find all by status with parent.
     *
     * @param status
     * @return
     */
    @EntityGraph(attributePaths = "parent")
    List<E> findAllByStatusEquals(Integer status);


    /**
     * SPI interface for sub class. sub implementations how to read {@link MutableGraph} nodes.
     * this interface will used for {@link #createGraph(CategoryEntityGraphNodesSupplier)}<br><br><br>
     * <p>
     * IMPORTANT NOTICE: read category entities's parent({@link AbstractCategoryEntity#getParent()} )  must provided.
     *
     * @return
     */

    interface CategoryEntityGraphNodesSupplier extends Supplier<List<? extends AbstractCategoryEntity>> {

    }


    /**
     * return {@link MutableGraph} from categories by {@link CategoryEntityGraphNodesSupplier}.
     * 上层应用不应该直接调用该方法，实现类应该根据不同策略调用该方法生成不同的重载版本
     *
     * @param graphNodesSupplier
     * @return
     */
    default CategoryEntityGraph<E> createGraph(CategoryEntityGraphNodesSupplier graphNodesSupplier) {
        Assert.notNull(graphNodesSupplier, "graphNodesSupplier must not be null.");
        List<? extends AbstractCategoryEntity> graphNodes = graphNodesSupplier.get();
        Assert.notEmpty(graphNodes, "graph nodes must not be empty.");
        CategoryEntityGraph<E> graph = new CategoryEntityGraph(GraphBuilder.directed().allowsSelfLoops(false).build());

        for (AbstractCategoryEntity graphNode : graphNodes) {
            AbstractCategoryEntity parent = graphNode.getParent();
            if (null == parent) {
                graph.addNode((E) graphNode);
            } else {
                graph.putEdge((E) parent, (E) graphNode);
            }
        }
        return graph;
    }

    /**
     * return {@link MutableGraph} from categories by {@link Specification} result.
     * 上层应用不应该直接调用该方法，实现类应该根据不同策略调用该方法生成不同的重载版本
     *
     * @param specification
     * @return
     */
    default CategoryEntityGraph<E> createGraph(Specification<? extends AbstractCategoryEntity> specification) {
        return createGraph(() -> findAll((Specification<E>) specification));
    }


}
