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

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.DefaultAlipayClient.Builder;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/**
 *
 *
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class AlipaySupport implements InitializingBean {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String DEFAULT_GATEWAY = "https://openapi.alipay.com/gateway.do";

	private static final String DEV_GATEWAY = "https://openapi.alipaydev.com/gateway.do";

	private SecurityConfigSource securityConfigSource;

	private Resource localPrivateKey;

	private Resource alipayPublicKey;

	private String payLoadSecretKey;

	private String appId;

	private boolean useSandbox;

	private String gateway;

	private String signType = AlipayConstants.SIGN_TYPE_RSA2;

	private String proxyHost;
	private int proxyPort;

	private int connectTimeout;
	private int readTimeout;

	protected AlipayClient createNativeAlipayClient() {
		Builder builder = DefaultAlipayClient.builder(getGateway(), getAppId(), getLocalPrivateKey());
		builder.alipayPublicKey(getAlipayPublicKey());
		builder.signType(getSignType());
		builder.format(AlipayConstants.FORMAT_JSON);
		if (null != getPayLoadSecretKey()) {
			builder.encryptType(AlipayConstants.ENCRYPT_TYPE_AES);
			builder.encryptKey(getPayLoadSecretKey());
		}
		builder.charset(AlipayConstants.CHARSET_UTF8);
		if (null != proxyHost && 0 != proxyPort) {
			builder.proxyHost(proxyHost);
			builder.proxyPort(proxyPort);
		}
		if (0 != connectTimeout)
			builder.connectTimeout(connectTimeout);
		if (0 != readTimeout)
			builder.readTimeout(readTimeout);
		return builder.build();
	}

	public void signVerify(Map<String, String> params, String sign, String charset, String signType) {
		Assert.notEmpty(params, "missing required content params.");
		Assert.hasText(sign, "sign is required.");
		Assert.hasText(charset, "charset is required.");
		Assert.hasText(signType, "signType is required.");
		if (!(params instanceof TreeMap)) {
			TreeMap<String, String> treeMap = Maps.newTreeMap();
			for (Entry<String, String> paramEntry : params.entrySet()) {
				treeMap.put(paramEntry.getKey(), paramEntry.getValue());
			}
			params = treeMap;
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, String> paramEntry : params.entrySet()) {
			String key = paramEntry.getKey();
			String value = paramEntry.getValue();
			sb.append(key + "=" + value + "&");
		}
		try {
			String content = StringUtils.removeEnd(sb.toString(), "&");
			logger.debug("Sign check:\n\tsignType: [{}]\n\tsign: [{}]\n\tcontent: [{}]", signType, sign, content);
			Assert.isTrue(
					AlipaySignature.rsaCheck(content, sign, securityConfigSource.alipayPublicKey(), charset, signType),
					"sign check failure.");
			if (logger.isDebugEnabled())
				logger.debug("Sign check successfully.");
		} catch (AlipayApiException e) {
			throw new IllegalArgumentException("invalid sign: " + e.getMessage(), e);
		}
	}

	@Override
	public void afterPropertiesSet() throws Exception {

		logger.info("Initializing alipay from [{}]", null != securityConfigSource ? securityConfigSource.getClass()
				: "local configure resource(***** un-safety ******)");

		if (null == securityConfigSource) {
			securityConfigSource = createDefaultSecurityConfigSource();
		}
	}

	protected SecurityConfigSource createDefaultSecurityConfigSource() {

		Assert.notNull(localPrivateKey, "localPrivateKey not configure.");
		Assert.notNull(alipayPublicKey, "alipayPublicKey not configure.");
		Assert.hasText(appId, "appId not configure.");
		final String _payLoadSecretKey = payLoadSecretKey;
		final String _localPrivateKey = readResourceText(localPrivateKey);
		final String _alipayPublicKey = readResourceText(alipayPublicKey);
		final String _appId = appId;

		return new SecurityConfigSource() {

			@Override
			public String payLoadSecretKey() {
				return _payLoadSecretKey;
			}

			@Override
			public String localPrivateKey() {
				return _localPrivateKey;
			}

			@Override
			public String appId() {
				return _appId;
			}

			@Override
			public String alipayPublicKey() {
				return _alipayPublicKey;
			}
		};
	}

	protected String readResourceText(Resource resource) {
		logger.info("Loading resource from: " + resource);
		Assert.notNull(resource, "input resource must not be null.");
		InputStream in = null;
		try {
			in = resource.getInputStream();
			return IOUtils.toString(in, "utf-8");
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	private final String getSignType() {
		return signType;
	}

	public String getAppId() {
		return appId;
	}

	public String getGateway() {
		return null != gateway ? gateway : (useSandbox ? DEV_GATEWAY : DEFAULT_GATEWAY);
	}

	private final String getLocalPrivateKey() {
		return securityConfigSource.localPrivateKey();
	}

	private final String getAlipayPublicKey() {
		return securityConfigSource.alipayPublicKey();
	}

	private final String getPayLoadSecretKey() {
		return payLoadSecretKey;
	}

	public void setSecurityConfigSource(SecurityConfigSource securityConfigSource) {
		this.securityConfigSource = securityConfigSource;
	}

	public void setAlipayPublicKey(Resource alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public void setLocalPrivateKey(Resource localPrivateKey) {
		this.localPrivateKey = localPrivateKey;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public void setPayLoadSecretKey(String payLoadSecretKey) {
		this.payLoadSecretKey = payLoadSecretKey;
	}

	public void setUseSandbox(boolean useSandbox) {
		this.useSandbox = useSandbox;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		this.readTimeout = readTimeout;
	}
}
