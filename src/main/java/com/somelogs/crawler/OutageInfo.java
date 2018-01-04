package com.somelogs.crawler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Set;

/**
 * 停电信息
 * @author LBG - 2017/9/29 0029
 */
@Data
public class OutageInfo {

    private String stopDate;
    private String scope;
    private String sgpoweroffId;
    private String orgNo;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String tranName;
    private String tgName;
    private String poweroffReason;
    private String roadName;
    private String startTime;
    private String countyName;
    private String sdLineName;
    private String powerOffReason;
    private String powerTime;
    private String lineName;
    private String poweroffId;
    private String powerOffId;
    private String subsName;
    private String powerComm;
    private String dateDay;
    private String pubTranName;
    private String poweroffArea;
    private String typeCode;
    private String lineNo;
    private String streetName;
    private String orgName;
    private String nowTime;
    private String stopTime;
    private String typeName;
    private String powerOffArea;
    private String infoStatus;
    private String tgNo;
    private String powerType;
    private String orgNos;
    private String infoStatusName;
    private String communityName;
    private String subsNo;
    private String villageName;

    public static void main(String[] args) {
        String content = "{\"message\":\"\",\"today\":\"2017-09-29\",\"seleList\":[{\"typeName\":null,\"typeCode\":\"计划停电\",\"startTime\":\"2017-09-29 06:00:00\",\"scope\":\"人民广场街道武胜路100号；武胜路350号；外滩街道武胜路925公交站台对面；武胜路黄陂北路；人民大道人行地下通道；武胜路北侧；西藏路人民广场南西广场；\",\"stopTime\":\"2017-09-29 18:00:00\",\"orgNo\":\"31401\",\"orgName\":\"市区供电公司\",\"cityCode\":\"31401\",\"cityName\":\"市区供电公司\",\"countyName\":null,\"lineName\":\"武黄\",\"tgName\":null,\"tranName\":null,\"sdLineName\":null,\"sgpoweroffId\":null,\"streetName\":null,\"villageName\":null,\"roadName\":null,\"communityName\":null,\"nowTime\":null,\"poweroffId\":2017092231000060,\"subsName\":\"武黄\",\"pubTranName\":\"武黄_站变\",\"stopDate\":\"2017-09-29 18:00:00\",\"poweroffArea\":\"站变;\",\"poweroffReason\":\"检修\",\"powerTime\":\"\",\"powerComm\":\"\",\"subsNo\":\"\",\"lineNo\":\"\",\"tgNo\":\"\",\"infoStatus\":\"1\",\"infoStatusName\":null,\"dateDay\":null,\"orgNos\":null,\"provinceCode\":null,\"powerType\":\"未送电\",\"powerOffArea\":\"站变;\",\"powerOffReason\":\"检修\",\"powerOffId\":\"2017092231000060\"},{\"typeName\":null,\"typeCode\":\"计划停电\",\"startTime\":\"2017-09-29 06:00:00\",\"scope\":\"延安西路65号；\",\"stopTime\":\"2017-09-29 18:00:00\",\"orgNo\":\"31401\",\"orgName\":\"市区供电公司\",\"cityCode\":\"31401\",\"cityName\":\"市区供电公司\",\"countyName\":null,\"lineName\":\"贵都饭店（永源）\",\"tgName\":null,\"tranName\":null,\"sdLineName\":null,\"sgpoweroffId\":null,\"streetName\":null,\"villageName\":null,\"roadName\":null,\"communityName\":null,\"nowTime\":null,\"poweroffId\":2017092231000043,\"subsName\":\"永源\",\"pubTranName\":\"\",\"stopDate\":\"2017-09-29 18:00:00\",\"poweroffArea\":\"1号变压器;4号变压器;\",\"poweroffReason\":\"检修\",\"powerTime\":\"\",\"powerComm\":\"\",\"subsNo\":\"\",\"lineNo\":\"\",\"tgNo\":\"\",\"infoStatus\":\"1\",\"infoStatusName\":null,\"dateDay\":null,\"orgNos\":null,\"provinceCode\":null,\"powerType\":\"未送电\",\"powerOffArea\":\"1号变压器;4号变压器;\",\"powerOffReason\":\"检修\",\"powerOffId\":\"2017092231000043\"}],\"pageModel\":{\"beginCount\":0,\"pageCount\":10,\"totalCount\":2,\"totalPage\":1,\"pageNow\":1,\"tableRows\":0},\"isExperi\":\"1\"}";
        //String content = "{\"name\":\"33\",\"age\":\"33\"}";
        JSONObject jsonObject = JSONObject.parseObject(content);
        JSONArray seleList = jsonObject.getJSONArray("seleList");
        jsonObject = (JSONObject) seleList.get(0);
        Set<String> strings = jsonObject.keySet();
        for (String s : strings) {
            System.out.println("private String " + s + ";");
        }
    }
}
