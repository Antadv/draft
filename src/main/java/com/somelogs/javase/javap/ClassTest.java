package com.somelogs.javase.javap;

import com.somelogs.javase.javap.file.ClassInfo;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * test
 *
 * @author LBG - 2018/1/15 0015 16:30
 */
public class ClassTest {

    @Test
    public void test() throws Exception {
        InputStream input = new FileInputStream("C:/Users/Administrator/Desktop/TestClass.class");

        DataInputStream inputStream = new DataInputStream(input);
        ClassInfo classInfo = ClassAnalyzer.analyze(inputStream);
        classInfo.print();
    }

    @Test
    public void testByDataInputStream() throws Exception {
        InputStream input = new FileInputStream("C:/Users/Administrator/Desktop/TestClass.class");
        DataInputStream inputStream = new DataInputStream(input);
        ClassInfo classInfo = ClassDataAnalyzer.analyze(inputStream);
        classInfo.print();
    }
}
