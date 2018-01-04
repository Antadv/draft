package com.somelogs.lucene;

import lombok.Data;

/**
 * 描述
 * Created by liubingguang on 2017/6/7.
 */
@Data
public class Article {

    private Integer Id;
    private String title;
    private String content;
    private String author;

    public Article(Integer id, String title, String content, String author) {
        Id = id;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "Id=" + Id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
