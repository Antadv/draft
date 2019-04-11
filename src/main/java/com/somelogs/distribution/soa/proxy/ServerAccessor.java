package com.somelogs.distribution.soa.proxy;

import com.somelogs.distribution.soa.annotation.SOARequestURL;
import com.somelogs.distribution.soa.model.Response;
import com.somelogs.distribution.soa.model.ResponseStatus;
import com.somelogs.utils.JsonUtils;
import com.somelogs.utils.http.HttpUtils;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 服务访问器
 *
 * @author LBG - 2018/1/3 0003
 */
public class ServerAccessor implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerAccessor.class);

    private String SERVER_URL;

    public ServerAccessor(String serverUrl) {
        if (serverUrl.endsWith("/")) {
            serverUrl = serverUrl.substring(0, serverUrl.length() - 1);
        }
        this.SERVER_URL = serverUrl;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        SOARequestURL methodAnnotation = method.getAnnotation(SOARequestURL.class);
        if (methodAnnotation == null) {
            return methodProxy.invokeSuper(o, objects);
        }
        SOARequestURL typeAnnotation = method.getDeclaringClass().getAnnotation(SOARequestURL.class);
        String postBody = JsonUtils.writeValueAsString(objects[0]);
        Response response;
        try {
            String requestUrl = SERVER_URL + typeAnnotation.url() + methodAnnotation.url();
            LOGGER.info("Server accessor request url:" + requestUrl);
            String responseJson = HttpUtils.post(requestUrl, postBody);
            response = JsonUtils.readValue(responseJson, method.getGenericReturnType());
        } catch (Exception e) {
            LOGGER.error("Server accessor request error", e);
            response = new Response<>();
            response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
            response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
        }
        return response;
    }
}
