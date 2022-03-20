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
package io.scleropages.sentarum.bc.alipay;

import com.google.common.collect.Maps;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;

/**
 *
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public abstract class AlipayMessages {

    public static AlipayMessage buildMessageFromRequest(HttpServletRequest request) {
        Map<String, String> params = Maps.newHashMap();
        Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, request.getParameter(name));
        }
        return new AlipayMessage(params);
    }

    public static void signVerify(HttpServletRequest request) {
        Map<String, String> params = Maps.newTreeMap();
        Enumeration<String> names = request.getParameterNames();
        String sign = null;
        String signType = null;
        String charset = null;
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if ("sign".equals(name))
                sign = request.getParameter(name);
            else if ("sign_type".equals(name))
                signType = request.getParameter(name);
            else {
                if ("charset".equals(name))
                    charset = request.getParameter(name);
                params.put(name, request.getParameter(name));
            }
        }
        clientTemplate.signVerify(params, sign, charset, signType);
    }

    private volatile static AlipayClientTemplate clientTemplate;

    public static void setClientTemplate(final AlipayClientTemplate clientTemplate) {
        // if (null != AlipayMessages.clientTemplete)
        // throw new IllegalArgumentException("not allowed set multiple
        // times.");
        AlipayMessages.clientTemplate = clientTemplate;
    }
}
