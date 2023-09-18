import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.StoredFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.BoostQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.store.Directory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {

        //the directory is where the data is really stored
        Directory directory = new ByteBuffersDirectory();
//        Directory directory = FSDirectory.open(Path.of("./lucene_index"));
        //analyzer analyzes text: tokenizes, removes stopwords and applies stemming
        Analyzer analyzer = new EnglishAnalyzer();


        indexData(directory, analyzer);


        DirectoryReader reader = DirectoryReader.open(directory);
        IndexSearcher searcher = new IndexSearcher(reader);


//        search1(searcher);
//        search2(searcher);
//        search3(searcher, analyzer);
    }

    private static void indexData(Directory directory, Analyzer analyzer) throws IOException {
        //index
        try (IndexWriter writer = new IndexWriter(directory, new IndexWriterConfig(analyzer))) {
            Document doc = new Document();
            doc.add(new TextField("name", "Dragons", Field.Store.YES));
            doc.add(new TextField("description", "WOW, so high fantasy!", Field.Store.NO));
            doc.add(new StringField("type", "fantasy", Field.Store.NO));
            writer.addDocument(doc);
            doc = new Document();
            doc.add(new TextField("name", "Orcs", Field.Store.YES));
            doc.add(new TextField("description", "dark fantasy is scary", Field.Store.NO));
            doc.add(new StringField("type", "fantasy", Field.Store.NO));
            writer.addDocument(doc);
            doc = new Document();
            doc.add(new TextField("name", "He is back!", Field.Store.YES));
            doc.add(new TextField("description", "bambam BOOOOM!", Field.Store.NO));
            doc.add(new StringField("type", "action", Field.Store.NO));
            writer.addDocument(doc);
        }
    }

    private static void search1(IndexSearcher searcher) throws IOException {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder
                .add(new TermQuery(new Term("type", "fantasy")), BooleanClause.Occur.MUST)
                .add(new WildcardQuery(new Term("description", "dark*")), BooleanClause.Occur.SHOULD)
        ;
        Query query = builder.build();

        TopDocs result = searcher.search(query, 10);

        explainResult(searcher, query, result);

        printResult(searcher, result);
    }

    private static void search2(IndexSearcher searcher) throws IOException {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        builder
                .add(new WildcardQuery(new Term("description", "fantasy*")), BooleanClause.Occur.MUST)
        ;
        Query query = builder.build();

        TopDocs result = searcher.search(query, 10);

        explainResult(searcher, query, result);

        printResult(searcher, result);
    }

    private static void search3(IndexSearcher searcher, Analyzer analyzer) throws IOException {
        String searchText = "I want dragons and dark fantasy!";

        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        try (TokenStream stream = analyzer.tokenStream("description", searchText)) {
            CharTermAttribute charTermAttribute = stream.addAttribute(CharTermAttribute.class);
            stream.reset();

            while (stream.incrementToken()) {
                System.out.println("searchterm added: " + charTermAttribute.toString());
                builder.add(
                        new BoostQuery(
                                new WildcardQuery(new Term("name", charTermAttribute.toString() + "*")),
                                4f
                        ),
                        BooleanClause.Occur.SHOULD);
                builder.add(
                        new WildcardQuery(new Term("description", charTermAttribute.toString() + "*")),
                        BooleanClause.Occur.SHOULD);
            }

        }
        Query query = builder.build();

        TopDocs result = searcher.search(query, 10);

        explainResult(searcher, query, result);

        printResult(searcher, result);
    }

    private static void explainResult(IndexSearcher searcher, Query query, TopDocs result) throws IOException {
        StoredFields storedFields = searcher.storedFields();
        for (ScoreDoc scoreDoc : result.scoreDocs) {
            System.out.println(storedFields.document(scoreDoc.doc).get("name"));
            System.out.println(searcher.explain(query, scoreDoc.doc));
        }
    }

    private static void printResult(IndexSearcher searcher, TopDocs result) throws IOException {
        StoredFields storedFields = searcher.storedFields();

        List<ScoreDoc> results = Arrays.asList(result.scoreDocs);

        results.sort(Comparator.comparing((ScoreDoc scoreDoc) -> scoreDoc.score).reversed());

        for (ScoreDoc scoreDoc : results) {
            System.out.println(storedFields.document(scoreDoc.doc).get("name"));
        }
    }
}