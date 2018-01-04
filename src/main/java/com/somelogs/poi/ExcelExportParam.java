package com.somelogs.poi;

import lombok.Data;

import java.io.InputStream;
import java.util.List;

/**
 * 描述
 * Created by liubingguang on 2017/7/26.
 */
@Data
public class ExcelExportParam {

    /**
     * 报表数据
     */
    private List<List<String>> data;

    /**
     * 数据标题
     */
    private List<String> titles;

    /**
     * 模板读取流
     */
    private InputStream inputStream;
}
