package org.example.todolistapp;
import  java.sql.*;

public class DatabaseManager{
    private  static  final String URL = "jdbc:mysql://localhost:3306/todo_db";
    private  static  final String USER = "root";
    private  static  final String PASSWORD = "Anohana@1";

    public  static  void initializeDatabase(){
        try(Connection conn = DriverManager.getConnection(URL, USER, PASSWORD); Statement stmt = conn.createStatement())
        {

            String sql = "CREATE TABLE IF NOT EXISTS tasks (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "task TEXT NOT NULL," +
                    "due_date DATE," +
                    "category VARCHAR(50)"+");";
            stmt.execute(sql);

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    //Insert a new task
    public static  void insertTask(String task, String dueDate, String category){
        String sql = "INSERT INTO tasks (task, due_date, category) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, task);
            pstmt.setString(2, dueDate);
            pstmt.setString(3, category);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Fetch tasks from the database
    public static ResultSet fetchTasks() {
        String sql = "SELECT * FROM tasks";
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
