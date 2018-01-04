package com.somelogs.elasticsearch;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.search.SearchHit;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述
 * Created by liubingguang on 2017/6/16.
 */
public class EsClientTest {

    private DataParam dataParam;

    @Before
    public void before() {
        System.out.println("initial data param");
        dataParam = new DataParam();
        dataParam.setIndex("website");
        dataParam.setType("blog");
    }

    @Test
    public void testInsertData() {
        Map<String, Object> dataMap = new HashMap<String, Object>(3);
        dataMap.put("id", 11);
        dataMap.put("title", "测试分词");
        dataMap.put("content", "发展中国家");

        dataParam.setDataMap(dataMap);
        dataParam.setDocId("11");
        try {
            IndexResponse indexResponse = EsClient.index(dataParam);
            if (indexResponse.isCreated()) {
                System.out.println("id=" + indexResponse.getId());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testGetDocById() {
        dataParam.setDocId("11");
        GetResponse response = EsClient.get(dataParam);
        System.out.println(response.getSourceAsString());
    }

    @Test
    public void testSearch() {
        try {
            dataParam.setSearchWord("中国");
            SearchResponse searchResponse = EsClient.query(dataParam);
            for (SearchHit hit : searchResponse.getHits()) {
                String id = String.valueOf(hit.getSource().get("id"));
                String title = String.valueOf(hit.getSource().get("title"));
                String content = String.valueOf(hit.getSource().get("content"));
                String score = String.valueOf(hit.getScore());
                System.out.println("id:" + id + ",title:" + title + ",content:" + content + ",score:" + score);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testPageSearch() {
        SearchResponse searchResponse = EsClient.pageSearch();
        Long hitsCount = searchResponse.getHits().totalHits();
        System.out.println("totalHits:" + hitsCount);
        for (SearchHit hit : searchResponse.getHits()) {
            System.out.println("docId:" + hit.getId());
            System.out.println("id:" + String.valueOf(hit.getSource().get("id"))
                    + ",title:" + String.valueOf(hit.getSource().get("title"))
                    + ",content:" + String.valueOf(hit.getSource().get("content")));
        }
    }

    @Test
    public void testUpdate() {
        dataParam.setDocId("10");
        GetResponse response = EsClient.get(dataParam);
        if (response == null) {
            System.out.println("response is null");
            return;
        }
        Map<String, Object> map = response.getSourceAsMap();
        map.put("title", String.valueOf(map.get("title")) + "~");

        dataParam.setDataMap(map);
        UpdateResponse updateResponse = EsClient.update(dataParam);
        map = updateResponse.getGetResult().getSource();
        System.out.println(map);
    }

    @Test
    public void testDelete() {
        dataParam.setDocId("AVywioMZjA4CE3gKEJ0p");
        DeleteResponse response = EsClient.delete(dataParam);
        System.out.println(response.getId());
    }

    @Test
    public void addDoc() throws IOException {
        dataParam.setIndex("company");
        dataParam.setType("employee");
        dataParam.setDocId("20");
        Map<String, Object> source = new HashMap<>();
        source.put("name", "tome");
        source.put("age", 20);
        dataParam.setDataMap(source);

        IndexResponse response = EsClient.index(dataParam);
        System.out.println(response);
    }

    public static void main(String[] args) {
        Preconditions.checkState(StringUtils.isNotBlank("2322323"), "null null");
    }

}
