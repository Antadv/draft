package elasticsearch;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
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
 * elasticsearch client
 *
 * @author LBG - 2017/6/16 0008
 */
public class EsClient {

    private static final String HOST = "127.0.0.1";
    private static Client client;
    static {
        Settings settings = Settings.settingsBuilder()
                .put("cluster.name", "elasticsearch")
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
            e.printStackTrace();
        }
    }

    private EsClient() {}

    /**
     * 获取
     */
    public static GetResponse get(DataParam dataParam) {
        return client.prepareGet(dataParam.getIndex(),
                                 dataParam.getType(),
                                 dataParam.getDocId()).get();
    }

    /**
     * 创建索引
     */
    public static CreateIndexResponse createIndex(String indexName) {
        return client.admin().indices().prepareCreate(indexName).execute().actionGet();
    }

    /**
     * 创建类型
     */
    public static void addType() throws IOException {
        XContentBuilder builder = jsonBuilder()
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
     * 索引数据
     */
    public static IndexResponse index(DataParam dataParam) throws IOException {
        if (StringUtils.isBlank(dataParam.getDocId())) {
            return indexWithoutDocId(dataParam);
        }
        return indexWithDocId(dataParam);
    }

    /**
     * 索引数据, 手动指定文档 id
     */
    private static IndexResponse indexWithDocId(DataParam dataParam) throws IOException {
        return client.prepareIndex(dataParam.getIndex(),dataParam.getType(), dataParam.getDocId())
                     .setSource(dataParam.getDataMap())
                     .execute()
                     .actionGet();
    }

    /**
     * 索引数据, 系统生成 id
     */
    private static IndexResponse indexWithoutDocId(DataParam dataParam) throws IOException {
        return client.prepareIndex(dataParam.getIndex(),dataParam.getType(), dataParam.getDocId())
                     .setSource(dataParam.getDataMap())
                     .execute()
                     .actionGet();
    }

    /**
     * 搜索
     */
    public static SearchResponse query(DataParam dataParam) {
        return client.prepareSearch(dataParam.getIndex())
                     .setTypes(dataParam.getType())
                     .setQuery(QueryBuilders.multiMatchQuery(dataParam.getSearchWord(), "title", "content"))
                     .setFrom(0).setSize(60)
                     .setExplain(true)
                     .execute()
                     .actionGet();
    }

    /**
     * 分页查询所有文档
     */
    public static SearchResponse pageSearch() {
        return client.prepareSearch("website")
                .setTypes("blog")
                .setFrom(0)
                .setSize(100)
                .setExplain(true)
                .execute()
                .actionGet();
    }

    /**
     * 更新
     */
    public static UpdateResponse update(DataParam dataParam) {
        UpdateRequest updateRequest
                = new UpdateRequest(dataParam.getIndex(), dataParam.getType(), dataParam.getDocId());
        try {
            updateRequest.doc(dataParam.getDataMap());
            return client.update(updateRequest).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除
     */
    public static DeleteResponse delete(DataParam dataParam) {
        return client.prepareDelete(dataParam.getIndex(),
                                    dataParam.getType(),
                                    dataParam.getDocId()).get();
    }
}
