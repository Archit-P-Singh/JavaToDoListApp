package org.example.todolistapp;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class TodoController {
    @FXML private TextField taskField;
    @FXML private ListView<String> taskListView;

    @FXML
    private void addTask() {
        String task = taskField.getText();
        if (!task.isEmpty()) {
            taskListView.getItems().add(task);
            taskField.clear();
        }
    }

    @FXML
    private void deleteTask() {
        int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            taskListView.getItems().remove(selectedIndex);
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
}
