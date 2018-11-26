package com.somelogs.bug;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;

/**
 * utf-8 bom 引起的读取配置文件失败的bug
 * @author LBG - 2017/10/11 0011
 */
public class UTF8WithBom {

    public static void main(String[] args) throws IOException {
        URL resource = Thread.currentThread().getContextClassLoader().getResource("bug/text.properties");
        if (resource != null) {
            System.out.println(resource.getPath());
            File file = new File(resource.getPath());
            System.out.println(FileUtils.readFileToString(file));
            System.out.println(Thread.currentThread().getContextClassLoader());
        }
        System.out.println(System.getProperty("java.class.path"));
        System.out.println("DriverManager class loader:" + DriverManager.class.getClassLoader());
    }
}
