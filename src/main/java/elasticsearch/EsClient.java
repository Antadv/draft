package elasticsearch;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 描述
 * Created by liubingguang on 2017/6/16.
 */
public class EsClient {

    private static final String HOST = "127.0.0.1";
    public static Client client;
    static {
        Settings settings = Settings.settingsBuilder()
                .put("client.transport.sniff", true)
                .build();
        try {
            client = TransportClient
                    .builder()
                    .settings(settings)
                    .build()
                    .addTransportAddress(
                            new InetSocketTransportAddress(InetAddress.getByName(HOST), 9300));
        } catch (UnknownHostException e) {
            System.out.println("error occurred");
            e.printStackTrace();
        }
    }

    public static GetResponse get(String id) {
        return client.prepareGet("website", "blog", id).get();
    }

    /**
     * 创建索引
     */
    public static CreateIndexResponse createIndex(String indexName) {
        return client.admin().indices().prepareCreate(indexName).execute().actionGet();
    }

    /**
     * 集群状态
     */
    public static void clusterStatus(){
        //注意集群的client获取方式略有不同
        ClusterAdminClient clusterAdminClient = client.admin().cluster();
        ClusterHealthResponse healths = clusterAdminClient.prepareHealth().get();
        String clusterName = healths.getClusterName();
        int numberOfDataNodes = healths.getNumberOfDataNodes();
        int numberOfNodes = healths.getNumberOfNodes();
        ClusterHealthStatus status = healths.getStatus();
        System.out.println(clusterName+"###"+numberOfDataNodes+"###"+status.name());
    }

    /**
     * 创建类型
     */
    public static void addType() throws IOException {
        // 定义索引字段属性,其实这里就是组合json,可以参考curl 方式创建索引的json格式  此处blog 和执行时blog必须一致
        XContentBuilder builder= jsonBuilder()
                .startObject()
                .startObject("blog")
                .startObject("properties")
                .startObject("id").field("type", "integer").field("store", "yes").endObject()
                .startObject("title").field("type", "string").field("store", "yes").endObject()
                .startObject("content").field("type", "string").field("store", "yes").endObject()
                .endObject()
                .endObject()
                .endObject();

        PutMappingRequest mappingRequest = Requests.putMappingRequest("website").type("blog").source(builder);
        client.admin().indices().putMapping(mappingRequest).actionGet();
    }

    /**
     * 创建数据
     */
    public static IndexResponse createData() throws IOException {
        // 创建数据json 注意此ID是一个字段不是上面查询的id
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("id", "2")
                .field("title", "我是标题")
                .field("content", "我是内容")
                .endObject();
        return client.prepareIndex("website","blog").setSource(builder).execute().actionGet();
    }

    /**
     * 搜索
     */
    public static SearchResponse search(String searchWord) {
        return client.prepareSearch("website")
                .setTypes("blog")
                .setQuery(QueryBuilders.termQuery("content", searchWord))
                .setFrom(0).setSize(10)
                .setExplain(true)
                .get();
    }


}
