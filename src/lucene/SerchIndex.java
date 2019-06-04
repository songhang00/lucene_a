
package lucene;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Before;
import org.junit.Test;
import java.io.File;
public class SerchIndex {
    public IndexReader indexReader;
    public IndexSearcher indexSearcher;

    @Before
    public void init() throws Exception {
        Directory directory = FSDirectory.open(new File("F:\\index").toPath());
        indexReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
    }

    @Test
    public void testRangeQuery() throws Exception {
        IndexReader indexReader;
        IndexSearcher indexSearcher;
        Directory directory = FSDirectory.open(new File("F:\\index").toPath());
        indexReader = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(indexReader);

        //创建一个query对象

        //可查大小的 范围 RangeQuery() 没有方法
        Query query = new TermQuery(new Term("content", "spring"));
        TopDocs topDocs = indexSearcher.search(query, 3);
//        6.去查询结果的总记录数
        System.out.println("查询总记录数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//        8.打印文档内容
        for (ScoreDoc doc : scoreDocs) {
            //取文档id
            int docId = doc.doc;
            //根据id取对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            //  System.out.println(document.get("content"));
            System.out.println("-----------------------");

        }
        indexReader.close();


    }

    public void printResult(Query query) throws Exception {

        TopDocs topDocs = indexSearcher.search(query, 3);
//        6.去查询结果的总记录数
        System.out.println("查询总记录数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//        8.打印文档内容
        for (ScoreDoc doc : scoreDocs) {
            //取文档id
            int docId = doc.doc;
            //根据id取对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            //  System.out.println(document.get("content"));
            System.out.println("-----------------------");

        }

        indexReader.close();

    }

    @Test  //语句查询
    public void testQueryParse() throws Exception {


       IndexReader indexReader;

            Directory directory = FSDirectory.open(new File("F:\\index").toPath());
            indexReader = DirectoryReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
            //创建一个QAUERYPARSE对象
        QueryParser queryParser = new QueryParser("name", new StandardAnalyzer());
        Query query = queryParser.parse("songisaman");

        TopDocs topDocs = indexSearcher.search(query, 3);
//        6.去查询结果的总记录数
        System.out.println("查询总记录数：" + topDocs.totalHits);
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
//        8.打印文档内容
        for (ScoreDoc doc : scoreDocs) {
            //取文档id
            int docId = doc.doc;
            //根据id取对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
            //  System.out.println(document.get("content"));
            System.out.println("-----------------------");

        }

        indexReader.close();


    }
}