package cglib;

import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * 描述
 * Created by liubingguang on 2017/8/11.
 */
public class ServerAccessor implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(method.getName());
        return null;
    }
}
