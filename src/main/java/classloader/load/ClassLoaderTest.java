package classloader.load;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * 描述
 * Created by liubingguang on 2017/6/22.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    System.out.println("fileName=" + fileName);
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if (is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = classLoader.loadClass("classloader.load.ClassLoaderTest").newInstance();
        System.out.println("class=" + obj.getClass());
        System.out.println(obj instanceof classloader.load.ClassLoaderTest);
    }

    @Test
    public void getClassLoaderTest() {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);
        System.out.println(classLoader.getParent());
        System.out.println(classLoader.getParent().getParent());
    }

    /**
     * 加载第三方class文件
     */
    @Test
    public void test() throws Exception {
        CustomClassLoader classLoader = new CustomClassLoader("E:\\");
        Class<?> clazz = classLoader.loadClass("HelloWorld");
        Object obj = clazz.newInstance();
        Method testMethod = clazz.getMethod("test", int.class);
        String title = String.valueOf(testMethod.invoke(obj, 1));
        System.out.println(clazz.getClassLoader());
        System.out.println("title=" + title);
    }

    @Test
    public void test2() throws Exception {
        Class<?> clazz = Class.forName("HelloWorld", false, new CustomClassLoader("E:\\"));
        String content = String.valueOf(clazz.getField("content").get(clazz));
        /**
         * 这里会打印content的值
         * 类初始化的五种情况中其中一种：
         *  使用java.lang.reflect包的方法对类进行反射调用的时候，如果类没有初始化，则需要先触发其初始化
         */
        System.out.println("content=" + content);
    }

    @Test
    public void test3() throws Exception {
        System.out.println(Object.class.getClassLoader());
        System.out.println(String.class.getClassLoader());
        System.out.println(CustomClassLoader.class.getClassLoader());
    }
}
