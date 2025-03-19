package org.example.todolistapp;
import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/todo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 300);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        primaryStage.setTitle("To-Do List");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        // Run the database connection test before launching JavaFX
        testDatabaseConnection();

        // Launch the JavaFX application
        launch();
    }

    private static void testDatabaseConnection() {
        Dotenv dotenv = Dotenv.load();
        String URL = dotenv.get("DB_URL");
        String USER = dotenv.get("DB_USER");
        String PASSWORD = dotenv.get("DB_PASSWORD");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            if (conn != null) {
                System.out.println("✅ Connected to the remote MySQL database!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed.");
            System.exit(-1);
            //System.out.println("Opening in offline mode.");
        }
    }
}
