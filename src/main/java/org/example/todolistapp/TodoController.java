package org.example.todolistapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TodoController {
    @FXML private TextField taskField;
    @FXML private DatePicker dueDatePicker;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ListView<String> taskListView;

    @FXML
    public void initialize() {
        DatabaseManager.initializeDatabase();
        loadTasks();
        loadCategories();
    }

    // Load tasks from the database
    private void loadTasks() {
        ResultSet resultSet = DatabaseManager.fetchTasks();
        try {
            while (resultSet != null && resultSet.next()) {
                String taskText = resultSet.getString("task");
                String dueDate = resultSet.getString("due_date");
                String category = resultSet.getString("category");

                String displayText = taskText + " | Due: " + (dueDate != null ? dueDate : "N/A") +
                        " | Category: " + (category != null ? category : "General");

                taskListView.getItems().add(displayText);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load categories into ComboBox
    private void loadCategories() {
        categoryComboBox.getItems().addAll("Work", "Personal", "Urgent", "Other");
        categoryComboBox.getSelectionModel().select("Personal"); // Default category
    }

    // Add task with due date & category
    @FXML
    private void addTask() {
        String task = taskField.getText();
        LocalDate dueDate = dueDatePicker.getValue();
        String category = categoryComboBox.getValue();

        if (!task.isEmpty()) {
            String formattedDueDate = (dueDate != null) ? dueDate.toString() : null;
            DatabaseManager.insertTask(task, formattedDueDate, category);
            taskListView.getItems().add(task + " | Due: " + formattedDueDate + " | Category: " + category);
            taskField.clear();
            dueDatePicker.setValue(null);
        }
    }



    @FXML
    private void deleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex != -1) {
            String selectedTask = extractTaskName(taskListView.getItems().get(selectedIndex));
            // Get task text
            System.out.println("📝 Selected task for deletion: " + selectedTask);

            int taskId = DatabaseManager.getTaskId(selectedTask); // Get correct task ID
            if (taskId != -1) {
                DatabaseManager.deleteTask(taskId); // Delete from DB
                taskListView.getItems().remove(selectedIndex); // Remove from UI
                System.out.println("✅ Task successfully removed from UI.");
            } else {
                System.out.println("⚠ Task not found in database.");
            }
        }
    }



    @FXML
    private void editTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            String newTask = taskField.getText();
            if (!newTask.isEmpty()) {
                taskListView.getItems().set(selectedIndex, newTask);
                taskField.clear();
            }
        }
    }
    private String extractTaskName(String fullTaskText) {
        return fullTaskText.split("\\|")[0].trim(); // Extracts only the task name
    }

}
