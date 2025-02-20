module org.example.todolistapp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires  java.dotenv;

    opens org.example.todolistapp to javafx.fxml;
    exports org.example.todolistapp;
}