package elasticsearch;

import lombok.Data;

import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/6/19.
 */
@Data
public class DataParam {

    /** 数据实体 */
    private Map<String, Object> dataMap;

    /** 索引 */
    private String index;

    /** 类型 */
    private String type;

    /** 客户端搜索内容 */
    private String searchWord;

    /** 文档id */
    private String docId;
}
