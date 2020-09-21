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
package io.scleropages.sentarum.core.model.primitive;

import org.springframework.util.Assert;

import java.util.Optional;

/**
 * domain primitive of tel
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class Tel {

    private final String tel;

    public Tel(String tel) {
        Assert.isTrue(Tel.isValid(tel), () -> "invalid tel format: " + tel);
        this.tel = tel;
    }

    /**
     * return tel text.
     *
     * @return
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * return area code if present.
     *
     * @return
     */
    public Optional<String> getAreaCode() {
        return getAreaCode(this.tel);
    }

    /**
     * return true if given tel is valid.
     *
     * @param tel
     * @return
     */
    public static boolean isValid(String tel) {
        Assert.hasText(tel, "tel must not empty.");
        String pattern = "^0?[1-9]{2,3}-?\\d{8}$";
        return tel.matches(pattern);
    }

    /**
     * return area code from tel prefix.
     *
     * @param tel
     * @return
     */
    public static Optional<String> getAreaCode(String tel) {
        for (int i = 0; i < tel.length(); i++) {
            String prefix = tel.substring(0, i);
            if (BaseAddresses.isAreaCode(prefix)) {
                return Optional.of(prefix);
            }
        }
        return Optional.empty();
    }

}
