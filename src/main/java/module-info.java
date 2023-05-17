module cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires com.google.gson;

    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork to javafx.fxml, com.google.gson;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.data to com.google.gson;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.core.io to com.google.gson;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork;
    exports cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui;
    opens cz.zcu.fav.kiv.ir.mjakubas.irsemestralwork.gui to com.google.gson, javafx.fxml;
}