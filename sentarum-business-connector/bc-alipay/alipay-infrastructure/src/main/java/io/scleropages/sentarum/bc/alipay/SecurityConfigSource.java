/**
 * 
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.scleropages.sentarum.bc.alipay;

import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 
 * alipay 安全配置扩展接口，实现改接口从其他安全源获取配置信息
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface SecurityConfigSource {

	/**
	 * 本地私钥，java默认pkcs8格式 {@link PKCS8EncodedKeySpec}，经base64编码的文本串
	 * 
	 * @return
	 */
	String localPrivateKey();

	/**
	 * alipay 网关公钥，java默认x509格式 {@link X509EncodedKeySpec}，经base64编码的文本串
	 * 
	 * @return
	 */
	String alipayPublicKey();

	/**
	 * 应用appId
	 * 
	 * @return
	 */
	String appId();

	/**
	 * 报文加密秘钥（AES/CBC/PKCS5Padding），经base64编码的文本串，可选(网关支持使用对称加密算法对消息报文进行加密)
	 * 
	 * @return
	 */
	String payLoadSecretKey();

}
