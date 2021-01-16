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
    public static final String DQL_FETCH = "FETCH PROP ON";


    /**
     * 遍历查询语句，从点开始遍历图获取点和边的属性，支持过滤（WHERE）、排序（ORDER BY）、及范围（LIMIT）
     *
     */
    public static final String DQL_GO = "GO";

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
    public static final String KW_YIELD = "YIELD";

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
    public static final String KW_EDGE_DIST_REF = "$$";


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
    public static final String KW_EDGE_ATTR_SRC_VID = "_src";

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
    public static final String KW_EDGE_ATTR_DIST_VID = "_dst";


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
    public static final String KW_EDGE_ATTR_TYPE = "_type";


    /**
     * 反向关系 (go from "100" over follow reversely)
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
}
