package com.somelogs.javase.javap.table;

import com.somelogs.javase.javap.attribute.AttributeInfo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * field table Or Method table
 *
 * @author LBG - 2018/1/17 0017
 */
@Setter
@Getter
public class FieldMethodInfo {

    private String accessFlag;
    private String simpleName;
    private String descriptor;
    private List<AttributeInfo> attributeInfoList;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(accessFlag)
            .append(descriptor)
            .append(" ")
            .append(simpleName)
            .append("\n");

        for (AttributeInfo info : attributeInfoList) {
            sb.append("\t").append(info.getPrintContent()).append("\n");
        }
        sb.deleteCharAt(sb.length() -1);
        return sb.toString();
    }
}
