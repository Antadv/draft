package com.somelogs.soa.proxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import com.somelogs.soa.annotation.SOARequestURL;
import com.somelogs.soa.model.Response;
import com.somelogs.soa.model.ResponseStatus;
import com.somelogs.utils.JsonUtils;
import com.somelogs.utils.http.HttpUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 描述
 *
 * @author LBG - 2018/1/3 0003
 */
public class ServerAccessor implements MethodInterceptor {

    private static final String SERVER_URL = "http://localhost:8080/";

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
            String responseJson = HttpUtils.post(SERVER_URL + typeAnnotation.url() + methodAnnotation.url(), postBody);
            response = (Response) JsonUtils.readValue(responseJson, method.getReturnType());
        } catch (Exception e) {
            response = new Response<>();
            response.setCode(ResponseStatus.INTERNAL_SERVER_ERROR.getCode());
            response.setMessage(ResponseStatus.INTERNAL_SERVER_ERROR.getMessage());
            throw new RuntimeException(response.toString(), e);
        }
        if (response == null || !Objects.equals(response.getCode(), ResponseStatus.SUCCESS.getCode())) {
            throw new RuntimeException(String.valueOf(response));
        }
        return response.getData();
    }
}
