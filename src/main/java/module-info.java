module com.cinema {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;


    opens com.cinema to javafx.fxml;
    exports com.cinema;
}