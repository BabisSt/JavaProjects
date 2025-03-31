package com.example;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main extends Application {
    final List<Label> taskLabels = new ArrayList<>(); // Store labels to remove later

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10); // Create VBox as the root layout
        Scene scene = new Scene(root, 500, 500); // Set width and height

        // Categories Label
        Label categoriesLabel = new Label("Task Categories");
        categoriesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        root.getChildren().add(categoriesLabel);

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

                    addTasks(tasks);

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

                        displayTasks(root, tasks);

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
                displayTasks(root, tasks);

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }

        }

        // Exit button
        Button b = new Button("Exit");
        b.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                primaryStage.hide();
            }

        });
        // add button
        root.getChildren().add(b);

        // Set scene and show stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Manager");
        primaryStage.show();
    }

    public void addTasks(HashMap<Category, ArrayList<Task>> tasks) {
        ArrayList<Task> workTasks = new ArrayList<>();
        // Create date instance
        // Create date instance
        Date d1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // Format and print current date
        String formattedDate1 = sdf.format(d1);

        // Add 2 days to d1
        Calendar cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.DAY_OF_MONTH, 2);
        Date d2 = cal.getTime();
        String formattedDate2 = sdf.format(d2);

        // Subtract 2 days from d1
        cal = Calendar.getInstance();
        cal.setTime(d1);
        cal.add(Calendar.DAY_OF_MONTH, -2);
        Date d3 = cal.getTime();
        String formattedDate3 = sdf.format(d3);

        // Use the new date in task creation
        workTasks.add(new Task("Get to work", false, formattedDate1, Priority.HIGH, Category.Work));
        workTasks.add(new Task("Finish Tasks", false, formattedDate2, Priority.HIGH, Category.Work));

        workTasks.add(new Task("Talk to Client", false, formattedDate3, Priority.MEDIUM, Category.Work));
        tasks.put(Category.Work, workTasks);

        ArrayList<Task> PersonalTasks = new ArrayList<>();
        PersonalTasks.add(new Task("Learn Java", false, formattedDate2, Priority.HIGH, Category.Personal));
        PersonalTasks.add(new Task("Make something to eat", false, formattedDate3, Priority.MEDIUM, Category.Personal));
        PersonalTasks.add(new Task("Clean the house", false, formattedDate1, Priority.LOW, Category.Personal));
        tasks.put(Category.Personal, PersonalTasks);

        ArrayList<Task> ShoppingTasks = new ArrayList<>();
        ShoppingTasks.add(new Task("Buy Groceries", false, formattedDate2, Priority.LOW,
                Category.Shopping));
        ShoppingTasks.add(new Task("Buy Meat", false, formattedDate1, Priority.MEDIUM, Category.Shopping));
        tasks.put(Category.Shopping, ShoppingTasks);

        ArrayList<Task> FitnessTasks = new ArrayList<>();
        FitnessTasks.add(new Task("Go for a walk", false, formattedDate3, Priority.MEDIUM,
                Category.Fitness));
        FitnessTasks.add(new Task("Renew gym membership", false, formattedDate1, Priority.MEDIUM, Category.Fitness));
        tasks.put(Category.Fitness, FitnessTasks);

    }

    public void displayTasks(VBox root, HashMap<Category, ArrayList<Task>> tasks) {
        HBox buttonContainer = new HBox(10); // 10px spacing between buttons
        buttonContainer.setAlignment(Pos.CENTER); // Center buttons in the line
        ArrayList<Button> buttons = new ArrayList<>(); // Store all buttons

        for (Category category : tasks.keySet()) {
            Button b = new Button(category.toString());

            // Set default button style
            b.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");

            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    // Reset all buttons to default color
                    for (Button btn : buttons) {
                        btn.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
                    }

                    // Change color of selected button
                    b.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");

                    // Clear previous task labels
                    root.getChildren().removeAll(taskLabels);
                    taskLabels.clear();

                    // Get tasks for the selected category
                    ArrayList<Task> taskList = tasks.get(category);
                    for (Task task : taskList) {
                        Label taskLabel = new Label(
                                task.getName() + " - " + task.getPriority() + " - " + task.getDueDate());
                        root.getChildren().add(taskLabel);
                        taskLabels.add(taskLabel);
                    }
                }
            });

            buttons.add(b); // Store button reference
            buttonContainer.getChildren().add(b); // Add button to HBox
        }

        root.getChildren().add(buttonContainer); // Add HBox to VBox
    }

}
