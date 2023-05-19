package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.IndexManager;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.BasicPreprocessing;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.CzechStemmerAggressive;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.AdvancedTokenizer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.HashIndex;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.InvalidFieldIndex;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.BooleanQueryProcessor;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws InvalidFieldIndex {
//        HashIndex index = new HashIndex();
//        IndexManager indexManager = new IndexManager(new DocumentProcessor(new BasicPreprocessing(new CzechStemmerAggressive(), new AdvancedTokenizer())), index);
//        BooleanQueryProcessor queryProcessor = new BooleanQueryProcessor(index);
//
//        Document[] documents = new Document[]{
//                new Document(1, null, "w1 w2", "w1 w2"),
//                new Document(2, null, "w1 w2 w3", "w1 w2 w3"),
//                new Document(3, null, "w3", "w3"),
//        };
//
//        indexManager.indexDocuments(Arrays.stream(documents).toList(), 0);
//
//        queryProcessor.performQuery("w1 AND w3 OR (w1 AND w2)", 1, indexManager.getDocumentProcessor());
        String opText = "asodfpjoasjdf paosdfjk poasjfpas fposajdfsad fojsadp jfsapjdf ";
        System.out.println(AdvancedTokenizer.removeHtmlTags(opText));
    }
}
