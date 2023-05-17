package cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;

import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data.Document;
import cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.IndexManager;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class IndexController {

    public static IndexController createController(List<Document> documents) {
        IndexManager forTitle = new IndexManager();
        IndexManager forText = new IndexManager();

        return new IndexController(forTitle, forText);
    }

    private final IndexManager titleManager;

    private final IndexManager textManager;
}
