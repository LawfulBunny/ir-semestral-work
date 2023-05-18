package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.IndexManager;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.BasicPreprocessing;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.DocumentProcessor;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer.CzechStemmerAggressive;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer.AdvancedTokenizer;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index.HashIndex;

/**
 * Contains methods for creating default index controllers.
 */
public class PreparedIndexController {

    /**
     * Creates default czech index controller.
     *
     * @return new czech index controller.
     */
    public static IndexController createCzechIndexController() {
        DocumentProcessor documentProcessor = new DocumentProcessor(
                new BasicPreprocessing(new CzechStemmerAggressive(), new AdvancedTokenizer())
        );

        IndexManager titleIndexManager = new IndexManager(documentProcessor, new HashIndex());
        IndexManager textIndexManager = new IndexManager(documentProcessor, new HashIndex());
        return new IndexController(titleIndexManager, textIndexManager);
    }
}
