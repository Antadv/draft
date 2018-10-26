package com.somelogs.ssq;

import com.somelogs.utils.JsonUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * 描述
 *
 * @author LBG - 2018/10/26 0026
 */
public class SSQCount {

    public static void main(String[] args) throws IOException {
        printRank();
    }

    private static SSQResponse getDataFromFile() {
        URL resource = SSQCount.class.getClassLoader().getResource("ssq.txt");
        Assert.notNull(resource);
        try {
            String jsonStr = FileUtils.readFileToString(new File(resource.getFile()));
            return JsonUtils.readValue(jsonStr, SSQResponse.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printRank() {
        List<SSQData> dataList = getDataFromFile().getResult();
        System.out.println("data size " + dataList.size());

        List<String> redList = new ArrayList<>(dataList.size() * 6);
        List<String> blueList = new ArrayList<>(dataList.size());

        for (SSQData data : dataList) {
            blueList.add(data.getBlue());
            String[] split = data.getRed().split(",");
            redList.addAll(Arrays.asList(split));
        }

        Map<String, Integer> redMap = new HashMap<>(dataList.size());
        Map<String, Integer> blueMap = new HashMap<>(dataList.size());

        for (String red : redList) {
            if (redMap.containsKey(red)) {
                redMap.put(red, redMap.get(red) + 1);
            } else {
                redMap.put(red, 1);
            }
        }

        for (String blue : blueList) {
            if (blueMap.containsKey(blue)) {
                blueMap.put(blue, redMap.get(blue) + 1);
            } else {
                blueMap.put(blue, 1);
            }
        }

        Comparator<Map.Entry<String, Integer>> valueComparator = new Comparator<Map.Entry<String,Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue() - o1.getValue();
            }
        };

        List<Map.Entry<String, Integer>> redEntryList = new ArrayList<>(redMap.entrySet());
        Collections.sort(redEntryList, valueComparator);
        System.out.println("———————————");
        System.out.println("red(" + redEntryList.size() + ")");
        System.out.println("———————————");
        for (Map.Entry<String, Integer> entry : redEntryList) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        List<Map.Entry<String, Integer>> blueEntryList = new ArrayList<>(blueMap.entrySet());
        Collections.sort(blueEntryList, valueComparator);
        System.out.println("———————————");
        System.out.println("blue(" + blueEntryList.size() + ")");
        System.out.println("———————————");
        for (Map.Entry<String, Integer> entry : blueEntryList) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
