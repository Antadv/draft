package soa.factory;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import soa.annotation.SOARequestURL;
import utils.JsonUtils;
import utils.http.HttpUtils;

import java.lang.reflect.Method;

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
        String responseJson = HttpUtils.post(SERVER_URL + typeAnnotation.value() + methodAnnotation.value(), postBody);


        return null;
    }
}
