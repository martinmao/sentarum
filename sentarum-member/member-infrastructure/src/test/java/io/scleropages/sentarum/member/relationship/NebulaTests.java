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
package io.scleropages.sentarum.member.relationship;


import com.vesoft.nebula.Row;
import com.vesoft.nebula.Value;
import com.vesoft.nebula.client.graph.NebulaPoolConfig;
import com.vesoft.nebula.client.graph.data.HostAddress;
import com.vesoft.nebula.client.graph.data.ResultSet;
import com.vesoft.nebula.client.graph.exception.AuthFailedException;
import com.vesoft.nebula.client.graph.exception.IOErrorException;
import com.vesoft.nebula.client.graph.exception.NotValidConnectionException;
import com.vesoft.nebula.client.graph.net.NebulaPool;
import com.vesoft.nebula.client.graph.net.Session;
import org.junit.Assert;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class NebulaTests {

    public static void main(String[] args) throws UnknownHostException, NotValidConnectionException, IOErrorException, AuthFailedException, UnsupportedEncodingException {
        NebulaPoolConfig nebulaPoolConfig = new NebulaPoolConfig();
        nebulaPoolConfig.setMaxConnSize(10);
        List<HostAddress> addresses = Arrays.asList(new HostAddress("127.0.0.1", 9669));
        NebulaPool pool = new NebulaPool();
        pool.init(addresses, nebulaPoolConfig);
        Session session = pool.getSession("root", "nebula", false);
        Assert.assertTrue(session.execute("use nba").isSucceeded());
        String stmt = "go from \"100\" over follow bidirect yield follow._src,$$.player.name, follow._dst, $^.player.name";
        ResultSet rs = session.execute(stmt);
        if (!rs.isSucceeded()) {
            System.out.println(String.format("Execute: `%s', failed: %s",
                    stmt, rs.getErrorMessage()));
            System.exit(1);
        }
        System.out.println(rs.getColumnNames());
        List<Row> rows = rs.getRows();
        rs.rowValues(1).values().forEach(valueWrapper -> valueWrapper.isBoolean());
        rows.forEach(row -> {
            List<Value> values = row.getValues();
            values.forEach(value -> {

                System.out.print(new String(value.getSVal()) + " ");
            });
            System.out.println("\n-------------------------------");
        });
        session.release();
        pool.close();
    }
}
