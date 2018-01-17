package com.somelogs.javase.javap.table;

import com.somelogs.javase.javap.attribute.AttributeInfo;
import lombok.Data;

import java.util.List;

/**
 * field table
 *
 * @author LBG - 2018/1/17 0017
 */
@Data
public class FieldTable {

    private String accessFlag;
    private String simpleName;
    private String descriptor;
    private List<AttributeInfo> attributeInfoList;
}
