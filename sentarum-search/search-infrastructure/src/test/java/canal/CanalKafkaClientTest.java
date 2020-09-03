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
package canal;

import com.alibaba.otter.canal.client.kafka.KafkaCanalConnector;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class CanalKafkaClientTest {

    public static void main(String[] args) {
        KafkaCanalConnector kafkaCanalConnector = new KafkaCanalConnector("mycentos01:2181,mycentos02:2181,mycentos03:2181", "test2", 0, null, null, false);
    }
}
