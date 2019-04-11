package com.somelogs.distribution.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.util.ArrayList;
import java.util.List;

/**
 * http://www.tianshouzhi.com/api/tutorials/lucene/75
 * Created by liubingguang on 2017/6/7.
 */
public class IndexSearch {

    public static void main(String args[]) throws Exception {
        // 搜索条件(不区分大小写)
        String queryString = "网上答案";
        //String queryString = "compass";


        //Directory directory = FSDirectory.open(new File("./indexDir/"));// 索引库目录
        Directory directory = FSDirectory.open(null);// 索引库目录
        Analyzer analyzer = new StandardAnalyzer();

        // 1、把查询字符串转为查询对象(存储的都是二进制文件，普通的String肯定无法查询，因此需要转换)
        QueryParser queryParser = new QueryParser("content", analyzer);// 只在标题里面查询
        Query query = queryParser.parse(queryString);

        // 2、查询，得到中间结果
        IndexReader indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

        TopDocs topDocs = indexSearcher.search(query, 100);// 根据指定查询条件查询，只返回前n条结果
        int count = topDocs.totalHits;// 总结果数
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;// 按照得分进行排序后的前n条结果的信息

        List<Article> articalList = new ArrayList<Article>();
        // 3、处理中间结果
        for (ScoreDoc scoreDoc : scoreDocs) {
            float score = scoreDoc.score;// 相关度得分
            int docId = scoreDoc.doc; // Document在数据库的内部编号(是唯一的，由lucene自动生成)

            // 根据编号取出真正的Document数据
            Document doc = indexSearcher.doc(docId);

            // 把Document转成Article
            Article article = new Article(
                    Integer.parseInt(doc.getField("id").stringValue()),//需要转为int型
                    doc.getField("title").stringValue(),
                    doc.getField("content").stringValue(),
                    doc.getField("author").stringValue()
            );

            articalList.add(article);
        }

        indexReader.close();//查询结束

        // 显示结果
        System.out.println("总结果数量为:" + articalList.size());
        for (Article article : articalList) {
            System.out.println("id=" + article.getId());
            System.out.println("title=" + article.getTitle());
            System.out.println("content=" + article.getContent());
        }
    }
}
