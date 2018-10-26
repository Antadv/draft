package com.somelogs.ssq;

import lombok.Data;

import java.util.List;

/**
 * 描述
 *
 * @author LBG - 2018/10/26 0026
 */
@Data
public class SSQResponse {

    private Integer state;
    private String message;
    private Integer pageCount;
    private Integer countNum;
    private List<SSQData> result;
}
