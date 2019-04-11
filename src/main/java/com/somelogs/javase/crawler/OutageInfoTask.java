package com.somelogs.javase.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import com.somelogs.utils.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * 任务
 * @author LBG - 2017/9/29 0029
 */
@Data
public class OutageInfoTask implements Callable<List<OutageInfo>> {

    private AreaOrg areaOrg;
    private String startDate;
    private String endDate;

    public OutageInfoTask(AreaOrg areaOrg, String startDate, String endDate) {
        this.areaOrg = areaOrg;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public List<OutageInfo> call() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("orgNo", areaOrg.getCode());
        params.put("outageStartTime", startDate);
        params.put("outageEndTime", endDate);
        params.put("scope", "");
        params.put("provinceNo", areaOrg.getProvinceCode());
        params.put("typeCode", "");
        params.put("lineName", "");
        params.put("anHui", "02");
        String result = HttpUtils.okPostForm(ApiConstant.OUTAGE_NOTICE_LIST_API, params);
        if (StringUtils.isBlank(result)) {
            return new ArrayList<>();
        }
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("seleList");
        if (jsonArray == null || jsonArray.size() == 0) {
            return new ArrayList<>();
        }
        List<OutageInfo> list = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject outageInfoJson = jsonArray.getJSONObject(i);
            OutageInfo outageInfo = JSON.parseObject(outageInfoJson.toJSONString(), OutageInfo.class);
            list.add(outageInfo);
        }
        return list;
    }
}
