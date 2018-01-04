package com.somelogs.utils.file;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述
 *
 * @author LBG - 2017/9/25 0025
 */
public class FileReaderUtils {

    public static void main(String[] args) throws IOException {
        String regex = "\"R_[a-zA-Z_]+\"";
        String[] fileNames = {"C:\\Users\\Administrator\\Desktop\\role_code\\agent_sale.txt",
                "C:\\Users\\Administrator\\Desktop\\role_code\\contract_soa.txt",
                "C:\\Users\\Administrator\\Desktop\\role_code\\sale_contract.txt",
                "C:\\Users\\Administrator\\Desktop\\role_code\\sale_contract_page.txt",
                "C:\\Users\\Administrator\\Desktop\\role_code\\wechat_ent.txt",};

        Set<String> set = new HashSet<>();
        for (String fileName : fileNames) {
            set = extractString(set, fileName, regex);
        }
        for (String role : set) {
            System.out.println(role);
        }
    }

    private static Set<String> extractString(Set<String> set, String filePathName, String regex) throws IOException {
        File file = new File(filePathName);
        String content = FileUtils.readFileToString(file);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            set.add(matcher.group());
        }
        return set;
    }
}
