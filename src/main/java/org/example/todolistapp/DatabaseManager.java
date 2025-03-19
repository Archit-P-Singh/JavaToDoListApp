package org.example.todolistapp;

import java.sql.*;
import io.github.cdimascio.dotenv.Dotenv;

public class DatabaseManager {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "task TEXT NOT NULL," +
                    "due_date DATE," +
                    "category VARCHAR(50)" +
                    ");";
            stmt.executeUpdate(sql);
            System.out.println("✅ Database connected and table initialized.");

        } catch (SQLException e) {
            System.out.println("❌ Database connection failed.");
            e.printStackTrace();
        }
    }

    public static void insertTask(String task, String dueDate, String category) {
        String sql = "INSERT INTO tasks (task, due_date, category) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task);
            pstmt.setString(2, dueDate); // Can be null if not selected
            pstmt.setString(3, category);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet fetchTasks() {
        String sql = "SELECT * FROM tasks";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            return conn.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void deleteTask(int taskId) {
        String sql = "DELETE FROM tasks WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, taskId); // Use actual task ID, not ListView index
            int rowsAffected = stmt.executeUpdate(); // Execute the delete query

            if (rowsAffected == 0) {
                System.out.println("⚠ No task found with ID: " + taskId);
            } else {
                System.out.println("✅ Task deleted successfully! Task ID: " + taskId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static int getTaskId(String taskText) {
        String sql = "SELECT id FROM tasks WHERE task = ? LIMIT 1";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, taskText.trim()); // Ensure it only searches for task name
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int foundTaskId = rs.getInt("id");
                System.out.println("🔍 Found Task ID: " + foundTaskId + " for Task: " + taskText);
                return foundTaskId;
            } else {
                System.out.println("❌ No Task Found for: " + taskText);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Return -1 if no task is found
    }




}
