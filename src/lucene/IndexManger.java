package lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class IndexManger {
//    public IndexWriter indexWriter;
//    @Before
//    public void init() throws Exception{
//        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\index").toPath()),
//                new IndexWriterConfig(new StandardAnalyzer()));
//
//    }


    @Test
    public void  addDocument() throws  Exception{

        //创建一个IndexWriter对象，需要使用IKANALYZER分析器
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\index").toPath()),
                new IndexWriterConfig(new StandardAnalyzer()));

        //代码中使用ikanalyzer   //2012只支持lucene 6--
        //创建一个Document对象
        Document document = new Document();
        //向document 对象中添加域
        document.add(new TextField("name","新添加的文件", Field.Store.YES));
        document.add(new TextField("content","新添加的文件内容", Field.Store.YES));
        document.add(new StoredField("path","f:/temp"));
    //将文档写入索引库
        indexWriter.addDocument(document);
        indexWriter.close();
    }
    @Test
    public  void  deleteAllDocument() throws Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\index").toPath()),
                new IndexWriterConfig(new StandardAnalyzer()));
        indexWriter.deleteAll();
        indexWriter.close();
    }
    @Test
    public void  deleteOrQuery() throws  Exception{
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\index").toPath()),
                new IndexWriterConfig(new StandardAnalyzer()));

    indexWriter.deleteDocuments(new Term("content","spring"));
    indexWriter.close();
        System.out.println("查询删除结束");
    }
    @Test
    public  void updateDocunment() throws  Exception{
        /**
         * 先删除后添加
         */
        IndexWriter indexWriter = new IndexWriter(FSDirectory.open(new File("F:\\index").toPath()),
                new IndexWriterConfig(new StandardAnalyzer()));

        Document document=new Document();
        document.add(new TextField("content","更新后的文档",Field.Store.YES));
        document.add(new TextField("name2","更新后的文档2",Field.Store.YES));
        document.add(new TextField("name3","更新后的文档3",Field.Store.YES));

        indexWriter.updateDocument(new Term("content","spring"),document);
        indexWriter.close();
    }
}
