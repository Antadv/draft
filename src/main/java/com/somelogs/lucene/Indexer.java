package com.somelogs.lucene;

import java.io.IOException;

/**
 * 描述
 * Created by liubingguang on 2017/6/7.
 */
public class Indexer {

    public static void main(String[] args) throws IOException {
        //Article artical = new Article(1, "Lucene全文检索框架",
        //        "Lucene如果信息检索系统在用户发出了检索请求后再去网上找答案","田守枝");
        //
        //Document document = new Document();
        //document.add(new LongField("id", artical.getId(), Field.Store.YES));
        //document.add(new TextField("title", artical.getTitle(), Field.Store.YES));
        //document.add(new StringField("author", artical.getAuthor(), Field.Store.YES));
        //document.add(new TextField("content", artical.getContent(), Field.Store.YES));
        //
        //Directory directory = FSDirectory.open(new File("./indexDir/"));
        //Analyzer analyzer = new StandardAnalyzer();
        //IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //indexWriter.addDocument(document);
        //indexWriter.close();
    }
}
