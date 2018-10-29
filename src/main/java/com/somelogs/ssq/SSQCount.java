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

    private static Comparator<Map.Entry<String, Integer>> rankComparator = new Comparator<Map.Entry<String, Integer>>() {
        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o2.getValue() - o1.getValue();
        }
    };
    private static Comparator<Map.Entry<Integer, Integer>> rangeRankComparator = new Comparator<Map.Entry<Integer, Integer>>() {
        @Override
        public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
            return o2.getValue() - o1.getValue();
        }
    };

    public static void main(String[] args) throws IOException {
        //printRank();
        printRangeRank();
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

        List<Map.Entry<String, Integer>> redEntryList = new ArrayList<>(redMap.entrySet());
        Collections.sort(redEntryList, rankComparator);
        System.out.println("———————————");
        System.out.println("red(" + redEntryList.size() + ")");
        System.out.println("———————————");
        for (Map.Entry<String, Integer> entry : redEntryList) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }

        List<Map.Entry<String, Integer>> blueEntryList = new ArrayList<>(blueMap.entrySet());
        Collections.sort(blueEntryList, rankComparator);
        System.out.println("———————————");
        System.out.println("blue(" + blueEntryList.size() + ")");
        System.out.println("———————————");
        for (Map.Entry<String, Integer> entry : blueEntryList) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }

    private static void printRangeRank() {
        List<SSQData> dataList = getDataFromFile().getResult();

        List<List<String>> redList = new ArrayList<>(dataList.size());
        List<String> blueList = new ArrayList<>(dataList.size());

        for (SSQData data : dataList) {
            blueList.add(data.getBlue());
            String[] split = data.getRed().split(",");
            redList.add(Arrays.asList(split));
        }

        // 两位 出现 次数

        // 0-9
        Map<Integer, Integer> tenMap = new HashMap<>();
        // 10-19
        Map<Integer, Integer> twentyMap = new HashMap<>();
        // 20-29
        Map<Integer, Integer> thirtyMap = new HashMap<>();
        // 30-33
        Map<Integer, Integer> fortyMap = new HashMap<>();
        for (List<String> list : redList) {
            int ten = 0;
            int twenty = 0;
            int thirty = 0;
            int forty = 0;
            for (String red : list) {
                if (red.startsWith("0")) {
                    ten++;
                } else if (red.startsWith("1")) {
                    twenty++;
                } else if (red.startsWith("2")) {
                    thirty++;
                } else {
                    forty++;
                }
            }
            putValue2Map(tenMap, ten);
            putValue2Map(twentyMap, twenty);
            putValue2Map(thirtyMap, thirty);
            putValue2Map(fortyMap, forty);
        }

        printRangeResult(tenMap, 0);
        printRangeResult(twentyMap, 1);
        printRangeResult(thirtyMap, 2);
        printRangeResult(fortyMap, 3);
    }

    private static void putValue2Map(Map<Integer, Integer> map, int value) {
        if (map.containsKey(value)) {
            map.put(value, map.get(value) + 1);
        } else {
            map.put(value, 1);
        }
    }

    private static void printRangeResult(Map<Integer, Integer> map, int type) {
        ArrayList<Map.Entry<Integer, Integer>> entryList = new ArrayList<>(map.entrySet());
        Collections.sort(entryList, rangeRankComparator);
        System.out.println("—————————");
        String title = "";
        if (type == 0) {
            title = "0 - 9";
        } else if (type == 1) {
            title = "10 - 19";
        } else if (type == 2) {
            title = "20 - 29";
        } else if (type == 3) {
            title = "30 - 33";
        }
        System.out.println(title);
        System.out.println("—————————");
        for (Map.Entry<Integer, Integer> entry : entryList) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
