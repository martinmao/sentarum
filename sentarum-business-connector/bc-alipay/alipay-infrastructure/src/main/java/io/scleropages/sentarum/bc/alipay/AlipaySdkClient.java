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

import com.alipay.api.AlipayObject;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradeFastpayRefundQueryModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeRefundModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class AlipaySdkClient implements AlipayRepository {

    private AlipayClientTemplate clientTemplate;

    @Override
    public AlipayTradeCreateResponse create(AlipayTradeCreateRequest request, AlipayTradeCreateModel model) {
        return executeInternal(request, model);
    }

    @Override
    public AlipayTradeWapPayResponse wapPay(AlipayTradeWapPayRequest request, AlipayTradeWapPayModel model) {
        return pageExecuteInternal(request, model);
    }

    @Override
    public AlipayTradePagePayResponse pagePay(AlipayTradePagePayRequest request, AlipayTradePagePayModel model) {
        return pageExecuteInternal(request, model);
    }

    @Override
    public AlipaySystemOauthTokenResponse authToken(AlipaySystemOauthTokenRequest request) {
        return executeInternal(request, null);
    }

    @Override
    public AlipayTradeRefundResponse refund(AlipayTradeRefundRequest request, AlipayTradeRefundModel model) {
        return executeInternal(request, model);
    }

    @Override
    public AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryRequest request,
                                                             AlipayTradeFastpayRefundQueryModel model) {
        return executeInternal(request, model);
    }

    @Override
    public AlipayTradeCloseResponse close(AlipayTradeCloseRequest request, AlipayTradeCloseModel model) {
        return executeInternal(request, model);
    }

    @Override
    public AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryRequest request, AlipayTradeQueryModel model) {
        return executeInternal(request, model);
    }

    public <T extends AlipayResponse> T executeInternal(AlipayRequest<T> request, AlipayObject model) {
        Assert.notNull(request, "alipay request must not be null.");
        setBizModelIfNecessary(request, model);
        return clientTemplate.execute(request);
    }

    public <T extends AlipayResponse> T pageExecuteInternal(AlipayRequest<T> request, AlipayObject model) {
        Assert.notNull(request, "alipay request must not be null.");
        setBizModelIfNecessary(request, model);
        return clientTemplate.pageExecute(request);
    }

    protected void setBizModelIfNecessary(AlipayRequest<?> request, AlipayObject model) {
        if (null != model && null == request.getBizModel()) {
            request.setBizModel(model);
        }
    }

    @Autowired
    public void setClientTemplate(AlipayClientTemplate clientTemplate) {
        this.clientTemplate = clientTemplate;
    }
}
