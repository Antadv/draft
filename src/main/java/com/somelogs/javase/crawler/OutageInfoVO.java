package com.somelogs.javase.crawler;

import lombok.Data;

/**
 * 描述
 * @author LBG - 2017/9/29 0029
 */
@Data
public class OutageInfoVO {

    private String subsName;
    private String typeCode;
    private String startTime;
    private String stopTime;
    private String scope;
    private String lineName;
}
