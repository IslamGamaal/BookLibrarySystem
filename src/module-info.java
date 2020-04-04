module LibraryDatabaseMaster {
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.controls;
    requires java.desktop;
    requires java.sql;
    requires mysql.connector.java;
    opens view;
}