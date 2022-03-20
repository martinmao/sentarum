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

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayRequest;
import com.alipay.api.AlipayResponse;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author <a href="mailto:martinmao@icloud.com">Martin Mao</a>
 */
public class AlipayClientTemplate extends AlipaySupport implements DisposableBean {

    @Value("${alipay.rpc.threads.enabled:false}")
    private boolean rpcThreadsEnabled = false;
    @Value("${alipay.rpc.threads.core:3}")
    private int coreRequestThreadsSize = 3;
    @Value("${alipay.rpc.threads.maximum:5}")
    private int maximumRequestThreadsSize = 5;
    @Value("${alipay.rpc.threads.alive_seconds:60}")
    private long requestThreadsKeepAliveSeconds = 60;
    @Value("${alipay.rpc.threads.flow_limit:-1}")
    private int flowLimit = -1;

    private AlipayClient alipayClient;
    private ExecutorService executorService;

    public void execute(final AlipayRequest<?> request, final AlipayRequestCallback callback) {
        if (null == executorService)
            throw new IllegalStateException(
                    "rpcThreads disabled. use [alipay.rpc.threads.enabled] to enable this feature.");
        executorService.submit(new Callable<AlipayResponse>() {
            @Override
            public AlipayResponse call() throws Exception {
                Exception throwable = null;
                AlipayResponse response = null;
                try {
                    response = execute(request);
                } catch (Exception e) {
                    throwable = e;
                } finally {
                    try {
                        callback.onReply(request, response, throwable);
                    } catch (Exception e) {
                        logger.warn(
                                "Detected a failure execution on callback[" + callback.getClass().getName()
                                        + "]. Do not throws out any exception in sub-classes of AlipayRequestCallback",
                                e);
                    }
                }
                return null;
            }
        });
    }

    public Future<AlipayResponse> execute(final AlipayRequest<?> request, final SettableFuture<AlipayResponse> future) {
        return executorService.submit(new Callable<AlipayResponse>() {
            @Override
            public AlipayResponse call() throws Exception {
                AlipayResponse response = execute(request);
                if (null != future)
                    future.set(response);
                return response;
            }
        });
    }

    public <T extends AlipayResponse> T execute(AlipayRequest<T> request) {
        try {
            return alipayClient.execute(request);
        } catch (AlipayApiException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request) {
        try {
            return alipayClient.pageExecute(request);
        } catch (AlipayApiException e) {
            throw new IllegalStateException(e);
        }
    }

    public <T extends AlipayResponse> T pageExecute(AlipayRequest<T> request, String method) {
        try {
            return alipayClient.pageExecute(request, method);
        } catch (AlipayApiException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void initExecutorService() {
        logger.debug("rpcThreadsEnabled [{}]", rpcThreadsEnabled);
        executorService = new ThreadPoolExecutor(coreRequestThreadsSize, maximumRequestThreadsSize,
                requestThreadsKeepAliveSeconds, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(getFlowLimit()),
                new ThreadFactoryBuilder().setNameFormat("alipay-rpc-%d").build(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                throw new RejectedExecutionException(
                        "overflow of request pending queues. reject new task. thread-pool: ["
                                + StringUtils.substringAfter(executor.toString(), "["));
            }
        });
        logger.info("Request thread-pool[{}->{}/{}s] Initialized. [{}", coreRequestThreadsSize,
                maximumRequestThreadsSize, requestThreadsKeepAliveSeconds,
                StringUtils.substringAfter(executorService.toString(), "["));

    }

    public int getFlowLimit() {
        final int minimumFlowLimit = computeMinimumFlowLimit();
        return flowLimit == -1 ? Integer.MAX_VALUE : (flowLimit > minimumFlowLimit ? flowLimit : minimumFlowLimit);
    }

    protected int computeMinimumFlowLimit() {
        return maximumRequestThreadsSize + (maximumRequestThreadsSize - coreRequestThreadsSize) * 2;
    }

    /**
     * flow limit of threads-pool. will reject any tasks when exceed this
     * value(will not apply settings in runtime).
     *
     * @param flowLimit
     */
    public void setFlowLimit(int flowLimit) {
        this.flowLimit = flowLimit;
    }

    /**
     * will not apply settings in runtime.
     *
     * @param maximumRequestThreadsSize
     */
    public void setMaximumRequestThreadsSize(int maximumRequestThreadsSize) {
        this.maximumRequestThreadsSize = maximumRequestThreadsSize;
    }

    /**
     * will not apply settings in runtime.
     *
     * @param coreRequestThreadsSize
     */
    public void setCoreRequestThreadsSize(int coreRequestThreadsSize) {
        this.coreRequestThreadsSize = coreRequestThreadsSize;
    }

    /**
     * will not apply settings in runtime.
     *
     * @param requestThreadsKeepAliveSeconds
     */
    public void setRequestThreadsKeepAliveSeconds(long requestThreadsKeepAliveSeconds) {
        this.requestThreadsKeepAliveSeconds = requestThreadsKeepAliveSeconds;
    }

    /**
     * will not apply settings in runtime.
     *
     * @param rpcThreadsEnabled
     */
    public void setRpcThreadsEnabled(boolean rpcThreadsEnabled) {
        this.rpcThreadsEnabled = rpcThreadsEnabled;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.debug("Initializing... alipay client.");
        super.afterPropertiesSet();
        alipayClient = createNativeAlipayClient();
        if (rpcThreadsEnabled)
            initExecutorService();
        logger.info("Successfully created alipay client.");
    }

    public boolean isRpcThreadsEnabled() {
        return rpcThreadsEnabled;
    }

    @Override
    public void destroy() throws Exception {
        if (null == executorService)
            return;
        logger.debug("Shutting-down alipay-rpc thread Pool.....");
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // restore interrupted flag
        }
        logger.info("alipay-rpc thread pool shutdown successfully.");
    }
}
