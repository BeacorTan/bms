package com.common.framework.util;


import com.common.framework.constant.SystemConstant;
import com.google.gson.internal.$Gson$Types;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * @author YH
 * @create 2017-11-17
 */
@SuppressWarnings("ALL")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Lazy(false)
public class InvokerUtil {

    private static Logger logger = LoggerFactory.getLogger(InvokerUtil.class);
    private static InvokerUtil invokerUtil;
    @Autowired
    private static RestTemplate restTemplateStatic;
    @Autowired
    private RestTemplate restTemplate;

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }

    /**
     * 调用 第三方接口，返回ResponseEntity
     *
     * @param path
     * @param entity
     * @param type:  1: HttpMethod.GET 2:HttpMethod.POST
     * @return ResponseEntity
     * @throws BizException
     */
    public static ResponseEntity<String> remoteResponseInvoke(String path, HttpEntity<String> entity, String type)
            throws Exception {
        ResponseEntity<String> response = null;
        HttpMethod method = null;
        try {
            logger.info("request to [{}] and the request params: [{}]", path,
                    ServiceUtil.limitStringLength(ServiceUtil.object2Json(entity), 400));
            if ("1".equals(type)) {
                method = HttpMethod.GET;
            }
            if ("2".equals(type)) {
                method = HttpMethod.POST;
            }

            response = restTemplateStatic.exchange(path, method, entity, String.class);
            String body = response.getBody();
            HttpStatus statusCode = response.getStatusCode();
            int statusCodeValue = response.getStatusCodeValue();
            HttpHeaders headers = response.getHeaders();
            StringBuffer result = new StringBuffer();
            result.append("responseEntity.getBody()：").append(body).append("<hr>")
                  .append("responseEntity.getStatusCode()：").append(statusCode).append("<hr>")
                  .append("responseEntity.getStatusCodeValue()：").append(statusCodeValue).append("<hr>")
                  .append("responseEntity.getHeaders()：").append(headers).append("<hr>");
            logger.info(result.toString());
            logger.info("-------------------------------------------------------------------");
            logger.info("response to [{}] and the resonse info: [{}]", path,
                    ServiceUtil.limitStringLength(ServiceUtil.object2Json(response), 400));
        } catch (Throwable e) {
            logger.error("request to [{}] meet exception,error message:", path, e);
            // 请求超时返回
            if (e.getCause() instanceof ConnectTimeoutException) {
                throw new Exception("请求超时请稍后重试");
            }
            throw new Exception("服务器开小差了，请稍后重试");
        }
        return response;
    }


    @PostConstruct
    public void init() {
        invokerUtil = this;
        restTemplateStatic = this.restTemplate;
        restTemplateStatic.getMessageConverters().set(1, new StringHttpMessageConverter(Charset.forName("UTF-8")));
        // 设置超时
        if (restTemplateStatic.getRequestFactory() instanceof SimpleClientHttpRequestFactory) {
            ((SimpleClientHttpRequestFactory) restTemplateStatic.getRequestFactory())
                    .setConnectTimeout(SystemConstant.CONNECT_TIME_OUT_120);
            ((SimpleClientHttpRequestFactory) restTemplateStatic.getRequestFactory())
                    .setReadTimeout(SystemConstant.READ_TIME_OUT_120);
        } else if (restTemplateStatic.getRequestFactory() instanceof HttpComponentsClientHttpRequestFactory) {
            ((HttpComponentsClientHttpRequestFactory) restTemplateStatic.getRequestFactory())
                    .setConnectTimeout(SystemConstant.CONNECT_TIME_OUT_120);
            ((HttpComponentsClientHttpRequestFactory) restTemplateStatic.getRequestFactory())
                    .setReadTimeout(SystemConstant.READ_TIME_OUT_120);
        }

    }

}