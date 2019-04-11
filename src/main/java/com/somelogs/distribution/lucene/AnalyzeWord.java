package com.somelogs.distribution.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

/**
 * 描述
 * Created by liubingguang on 2017/6/7.
 */
public class AnalyzeWord {

    @Test
    public void testWord() throws IOException {
        Analyzer analyzer=new StandardAnalyzer();
        String text = "An IndexWriter creates and maintains an index";
        TokenStream tokenStream = analyzer.tokenStream("", text);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            System.out.println(charTermAttribute);

        }
    }

    @Test
    public void testChineseWord() throws Exception {
    }

    public void testAnalyzer(Analyzer analyzer, String text) throws Exception {
        System.out.println("当前使用的分词器：" + analyzer.getClass());

        TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
        tokenStream.addAttribute(CharTermAttribute.class);

        while (tokenStream.incrementToken()) {
            CharTermAttribute termAttribute = tokenStream.getAttribute(CharTermAttribute.class);
            System.out.println(termAttribute);
        }
    }
}
