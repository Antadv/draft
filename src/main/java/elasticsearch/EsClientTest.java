package elasticsearch;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.get.GetField;
import org.elasticsearch.search.SearchHit;

import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/6/16.
 */
public class EsClientTest {

    public static void main(String[] args) {
        //EsClient.createIndex("website");
        try {
            //EsClient.addType();
            //IndexResponse indexResponse = EsClient.createData();
            //System.out.println(indexResponse.getIndex() + "-" + indexResponse.getType() + ":" + indexResponse.getId());
            GetResponse response = EsClient.get("AVywjOZwjA4CE3gKEJ0v");
            System.out.println(response.getSourceAsString());
            for (Map.Entry<String, GetField> entry : response.getFields().entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().getValue());
            }

            SearchResponse searchResponse = EsClient.search("内");
            for (SearchHit hit : searchResponse.getHits()) {
                String id = hit.getId();
                String title = hit.getFields().get("title").getValue();
                String content = hit.getFields().get("content").getValue();
                System.out.println("id:" + id + ", title:" + title + ", content:" + content);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
