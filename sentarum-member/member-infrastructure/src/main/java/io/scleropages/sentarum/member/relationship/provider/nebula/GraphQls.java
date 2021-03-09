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
package io.scleropages.sentarum.member.relationship.provider.nebula;

import org.springframework.util.Assert;

import java.util.List;

/**
 * utility class for nQLS(nebula graph query language.)
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class GraphQls {


    /**
     * 获取点或边的属性
     * <pre>
     * FETCH PROP ON &lt;TAG1,TAG2...| * &gt; &lt;VID1,VID2...&gt; [YIELD [DISTINCT] TAG1.ATTR,TAG2.ATTR....]
     *
     * FETCH PROP ON player "100"
     * +----------+--------------+------------+
     * | VertexID | player.name  | player.age |
     * +----------+--------------+------------+
     * | "100"    | "Tim Duncan" | 42         |
     * +----------+--------------+------------+
     *
     * FETCH PROP ON * "101","102"
     * +----------+---------------------+------------+-----------+
     * | VertexID | player.name         | player.age | team.name |
     * +----------+---------------------+------------+-----------+
     * | "102"    | "LaMarcus Aldridge" | 33         |           |
     * +----------+---------------------+------------+-----------+
     * | "101"    | "Tony Parker"       | 36         |           |
     * +----------+---------------------+------------+-----------+
     *
     * FETCH PROP ON player "100","101","102" YIELD DISTINCT player.name, player.age
     * +----------+---------------------+------------+
     * | VertexID | player.name         | player.age |
     * +----------+---------------------+------------+
     * | "100"    | "Tim Duncan"        | 42         |
     * +----------+---------------------+------------+
     * | "102"    | "LaMarcus Aldridge" | 33         |
     * +----------+---------------------+------------+
     * | "101"    | "Tony Parker"       | 36         |
     * +----------+---------------------+------------+
     *
     *
     * FETCH PROP ON &lt;EDGE&gt; &lt;VID1->VID2,VID2->VID3...&gt;[@RANK] [YIELD [DISTINCT] TAG1.ATTR,TAG2.ATTR....]
     *
     * FETCH PROP ON follow "100"->"101","101"->"102"@0   #默认rank为0
     * +-------------+-------------+--------------+---------------+
     * | follow._src | follow._dst | follow._rank | follow.degree |
     * +-------------+-------------+--------------+---------------+
     * | "100"       | "101"       | 0            | 95            |
     * +-------------+-------------+--------------+---------------+
     * | "101"       | "102"       | 0            | 91            |
     * +-------------+-------------+--------------+---------------+
     * </pre>
     */
    public static final String DQL_FETCH = "fetch prop on";


    /**
     * 遍历查询语句，从点开始遍历图获取点和边的属性，支持过滤（WHERE）、排序（ORDER BY）、及范围（LIMIT）
     *
     * <pre>
     *
     * GO [&lt;M&gt;[TO &lt;N&gt;] STEPS] FROM &lt;TAG1,TAG2...&gt; OVER &lt;EDGE1,EDGE2...&gt; [REVERSELY反向遍历] [BIDIRECT双向遍历] [WHERE &lt;expression&gt;] YIELD [DISTINCT] &lt;TAG1.ATTR,TAG2.ATTR....&gt;
     * [M,N] 可选，指定跳的区间，n必须大于m
     *
     * 二度好友：GO 2 STEPS FROM "100" OVER follow WHERE follow.degree > 10 YIELD follow._dst, $$.player.name, follow.degree, follow._src, $^.player.name
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | follow._dst | $$.player.name      | follow.degree | follow._src | $^.player.name      |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | "101"       | "Tony Parker"       | 75            | "102"       | "LaMarcus Aldridge" |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | "100"       | "Tim Duncan"        | 95            | "101"       | "Tony Parker"       |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | "102"       | "LaMarcus Aldridge" | 91            | "101"       | "Tony Parker"       |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | "100"       | "Tim Duncan"        | 88            | "106"       | "Kyle Anderson"     |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * | "107"       | "Aron Baynes"       | 81            | "106"       | "Kyle Anderson"     |
     * +-------------+---------------------+---------------+-------------+---------------------+
     * </pre>
     */
    public static final String DQL_GO = "go";

    /**
     * YIELD 关键词用于指定返回字段，可以在 FETCH、GO 语句中作为子句使用，也可以在 PIPE(|) 中作为独立的语句使用，同时可以作为用于计算的单句使用
     * <pre>
     * (yield 1==1)
     * +--------+
     * | (1==1) |
     * +--------+
     * | true   |
     * +--------+
     *
     * YIELD "100" AS id | FETCH PROP ON player $-.id
     * +----------+--------------+------------+
     * | VertexID | player.name  | player.age |
     * +----------+--------------+------------+
     * | "100"    | "Tim Duncan" | 42         |
     * +----------+--------------+------------+
     * </pre>
     */
    public static final String KW_YIELD = "yield";

    /**
     * 管道用于连接两个语句与sql子查询类似(但写法上非嵌套结构)
     */
    public static final String KW_PIPE = "|";

    /**
     * 管道输入 (go from "100" over follow yield follow._dst as dstid, $$.player.name as dname | go from $-.dstid over follow yield follow._dst, follow.degree, $-.dname)
     * <pre>
     * +-------------+---------------+---------------------+
     * | follow._dst | follow.degree | $-.dname            |
     * +-------------+---------------+---------------------+
     * | "101"       | 75            | "LaMarcus Aldridge" |
     * +-------------+---------------+---------------------+
     * | "100"       | 95            | "Tony Parker"       |
     * +-------------+---------------+---------------------+
     * | "102"       | 91            | "Tony Parker"       |
     * +-------------+---------------+---------------------+
     * | "100"       | 88            | "Kyle Anderson"     |
     * +-------------+---------------+---------------------+
     * | "107"       | 81            | "Kyle Anderson"     |
     * +-------------+---------------+---------------------+
     * </pre>
     */
    public static final String KW_PIPE_INPUT = "$-";


    /**
     * 属性引用,边上的起始点 (go from "100" over follow yield follow._src as s_vid, $^.player.name as s_name, follow._dst as d_vid, $$.player.name as d_name)
     * <pre>
     * +-------+--------------+-------+---------------------+
     * | s_vid | s_name       | d_vid | d_name              |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "101" | "Tony Parker"       |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "102" | "LaMarcus Aldridge" |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "106" | "Kyle Anderson"     |
     * +-------+--------------+-------+---------------------+
     * </pre>
     */
    public static final String KW_EDGE_SRC_REF = "$^";

    /**
     * 属性引用,边上的目标点 (go from "100" over follow yield follow._src as s_vid, $^.player.name as s_name, follow._dst as d_vid, $$.player.name as d_name)
     * <pre>
     * +-------+--------------+-------+---------------------+
     * | s_vid | s_name       | d_vid | d_name              |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "101" | "Tony Parker"       |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "102" | "LaMarcus Aldridge" |
     * +-------+--------------+-------+---------------------+
     * | "100" | "Tim Duncan" | "106" | "Kyle Anderson"     |
     * +-------+--------------+-------+---------------------+
     * </pre>
     */
    public static final String KW_EDGE_DST_REF = "$$";


    /**
     * 边的内置属性，边上的起始点vid (go from "100" over follow yield follow._src, follow._dst, follow._type, follow._rank)
     * <pre>
     * +-------------+-------------+--------------+--------------+
     * | follow._src | follow._dst | follow._type | follow._rank |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "101"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "102"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "106"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * </pre>
     */
    public static final String KW_EDGE_FIELD_SRC_VID = "_src";

    /**
     * 边的内置属性，边上的目标点vid (go from "100" over follow yield follow._src, follow._dst, follow._type, follow._rank)
     * <pre>
     * +-------------+-------------+--------------+--------------+
     * | follow._src | follow._dst | follow._type | follow._rank |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "101"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "102"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "106"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * </pre>
     */
    public static final String KW_EDGE_FIELD_DST_VID = "_dst";


    /**
     * 边的内置属性，边的类型 (go from "100" over follow yield follow._src, follow._dst, follow._type, follow._rank)
     * <pre>
     * +-------------+-------------+--------------+--------------+
     * | follow._src | follow._dst | follow._type | follow._rank |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "101"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "102"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "106"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * </pre>
     */
    public static final String KW_EDGE_FIELD_TYPE = "_type";


    /**
     * 边的内置属性，边的类型 (go from "100" over follow yield follow._src, follow._dst, follow._type, follow._rank)
     * <pre>
     * +-------------+-------------+--------------+--------------+
     * | follow._src | follow._dst | follow._type | follow._rank |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "101"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "102"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * | "100"       | "106"       | 13           | 0            |
     * +-------------+-------------+--------------+--------------+
     * </pre>
     */
    public static final String KW_EDGE_FIELD_RANK = "_rank";


    /**
     * 反向遍历 (go from "100" over follow reversely)，关注我的
     * <pre>
     * +-------------+
     * | follow._dst |
     * +-------------+
     * | "101"       |
     * +-------------+
     * | "106"       |
     * +-------------+
     * </pre>
     */
    public static final String KW_REVERSELY = "reversely";

    /**
     * 双向遍历 (go from "100" over follow bidirect yield follow._src,$$.player.name, follow._dst, $^.player.name),我关注的及关注我的
     * <pre>
     * +-------------+---------------------+-------------+----------------+
     * | follow._src | $$.player.name      | follow._dst | $^.player.name |
     * +-------------+---------------------+-------------+----------------+
     * | "100"       | "Tony Parker"       | "101"       | "Tim Duncan"   |
     * +-------------+---------------------+-------------+----------------+
     * | "100"       | "LaMarcus Aldridge" | "102"       | "Tim Duncan"   |
     * +-------------+---------------------+-------------+----------------+
     * | "100"       | "Kyle Anderson"     | "106"       | "Tim Duncan"   |
     * +-------------+---------------------+-------------+----------------+
     * | "100"       | "Tony Parker"       | "101"       | "Tim Duncan"   |
     * +-------------+---------------------+-------------+----------------+
     * | "100"       | "Kyle Anderson"     | "106"       | "Tim Duncan"   |
     * +-------------+---------------------+-------------+----------------+
     * </pre>
     */
    public static final String KW_BIDIRECT = "bidirect";


    /**
     * 分页 (go from "200" over serve reversely yield serve._dst as vid, $$.player.name as name, $$.player.age as age  | limit 0,10)
     * <pre>
     * +-------+-----------------+-----+
     * | vid   | name            | age |
     * +-------+-----------------+-----+
     * | "106" | "Kyle Anderson" | 25  |
     * +-------+-----------------+-----+
     * | "107" | "Aron Baynes"   | 32  |
     * +-------+-----------------+-----+
     * </pre>
     */
    public static final String KW_LIMIT = "limit";


    /**
     * 排序 (go from "200" over serve reversely yield serve._dst as vid, $$.player.name as name, $$.player.age as age | order by age,name | limit 10)
     * <pre>
     * +---------------------+-----+
     * | friend              | age |
     * +---------------------+-----+
     * | "Kyle Anderson"     | 25  |
     * +---------------------+-----+
     * | "Aron Baynes"       | 32  |
     * +---------------------+-----+
     * | "LaMarcus Aldridge" | 33  |
     * +---------------------+-----+
     * | "Tony Parker"       | 36  |
     * +---------------------+-----+
     * | "Tim Duncan"        | 42  |
     * +---------------------+-----+
     * </pre>
     */
    public static final String KW_ORDER = "order";


    /**
     * 申明变量 （$var = go from "100" over follow yield follow._dst as id; go from $var.id over serve;）
     * <pre>
     * +------------+
     * | serve._dst |
     * +------------+
     * | "200"      |
     * +------------+
     * | "200"      |
     * +------------+
     * | "200"      |
     * +------------+
     * </pre>
     */
    public static final String KW_VAR = "$var";

    /**
     * 表达式中包含指定字符 (go from "107" over serve where $$.team.name contains 'riors' yield $^.player.name, serve.start_year, serve.end_year, $$.team.name)
     * <pre>
     * +----------------+------------------+----------------+--------------+
     * | $^.player.name | serve.start_year | serve.end_year | $$.team.name |
     * +----------------+------------------+----------------+--------------+
     * | "Aron Baynes"  | 2001             | 2009           | "Warriors"   |
     * +----------------+------------------+----------------+--------------+
     * </pre>
     */
    public static final String FUNC_CONTAINS = "contains";

    /**
     * 哈希（murmurhash2，10亿边存在碰撞概率）计算 (yield hash("hello world!!!"))
     * <pre>
     * +----------------------+
     * | hash(hello world!!!) |
     * +----------------------+
     * | -349643436452247981  |
     * +----------------------+
     * </pre>
     */
    public static final String FUNC_HASH = "hash";

    /**
     * 当前时间 (yield now())
     * +------------+
     * | now()      |
     * +------------+
     * | 1610605190 |
     * +------------+
     */
    public static final String FUNC_NOW = "now";

    /**
     * 生成全局唯一的标识符(Murmurhash2 与当前时间戳（秒）组合而成，避免碰撞)，未来可能存在兼容性问题
     */
    public static final String FUNC_UUID = "uuid";

    /**
     * 边上的系统保留字段.
     */
    public static final StdEdgeFieldBuilder FIELD_EDGE_STD = new StdEdgeFieldBuilder();
    /**
     * 边上的自定义字段.
     */
    public static final EdgeFieldBuilder FIELD_EDGE = new EdgeFieldBuilder();
    /**
     * 变量输入或管道输入字段.
     */
    public static final VarFieldBuilder FIELD_VAR = new VarFieldBuilder();


    public static final Field fieldTag(String tag, String field) {
        return new FieldImpl(FieldImpl.TAG_FIELD, tag, field);
    }

    public static final class StdEdgeFieldBuilder {
        private StdEdgeFieldBuilder() {
        }

        public final Field src(String edge) {
            return new FieldImpl(FieldImpl.EDGE_FIELD, edge, KW_EDGE_FIELD_SRC_VID);
        }

        public final Field dst(String edge) {
            return new FieldImpl(FieldImpl.EDGE_FIELD, edge, KW_EDGE_FIELD_DST_VID);
        }

        public final Field type(String edge) {
            return new FieldImpl(FieldImpl.EDGE_FIELD, edge, KW_EDGE_FIELD_TYPE);
        }

        public final Field rank(String edge) {
            return new FieldImpl(FieldImpl.EDGE_FIELD, edge, KW_EDGE_FIELD_RANK);
        }

        public final Field src(String tag, String field) {
            return new FieldImpl(FieldImpl.TAG_FIELD, KW_EDGE_SRC_REF + "." + tag, field);
        }

        public final Field dst(String tag, String field) {
            return new FieldImpl(FieldImpl.TAG_FIELD, KW_EDGE_DST_REF + "." + tag, field);
        }
    }

    public static final class EdgeFieldBuilder {
        private EdgeFieldBuilder() {
        }

        public final Field field(String edge, String field) {
            return new FieldImpl(FieldImpl.EDGE_FIELD, edge, field);
        }
    }

    public static final class VarFieldBuilder {
        private VarFieldBuilder() {
        }

        public final Field pipe(String field) {
            return new FieldImpl(FieldImpl.VAR_FIELD, KW_PIPE_INPUT, field);
        }

        public final Field var(String field) {
            return new FieldImpl(FieldImpl.VAR_FIELD, KW_VAR, field);
        }
    }


    /**
     * 创建 go 遍历. {@link #DQL_GO}
     *
     * @param m 起跳点
     * @param n 终点
     * @return
     */
    public static final GoStep go(int m, int n) {
        return new GoImpl(m, n);
    }

    public static final GoStep go() {
        return new GoImpl();
    }

    public interface GoStep {
        FromStep from(String... tags);
    }

    public interface FromStep {
        OverStep over(String... edges);
    }

    public interface OverStep {

        WhereStep reversely();

        WhereStep bidirect();

        WhereStep where(Condition... condition);

        WhereStep where(String expression);

        WhereStep where(List<Condition> conditions);
    }


    public interface WhereStep extends Condition {
    }

    public interface Field {

        /**
         * this == given
         *
         * @param field
         * @return
         */
        Condition eq(Field field);

        /**
         * this == given
         *
         * @param expression
         * @return
         */
        Condition eq(String expression);

        /**
         * this != given
         *
         * @param field
         * @return
         */
        Condition ne(Field field);

        /**
         * this != given
         *
         * @param expression
         * @return
         */
        Condition ne(String expression);

        /**
         * this < given
         *
         * @param field
         * @return
         */
        Condition lt(Field field);

        /**
         * this < given
         *
         * @param expression
         * @return
         */
        Condition lt(String expression);

        /**
         * this <= given.
         *
         * @param field
         * @return
         */
        Condition lte(Field field);

        /**
         * this <= given
         *
         * @param expression
         * @return
         */
        Condition lte(String expression);


        /**
         * this > given
         *
         * @param field
         * @return
         */
        Condition gt(Field field);

        /**
         * this > given
         *
         * @param expression
         * @return
         */
        Condition gt(String expression);

        /**
         * this >= given.
         *
         * @param field
         * @return
         */
        Condition gte(Field field);

        /**
         * this >= given
         *
         * @param expression
         * @return
         */
        Condition gte(String expression);

        /**
         * this is null.
         *
         * @return
         */
        Condition isNull();


        /**
         * this is not null.
         *
         * @return
         */
        Condition isNotNull();

        /**
         * this like given
         *
         * @param field
         * @return
         */
        Condition like(Field field);

        /**
         * this like given.
         *
         * @param expression
         * @return
         */
        Condition like(String expression);

        /**
         * this in given
         *
         * @param field
         * @return
         */
        Condition in(Field field);


        /**
         * this in given
         *
         * @param expression
         * @return
         */
        Condition in(String expression);

        /**
         * this not in given.
         *
         * @param field
         * @return
         */
        Condition notIn(Field field);

        /**
         * this not in given.
         *
         * @param expression
         * @return
         */
        Condition notIn(String expression);
    }

    public interface Condition {

        Condition and(Condition condition);

        Condition and(String expression);

        Condition or(Condition condition);

        Condition or(String expression);
    }

    private static final class FieldImpl implements Field {

        private static final int EDGE_FIELD = 0;
        private static final int TAG_FIELD = 1;
        private static final int VAR_FIELD = 2;

        private final int typeId;
        private final String typeName;
        private final String fieldName;

        private FieldImpl(int typeId, String typeName, String fieldName) {
            Assert.isTrue(typeId == EDGE_FIELD || typeId == TAG_FIELD || typeId == VAR_FIELD, "not valid typeId.");
            this.typeId = typeId;
            this.typeName = typeName;
            this.fieldName = fieldName;
        }

        @Override
        public Condition eq(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.EQ, this, field);
        }

        @Override
        public Condition eq(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.EQ, this, expression);
        }

        @Override
        public Condition ne(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.NE, this, field);
        }

        @Override
        public Condition ne(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.NE, this, expression);
        }

        @Override
        public Condition lt(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.LT, this, field);
        }

        @Override
        public Condition lt(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.LT, this, expression);
        }

        @Override
        public Condition lte(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.LTE, this, field);
        }

        @Override
        public Condition lte(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.LTE, this, expression);
        }

        @Override
        public Condition gt(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.GT, this, field);
        }

        @Override
        public Condition gt(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.GT, this, expression);
        }

        @Override
        public Condition gte(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.GTE, this, field);
        }

        @Override
        public Condition gte(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.GTE, this, expression);
        }

        @Override
        public Condition isNull() {
            return new ConditionImpl(ConditionImpl.Operator.NULL, this);
        }

        @Override
        public Condition isNotNull() {
            return new ConditionImpl(ConditionImpl.Operator.NOT_NULL, this);
        }

        @Override
        public Condition like(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.LIKE, this, field);
        }

        @Override
        public Condition like(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.LIKE, this, expression);
        }

        @Override
        public Condition in(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.IN, this, field);
        }

        @Override
        public Condition in(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.IN, this, expression);
        }

        @Override
        public Condition notIn(Field field) {
            return new ConditionImpl(ConditionImpl.Operator.NOT_IN, this, field);
        }

        @Override
        public Condition notIn(String expression) {
            return new ConditionImpl(ConditionImpl.Operator.NOT_IN, this, expression);
        }
    }

    private static final class ConditionImpl implements Condition {

        enum Operator {
            EQ, NE, LIKE, GT, LT, GTE, LTE, NULL, NOT_NULL, IN, NOT_IN
        }

        enum Conjunction {
            AND, OR
        }

        private final Operator operator;
        private final Field source;
        private final Field targetField;
        private final String targetExpression;

        private Conjunction conjunction;

        public ConditionImpl(Operator operator, Field source, Field targetField, String targetExpression) {
            this.operator = operator;
            this.source = source;
            this.targetField = targetField;
            this.targetExpression = targetExpression;
        }

        public ConditionImpl(Operator operator, Field source, Field targetField) {
            this(operator, source, targetField, null);
        }

        public ConditionImpl(Operator operator, Field source, String targetExpression) {
            this(operator, source, null, targetExpression);
        }

        public ConditionImpl(Operator operator, Field source) {
            this(operator, source, null, null);
        }


        @Override
        public Condition and(Condition condition) {
            return null;
        }

        @Override
        public Condition and(String expression) {
            return null;
        }

        @Override
        public Condition or(Condition condition) {
            return null;
        }

        @Override
        public Condition or(String expression) {
            return null;
        }
    }


    public static final class GoImpl implements GoStep, FromStep, OverStep, WhereStep {

        private static final int STEP_ONE = 1;
        private int m = STEP_ONE;
        private int n = STEP_ONE;
        private String[] tags;
        private String[] edges;
        private boolean reversely;
        private boolean bidirect;

        public GoImpl(int m, int n) {
            Assert.isTrue(m > 0, "m must more than zero.");
            Assert.isTrue(n > 0, "n must more than zero.");
            this.m = m;
            this.n = n;
        }

        public GoImpl() {
        }

        public FromStep from(String... tags) {
            this.tags = tags;
            return this;
        }

        public OverStep over(String... edges) {
            this.edges = edges;
            return this;
        }

        public WhereStep reversely() {
            this.reversely = true;
            return this;
        }

        public WhereStep bidirect() {
            this.bidirect = true;
            return this;
        }

        @Override
        public WhereStep where(Condition... condition) {
            return null;
        }

        @Override
        public WhereStep where(String expression) {
            return null;
        }

        @Override
        public WhereStep where(List<Condition> conditions) {
            return null;
        }

        @Override
        public Condition and(Condition condition) {
            return null;
        }

        @Override
        public Condition and(String expression) {
            return null;
        }

        @Override
        public Condition or(Condition condition) {
            return null;
        }

        @Override
        public Condition or(String expression) {
            return null;
        }
    }
}
