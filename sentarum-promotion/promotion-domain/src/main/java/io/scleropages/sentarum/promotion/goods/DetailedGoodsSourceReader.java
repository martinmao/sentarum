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
package io.scleropages.sentarum.promotion.goods;

import io.scleropages.sentarum.promotion.goods.model.Goods;
import io.scleropages.sentarum.promotion.goods.model.GoodsSpecs;

import java.util.Set;

/**
 * spi strategy interface for detailed goods reading.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface DetailedGoodsSourceReader {

    /**
     * read all of goods.
     *
     * @return
     */
    AllOfGoods allOfGoods();


    /**
     * all of goods read results by {@link DetailedGoodsSourceReader}
     */
    interface AllOfGoods {
        /**
         * return a goods holder by id (native id).
         *
         * @param id
         * @return
         */
        GoodsHolder goods(Long id);

        /**
         * return all id of goods.
         *
         * @return
         */
        Set<Long> ids();
    }

    /**
     * holder of goods.
     */
    interface GoodsHolder {

        /**
         * return goods of this holder.
         *
         * @return
         */
        Goods get();

        /**
         * return goods specs by id from this goods.
         *
         * @param id
         * @return
         */
        GoodsSpecs goodsSpecs(Long id);

        /**
         * return all id of goods specs.
         *
         * @return
         */
        Set<Long> ids();

        /**
         * return true if goods specs is empty.
         *
         * @return
         */
        Boolean emptySpecs();
    }
}
