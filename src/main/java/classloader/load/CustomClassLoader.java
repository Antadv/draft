package classloader.load;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 描述
 * Created by liubingguang on 2017/6/22.
 */
public class CustomClassLoader extends ClassLoader {

    private String basePath;

    public CustomClassLoader(String basePath) {
        this.basePath = basePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> clazz = findLoadedClass(name);
        if (clazz != null) {
            return clazz;
        }

        String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
        InputStream is = null;
        try {
            is = new FileInputStream(basePath + fileName);
            byte[] data = new byte[is.available()];
            is.read(data);
            return defineClass(name, data, 0, data.length);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
