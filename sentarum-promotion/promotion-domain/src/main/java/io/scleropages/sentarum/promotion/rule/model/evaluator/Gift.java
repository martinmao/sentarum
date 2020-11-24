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
package io.scleropages.sentarum.promotion.rule.model.evaluator;

import io.scleropages.sentarum.core.model.primitive.Amount;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * 赠品,统一描述为单规格商品，如存在多规格，做不同 gift 处理.
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Gift {

    /**
     * 赠品唯一标识.
     */
    private Long id;
    /**
     * 商品id
     */
    private Long goodsId;
    /**
     * 商品规格id
     */
    private Long goodsSpecsId;
    /**
     * 商品外部编码
     */
    private String outerGoodsId;
    /**
     * 商品规格外部编码
     */
    private String outerGoodsSpecsId;
    /**
     * 名称
     */
    private String name;
    /**
     * 单用户赠送数量.
     */
    private Integer userNum;
    /**
     * 可赠总数.
     */
    private Integer num;
    /**
     * 补差价，例如：满100元+5元送牙刷
     */
    private Amount adjustFee;
    /**
     * 赠品单价
     */
    private Amount price;

    @Null(groups = Create.class)
    @NotNull(groups = Update.class)
    public Long getId() {
        return id;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getGoodsId() {
        return goodsId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public Long getGoodsSpecsId() {
        return goodsSpecsId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public String getOuterGoodsId() {
        return outerGoodsId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public String getOuterGoodsSpecsId() {
        return outerGoodsSpecsId;
    }

    @NotNull(groups = Create.class)
    @Null(groups = Update.class)
    public String getName() {
        return name;
    }

    @NotNull(groups = Create.class)
    public Integer getUserNum() {
        return userNum;
    }

    @NotNull(groups = Create.class)
    public Integer getNum() {
        return num;
    }

    public Amount getAdjustFee() {
        return adjustFee;
    }

    @NotNull(groups = Create.class)
    public Amount getPrice() {
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public void setGoodsSpecsId(Long goodsSpecsId) {
        this.goodsSpecsId = goodsSpecsId;
    }

    public void setOuterGoodsId(String outerGoodsId) {
        this.outerGoodsId = outerGoodsId;
    }

    public void setOuterGoodsSpecsId(String outerGoodsSpecsId) {
        this.outerGoodsSpecsId = outerGoodsSpecsId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public void setAdjustFee(Amount adjustFee) {
        this.adjustFee = adjustFee;
    }

    public void setPrice(Amount price) {
        this.price = price;
    }


    public interface Create {
    }

    public interface Update {
    }
}
