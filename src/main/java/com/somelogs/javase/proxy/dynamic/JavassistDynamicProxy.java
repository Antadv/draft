package com.somelogs.javase.proxy.dynamic;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewConstructor;

/**
 * 描述
 *
 * @author LBG - 2019/3/4
 */
public class JavassistDynamicProxy {

    /**
     * javassist通过字节码直接生成代理对象
     */
    public static BookApi createJavassistBytecodeDynamicProxy() throws Exception {
        ClassPool pool = new ClassPool(true);
        CtClass ctClass = pool.makeClass(BookApi.class.getName() + "JavassistProxy");
        ctClass.addInterface(pool.get(BookApi.class.getName()));
        ctClass.addConstructor(CtNewConstructor.defaultConstructor(ctClass));
        ctClass.addMethod(CtMethod.make("public void sell() { System.out.print(\"javassist dynamic proxy\");}", ctClass));
        Class pc = ctClass.toClass();
        return (BookApi) pc.newInstance();
    }
}
