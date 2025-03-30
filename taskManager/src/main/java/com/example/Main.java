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

import com.fasterxml.jackson.core.type.TypeReference;
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

                    ArrayList<Task> workTasks = new ArrayList<>();
                    workTasks.add(new Task("Get to work", false, Priority.HIGH, Category.Work));
                    workTasks.add(new Task("Finish Tasks", false, Priority.HIGH, Category.Work));
                    workTasks.add(new Task("Talk to Client", false, Priority.MEDIUM, Category.Work));
                    tasks.put(Category.Work, workTasks);

                    ArrayList<Task> PersonalTasks = new ArrayList<>();
                    PersonalTasks.add(new Task("Learn Java", false, Priority.HIGH, Category.Personal));
                    PersonalTasks.add(new Task("Make something to eat", false, Priority.MEDIUM, Category.Personal));
                    PersonalTasks.add(new Task("Clean the house", false, Priority.LOW, Category.Personal));
                    tasks.put(Category.Personal, PersonalTasks);

                    ArrayList<Task> ShoppingTasks = new ArrayList<>();
                    ShoppingTasks.add(new Task("Buy Groceries", false, Priority.LOW,
                            Category.Shopping));
                    ShoppingTasks.add(new Task("Buy Meat", false, Priority.MEDIUM, Category.Shopping));
                    tasks.put(Category.Shopping, ShoppingTasks);

                    ArrayList<Task> FitnessTasks = new ArrayList<>();
                    FitnessTasks.add(new Task("Go for a walk", false, Priority.MEDIUM,
                            Category.Fitness));
                    FitnessTasks.add(new Task("Renew gym membership", false, Priority.MEDIUM, Category.Fitness));
                    tasks.put(Category.Fitness, FitnessTasks);

                    // Convert tasks to JSON and write to file
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, tasks);

                    System.out.println("Tasks saved to " + file.getAbsolutePath());

                    // Read newly created tasks
                    try {
                        // Read the tasks from the file into the tasks variable using TypeReference
                        tasks = objectMapper.readValue(file,
                                new TypeReference<HashMap<Category, ArrayList<Task>>>() {
                                });

                        // Display tasks in the VBox
                        for (Category category : tasks.keySet()) {
                            ArrayList<Task> taskList = tasks.get(category);
                            for (Task task : taskList) {
                                // Create a label for each task and add it to the VBox
                                Label taskLabel = new Label(
                                        task.getName() + " - " + task.getPriority() + " - "
                                                + task.getCategory());
                                root.getChildren().add(taskLabel);
                            }

                        }

                    } catch (IOException e) {
                        System.out.println("An error occurred while reading the file.");
                        e.printStackTrace();
                    }
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            try {
                // Create ObjectMapper to read JSON
                ObjectMapper objectMapper = new ObjectMapper();

                // Read the tasks from the file into the tasks variable using TypeReference
                HashMap<Category, ArrayList<Task>> tasks = objectMapper.readValue(file,
                        new TypeReference<HashMap<Category, ArrayList<Task>>>() {
                        });

                // Display tasks in the VBox
                for (Category category : tasks.keySet()) {
                    ArrayList<Task> taskList = tasks.get(category);
                    for (Task task : taskList) {
                        // Create a label for each task and add it to the VBox
                        Label taskLabel = new Label(
                                task.getName() + " - " + task.getPriority() + " - "
                                        + task.getCategory());
                        root.getChildren().add(taskLabel);
                    }
                }

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
