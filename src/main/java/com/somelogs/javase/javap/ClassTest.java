package com.somelogs.javase.javap;

import com.somelogs.javase.javap.file.ClassInfo;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * test
 *
 * @author LBG - 2018/1/15 0015 16:30
 */
public class ClassTest {

    @Test
    public void test() throws FileNotFoundException {
        InputStream input = new FileInputStream("C:/Users/Administrator/Desktop/TestClass.class");
        ClassInfo classInfo = ClassAnalyzer.analyze(input);
        classInfo.print();
    }
}
