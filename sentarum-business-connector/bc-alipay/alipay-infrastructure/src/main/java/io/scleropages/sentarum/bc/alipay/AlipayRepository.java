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

/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public interface AlipayRepository {


    /**
     * 统一收单交易创建接口 <a href="https://opendocs.alipay.com/apis/api_1/alipay.trade.create">https://opendocs.alipay.com/apis/api_1/alipay.trade.create</a>
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeCreateResponse create(AlipayTradeCreateRequest request, AlipayTradeCreateModel model);

    /**
     * H5快捷支付模式<br>
     * 适用于商家在移动端网页应用中集成支付宝支付功能。<br>
     * 商家在网页中调用支付宝提供的网页支付接口调起支付宝客户端内的支付模块，商家网页会跳转到支付宝中完成支付，支付完后跳回到商家网页内，最后展示支付结果。若无法唤起支付宝客户端，则在一定的时间后会自动进入网页支付流程。
     * <br>
     * NOTE: <br>
     * <p>
     * 20190516,该接口支付流程如下，调用该接口并不会请求阿里网关，而是根据请求参数生成form表单(executePage函数)（故而响应消息不会产生trade_no），将响应form
     * 文本直接展示给客户端，页面加载完成form表单自动提交(默认forms[0]，使用装饰器如sitemesh
     * 中存在表单的需要注意.)，form表单提交给alipay网关，此时才会产生实际订单号（trade_no），如果用户退出支付则忽略，并回调notify_url以及return_url(不确保顺序)
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeWapPayResponse wapPay(AlipayTradeWapPayRequest request, AlipayTradeWapPayModel model);

    /**
     * 该接口提供所有支付宝支付订单的查询，商户可以通过该接口主动查询订单状态，完成下一步的业务逻辑。 需要调用查询接口的情况：
     * 当商户后台、网络、服务器等出现异常，商户系统最终未接收到支付通知； 调用支付接口后，返回系统错误或未知交易状态情况；
     * 调用alipay.trade.pay，返回 IN-PROCESS 的状态； 调用alipay.trade.cancel之前，需确认支付状态
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeQueryResponse tradeQuery(AlipayTradeQueryRequest request, AlipayTradeQueryModel model);

    /**
     * 用于交易创建后，用户在一定时间内未进行支付，可调用该接口直接将未付款的交易进行关闭
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeCloseResponse close(AlipayTradeCloseRequest request, AlipayTradeCloseModel model);

    /**
     * 商户可使用该接口查询自已通过alipay.trade.refund提交的退款请求是否执行成功。
     * 该接口的返回码10000，仅代表本次查询操作成功，不代表退款成功。如果该接口返回了查询数据，则代表退款成功，如果没有查询到则代表未退款成功，可以调用退款接口进行重试。重试时请务必保证退款请求号一致
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeFastpayRefundQueryResponse refundQuery(AlipayTradeFastpayRefundQueryRequest request,
                                                      AlipayTradeFastpayRefundQueryModel model);

    /**
     * 当交易发生之后一段时间内，由于买家或者卖家的原因需要退款时，卖家可以通过退款接口将支付款退还给买家，支付宝将在收到退款请求并且验证成功之后，按照退款规则将支付款按原路退到买家账号上。
     * 交易超过约定时间（签约时设置的可退款时间）的订单无法进行退款
     * 支付宝退款支持单笔交易分多次退款，多次退款需要提交原支付订单的商户订单号和设置不同的退款单号。一笔退款失败后重新提交，要采用原来的退款单号。总退款金额不能超过用户实际支付金额
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradeRefundResponse refund(AlipayTradeRefundRequest request, AlipayTradeRefundModel model);

    /**
     * NOTE:<br>
     * <b>setProductCode 必须设置为FAST_INSTANT_TRADE_PAY</b><br>
     * 用户通过支付宝PC收银台完成支付，交易款项即时给到商户支付宝账户。
     * 类似于wapPay，对于页面跳转类API，SDK不会也无法像系统调用类API一样自动请求支付宝并获得结果，而是在接受request请求对象后，为开发者生成前台页面请求需要的完整form表单的html（包含自动提交脚本），商户直接将这个表单的String输出到http
     * response中即可。
     *
     * @param request
     * @param model
     * @return
     */
    AlipayTradePagePayResponse pagePay(AlipayTradePagePayRequest request, AlipayTradePagePayModel model);


    /**
     * <a href="https://opendocs.alipay.com/open/02xtla">alipay.system.oauth.token(换取授权访问令牌)</a>
     *
     * @param request
     * @return
     */
    AlipaySystemOauthTokenResponse authToken(AlipaySystemOauthTokenRequest request);
}
