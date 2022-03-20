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
package io.scleropages.sentarum.boot.bc.alipay.tests;

import com.alipay.api.domain.AlipayTradeCreateModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayTradeCreateRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayTradeCreateResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import io.scleropages.sentarum.bc.alipay.AlipayMessage;
import io.scleropages.sentarum.bc.alipay.AlipayRepository;
import org.scleropages.core.concurrent.GuavaFutures;
import org.scleropages.core.mapper.JsonMapper2;
import org.scleropages.core.util.Encodes;
import org.scleropages.crud.web.Servlets;
import org.scleropages.crud.web.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
@Controller
@RequestMapping("alipay")
public class AlipayAction {

    private final GuavaFutures.IdObservable<String, AlipayMessage> pendingOrders = GuavaFutures
            .newIdObservable(CacheBuilder.newBuilder().maximumSize(10000).expireAfterWrite(1, TimeUnit.MINUTES));

    private AlipayRepository alipayRepository;

    @RequestMapping("page_pay")
    public void payPage(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(Servlets.fromCurrentRequestUrl(httpRequest, "notify").toString());
        request.setReturnUrl(Servlets.fromCurrentRequestUrl(httpRequest, "return").toString());

        AlipayTradePagePayModel model = new AlipayTradePagePayModel();
        String orderId = System.currentTimeMillis() + "";

        model.setOutTradeNo(orderId);
        model.setTotalAmount("0.11");
        model.setSubject("送钱");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        model.setBody("让你送钱");

        Map<String, Object> passbackParams = Maps.newHashMap();
        passbackParams.put("attr1", 1);
        passbackParams.put("attr2", "2");
        model.setPassbackParams(Encodes.urlEncode(passbackParams));
        AlipayTradePagePayResponse response = alipayRepository.pagePay(request, model);

        pendingOrders.subscribe(orderId);
        System.out.println(response.getBody());
        Views.renderHtml(httpResponse, response.getBody());
    }

    @RequestMapping("wap_pay")
    public void wapPay(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
        request.setNotifyUrl(Servlets.fromCurrentRequestUrl(httpRequest, "notify").toString());
        request.setReturnUrl(Servlets.fromCurrentRequestUrl(httpRequest, "return").toString());

        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
        String orderId = System.currentTimeMillis() + "";
        model.setOutTradeNo(orderId);
        model.setTotalAmount("0.11");
        model.setSubject("送钱");
        model.setProductCode("QUICK_WAP_PAY");
        Map<String, Object> passbackParams = Maps.newHashMap();
        passbackParams.put("attr1", 1);
        passbackParams.put("attr2", "2");
        model.setPassbackParams(Encodes.urlEncode(passbackParams));
        AlipayTradeWapPayResponse response = alipayRepository.wapPay(request, model);

        pendingOrders.subscribe(orderId);
        System.out.println(response.getBody());
        Views.renderHtml(httpResponse, response.getBody());
    }

    @RequestMapping("create")
    @ResponseBody
    public AlipayTradeCreateResponse create(HttpServletRequest httpRequest) {
        AlipayTradeCreateRequest request = new AlipayTradeCreateRequest();
        request.setNotifyUrl(Servlets.fromCurrentRequestUrl(httpRequest, "notify").toString());
        request.setReturnUrl(Servlets.fromCurrentRequestUrl(httpRequest, "return").toString());

        AlipayTradeCreateModel model = new AlipayTradeCreateModel();
        String orderId = System.currentTimeMillis() + "";
        model.setOutTradeNo(orderId);
        model.setTotalAmount("0.11");
        model.setSubject("送钱");
        model.setProductCode("QUICK_WAP_PAY");
        Map<String, Object> passbackParams = Maps.newHashMap();
        passbackParams.put("attr1", 1);
        passbackParams.put("attr2", "2");
        model.setPassbackParams(Encodes.urlEncode(passbackParams));
        return alipayRepository.create(request, model);
    }

    /**
     * https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2018071360585567&scope=auth_base&redirect_uri=http://kscfwh.natappfree.cc/alipay/auth_token
     *
     * @param httpRequest
     * @return
     */
    @RequestMapping("auth_token")
    @ResponseBody
    public AlipaySystemOauthTokenResponse authToken(HttpServletRequest httpRequest) {
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setGrantType("authorization_code");
        request.setCode(httpRequest.getParameter("auth_code"));
        AlipaySystemOauthTokenResponse response = alipayRepository.authToken(request);
        System.out.println(JsonMapper2.toJson(response));
        return response;
    }

    @GetMapping("auth_code")
    public String home() {
        return "redirect:/webjars/home.html";
    }


    @RequestMapping("*/notify")
    @ResponseBody
    public void payNotify(HttpServletRequest request, HttpServletResponse response, AlipayMessage alipayMessage) {
        pendingOrders.done(alipayMessage.get("out_trade_no"), alipayMessage, false);
        Views.renderText(response, "success");
    }

    @RequestMapping("*/return")
    @ResponseBody
    public Map<String, Object> payReturn(AlipayMessage alipayMessage, HttpServletResponse response) {
        String orderId = alipayMessage.get("out_trade_no");
        Map<String, Object> resp = Maps.newHashMap();
        try {
            /* return_url 传过来的消息 */
            resp.put("return", alipayMessage.rawData());
            /* notify_url 传过来的消息 */
            resp.put("notify", pendingOrders.waitDone(orderId, 10, TimeUnit.SECONDS).rawData());
            /* 订单查询结果 */
            resp.put("query", tradeQuery(orderId));
        } catch (Exception e) {
            e.printStackTrace();
            resp.put("errorMessage", "尚未收到订单支付成功消息");
            return resp;
        }
        return resp;
    }

    protected AlipayTradeQueryResponse tradeQuery(String orderId) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(orderId);
        return alipayRepository.tradeQuery(request, model);
    }


    @Autowired
    public void setAlipayRepository(AlipayRepository alipayRepository) {
        this.alipayRepository = alipayRepository;
    }

}
