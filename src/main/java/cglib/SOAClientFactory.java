package cglib;

import cglib.service.TestAService;
import cglib.service.TestBService;
import net.sf.cglib.proxy.Callback;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 工厂类
 * Created by liubingguang on 2017/8/11.
 */
public class SOAClientFactory extends BaseClientFactory {

    private final static Map<Method, Callback> CALLBACK_MAP = new HashMap<>();

    public static SOAClientFactory initialize(ClientConfig config) {
        return new SOAClientFactory(config);
    }

    private SOAClientFactory(ClientConfig config) {
        ServerAccessor serverAccessor = new ServerAccessor();
        Class<?>[] classes = {
                TestAService.class,
                TestBService.class
        };
        putAllMethodRelatedSameValueToMap(serverAccessor, classes);
    }

    private static void putAllMethodRelatedSameValueToMap(Callback callback, Class<?>...classes) {
        if (classes == null || classes.length == 0) {
            return;
        }
        for (Class<?> clazz : classes) {
            //Method[] methods = clazz.getMethods();
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                CALLBACK_MAP.put(method, callback);
            }
        }
    }

    public SOAClient create() {
        return create(SOAClient.class, CALLBACK_MAP);
    }
}
