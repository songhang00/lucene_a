package lucene;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;

public class LuceneFirst {
    @Test
    public void createIndex() throws Exception {
        /**
         创建一个Director对象，指定索引保存的位置
         基于Directory对象创建一个IndexWriter对象
         读取磁盘上的文件，对应每个文件创建一个文档对象
         向文档对象中添加域
         把文档对象写入索引库
         关闭indexWriter对象
         */

        Directory directory = FSDirectory.open(new File("F:\\index").toPath());

      //代码中使用ikanalyzer   //2012只支持lucene 6--
        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());


        IndexWriter indexWriter = new IndexWriter(directory, config);
        File dir = new File("F://datasource");
        File[] files = dir.listFiles();
        for(File f : files){
//        获取文件名
            String fileName = f.getName();
            String filePath = f.getPath();
            String fileContent = FileUtils.readFileToString(f, "utf-8");
            Field fieldName=new TextField("name",fileName, Field.Store.YES);
            Field fieldPath=new TextField("path",filePath, Field.Store.YES);
            Field fieldContent=new TextField("content",fileContent, Field.Store.YES);
//            创建文档对象
            Document document = new Document();
            document.add(fieldName);
            document.add(fieldPath);
            document.add(fieldContent);
//            将文档对象写入索引库
            indexWriter.addDocument(document);
        }
//        关闭indexWriter对象
indexWriter.close();
    }

    @Test
    public void searchIndex() throws  Exception{

//        1.创建一个Directory对象，指定索引库的位置
        Directory directory = FSDirectory.open(new File("F:\\index").toPath());

//        2.创建一个IndexReader对象
        DirectoryReader indexReader = DirectoryReader.open(directory);
//        3.创建一个IndexSearcher对象，构造方法中的参数indexReader对象
        IndexSearcher indexSearcher=new IndexSearcher(indexReader);
//        4.创建一个Query对象，TermQuery
        Query query=new TermQuery(new Term("content","spring"));
//        5.执行查询，得到一个TopDocs对象
//        参数一查询对象 参数二返回最大记录*数；
         TopDocs topDocs= indexSearcher.search(query,3);
//        6.去查询结果的总记录数
        System.out.println("查询总记录数："+topDocs.totalHits);
//        7.取文档列表
        ScoreDoc[] scoreDocs=topDocs.scoreDocs;
//        8.打印文档内容
        for(ScoreDoc doc: scoreDocs){
            //取文档id
            int docId=doc.doc;
            //根据id取对象
            Document document = indexSearcher.doc(docId);
            System.out.println(document.get("name"));
            System.out.println(document.get("path"));
          //  System.out.println(document.get("content"));
            System.out.println("-----------------------");

        }
//        9.关闭对象
        indexReader.close();
    }


    @Test
    public void testTokenStream() throws Exception{

//        1>创建一个Analyzer对象，StandarAnalyzer对象
       StandardAnalyzer analyzer= new StandardAnalyzer();
//        Analyzer analyze= new IKAnalyzer();  //2012只支持lucene 4--

//        2>使用分析器对象tokenStream方法获得一个TokenStream对象
        TokenStream tokenStream=analyzer.tokenStream("","纠纷中文词典第六届spring somng");

//        3>向TokenStream对象中设置一个引用，相当于数一个指针
        CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//        4>调用TokenStream的reset方法，必须使用
        tokenStream.reset();
//        5>使用while循环使用TokenStream对象
        while (tokenStream.incrementToken()){

            System.out.println(charTermAttribute.toString());
        }
//        6>关闭TokenStream对象
        tokenStream.close();


    }




}



