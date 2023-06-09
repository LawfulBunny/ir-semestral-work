module cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires com.google.gson;
    requires com.google.common;
    requires org.jsoup;
    requires xsoup;
    requires java.desktop;

    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork to javafx.fxml, com.google.gson;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data to com.google.gson;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io to com.google.gson;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui to com.google.gson, javafx.fxml;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage to com.google.gson, javafx.fxml;

    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui.storage;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.index;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.query.utils;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.tokenizer;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.index.document.stemmer;
}