package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(); // Create VBox as the root layout
        Scene scene = new Scene(root, 400, 300); // Set width and height

        // Define file
        File file = new File("tasks.json");

        // Check if file exists
        if (!file.exists()) {
            try {
                // Create the file
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());

                    // Create tasks
                    HashMap<Category, ArrayList<Task>> tasks = new HashMap<>();

                    // TODO : make better file with more Categories
                    ArrayList<Task> workTasks = new ArrayList<>();
                    workTasks.add(new Task("Get to work", false, Priority.HIGH, Category.Work));
                    // workTasks.add(new Task("Learn Java", false, Priority.HIGH,
                    // Category.Personal));
                    // workTasks.add(new Task("Buy Groceries", false, Priority.LOW,
                    // Category.Shopping));
                    // workTasks.add(new Task("Go to gym", false, Priority.MEDIUM,
                    // Category.Fitness));

                    tasks.put(Category.Work, workTasks);

                    // Convert tasks to JSON and write to file
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, tasks);

                    System.out.println("Tasks saved to " + file.getAbsolutePath());
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            try {
                // Create ObjectMapper to read JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Read the tasks from the file into the tasks variable
                HashMap<Category, ArrayList<Task>> tasks = objectMapper.readValue(file,
                        objectMapper.getTypeFactory().constructMapType(HashMap.class, Category.class, ArrayList.class));

                // Display tasks in the VBox
                // for (Category category : tasks.keySet()) {
                // ArrayList<Task> taskList = tasks.get(category);
                // for (int i = 0; i < taskList.size(); i++) {
                // // Create a label for each task and add it to the VBox
                // Label taskLabel = new Label(
                // taskList.get(i).getName() + " - " + taskList.get(i).getPriority() + " - "
                // + taskList.get(i).getCategory());
                // root.getChildren().add(taskLabel);
                // }

                // }

                for (Category category : tasks.keySet()) {
                    ArrayList<Task> taskList = tasks.get(category);
                    for (int i = 0; i < taskList.size(); i++) {
                        // Access each task
                        Task task = taskList.get(i);

                        // TODO : read the file and print the results
                        // Print task name (you can also add this to the VBox as a Label)
                        System.out.println("AAAA " + task.getName()); // Print the name of the task
                    }
                }

                System.out.println("Tasks loaded from file: " + tasks);

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }
        }

        // create a button
        Button b = new Button("button");
        // add button
        root.getChildren().add(b);

        // Set scene and show stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Manager");
        primaryStage.show();
    }
}
