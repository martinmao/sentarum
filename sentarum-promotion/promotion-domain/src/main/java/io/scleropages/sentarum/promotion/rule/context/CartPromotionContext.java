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
package io.scleropages.sentarum.promotion.rule.context;

import java.util.List;

/**
 * 购物车级别促销，购物车内商品需要根据卖家商业主体（商家、平台、三方等）、或仓储区域进行拆车. 产生一组 {@link OrderPromotionContext}（订单维度的促销结果）.<br>
 * 其所处维度处于整个促销计算过程的顶级. 但在计算执行中使其进行兜底，即计算完所有订单级促销规则后合并计算cart级别优惠.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CartPromotionContext extends PromotionContext {


    /**
     * 返回拆车后订单维度促销结果集.
     *
     * @return
     */
    List<OrderPromotionContext> orderPromotions();
}
