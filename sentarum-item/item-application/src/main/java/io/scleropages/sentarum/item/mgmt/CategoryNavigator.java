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
package io.scleropages.sentarum.item.mgmt;

import io.scleropages.sentarum.item.category.model.Category;

import java.util.Set;

/**
 * 类目导航器，提供类目向上(to parents)以及向下(to children)的访问操作。
 * 导航视图是基于内存的类目视图。如果类目发生改变，可能不会第一时间在内存视图中生效。
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CategoryNavigator {

    /**
     * 如果存在连接（direct）返回true
     *
     * @param u
     * @param v
     * @return
     */
    boolean hasConnecting(Category u, Category v);

    /**
     * 当前类目导航是否存在循环 eg:(a->b->c->a)
     *
     * @return
     */
    boolean hasCycle();


    /**
     * 返回所有相邻类目（不论方向）
     *
     * @return
     */
    Set<Category> adjacentCategory(Category n);


    /**
     * 返回给定类目的所有连线（direct）
     *
     * @param n
     * @return
     */
    Set<Connection> connections(Category n);

    /**
     * 返回所有前置（或上级）类目（direct）
     *
     * @param n
     * @return
     */
    Set<Category> predecessors(Category n);


    /**
     * 返回所有后续（或下级）类目（direct）
     *
     * @param n
     * @return
     */
    Set<Category> successors(Category n);

    /**
     * 返回给定类目所有可到达的类目.
     *
     * @param n
     * @return
     */
    Set<Category> reachableCategories(Category n);


    class Connection {
        private final Category source;
        private final Category target;

        public Connection(Category source, Category target) {
            this.source = source;
            this.target = target;
        }

        public Category getSource() {
            return source;
        }

        public Category getTarget() {
            return target;
        }

        @Override
        public String toString() {
            return "Connection{" +
                    "source=" + source +
                    ", target=" + target +
                    '}';
        }
    }
}
