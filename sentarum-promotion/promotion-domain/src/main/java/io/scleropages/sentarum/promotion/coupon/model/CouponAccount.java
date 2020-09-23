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
package io.scleropages.sentarum.promotion.coupon.model;

/**
 * user account of coupon.
 * <pre>
 *     优惠券核销规则：
 *     1.筛选可用优惠券，有效期、商品范围等维度
 *     2.优先使用抵扣金额最多的优惠券
 *     3.金额相同，优先使用同级优惠券，例如单品优惠券>品牌优惠券>品类优惠券
 *     4.优惠券额度大于实际支付金额时，需要用户确认
 * </pre>
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface CouponAccount {


}
