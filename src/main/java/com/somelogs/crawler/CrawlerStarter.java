package com.somelogs.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import com.somelogs.utils.http.HttpUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author LBG - 2017/9/29 0029
 */
public class CrawlerStarter {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private static final ExecutorService EXECUTOR
            = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);

    public static void main(String[] args) throws IOException {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        String outageDate = DATE_FORMAT.format(calendar.getTime());

        // 获取省份
        String provinceResult = HttpUtils.okPost(ApiConstant.ORG_LIST_API, "");
        JSONArray provinceArray = JSONArray.parseArray(provinceResult);
        if (provinceArray == null || provinceArray.size() == 0) {
            return;
        }

        List<AreaOrg> orgList = new ArrayList<>(provinceArray.size());
        for (int i = 0; i < provinceArray.size(); i++) {
            JSONObject jsonObject = provinceArray.getJSONObject(i);
            AreaOrg provinceOrg = JSON.parseObject(jsonObject.toJSONString(), AreaOrg.class);
            orgList.add(provinceOrg);
        }

        // 获取区
        List<Future<List<OutageInfo>>> outageInfoList = new ArrayList<>(1000);
        for (AreaOrg province : orgList) {
            //String areaResult = HttpUtils.okPost(ApiConstant.NEXT_ORG_LIST_API, "orgNo=" + province.getCode());
            Map<String, Object> map = new HashMap<>();
            map.put("orgNo", province.getCode());
            String areaResult = HttpUtils.okPostForm(ApiConstant.NEXT_ORG_LIST_API, map);
            if (StringUtils.isBlank(areaResult)) {
                continue;
            }
            JSONArray areaArray = JSONArray.parseArray(areaResult);
            if (areaArray == null || areaArray.size() == 0) {
                continue;
            }
            for (int i = 0; i < areaArray.size(); i++) {
                JSONObject jsonObject = areaArray.getJSONObject(i);
                AreaOrg areaOrg = JSON.parseObject(jsonObject.toJSONString(), AreaOrg.class);
                areaOrg.setProvinceCode(province.getCode());
                outageInfoList.add(EXECUTOR.submit(new OutageInfoTask(areaOrg, outageDate, outageDate)));
            }
        }

        // 获取结果
        List<OutageInfoVO> voList = new ArrayList<>(1000);
        for (Future<List<OutageInfo>> future : outageInfoList) {
            try {
                List<OutageInfo> list = future.get();
                for (OutageInfo outageInfo : list) {
                    OutageInfoVO infoVO = new OutageInfoVO();
                    infoVO.setLineName(outageInfo.getLineName());
                    infoVO.setScope(outageInfo.getScope());
                    infoVO.setStartTime(outageInfo.getStartTime());
                    infoVO.setStopTime(outageInfo.getStopTime());
                    infoVO.setSubsName(outageInfo.getSubsName());
                    infoVO.setTypeCode(outageInfo.getTypeCode());
                    voList.add(infoVO);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(voList);
    }
}
