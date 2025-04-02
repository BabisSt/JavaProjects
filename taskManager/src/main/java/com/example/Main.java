package com.example;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;

public class Main extends Application {
    final List<Label> taskLabels = new ArrayList<>(); // Store labels to remove later

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10); // Create VBox as the root layout
        Scene scene = new Scene(root, 500, 500); // Set width and height

        Button add = new Button("Add task");
        Button exit = new Button("Exit");
        add.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");
        exit.setStyle("-fx-background-color: lightgray; -fx-text-fill: black;");

        // For add and exit
        HBox buttonContainer = new HBox(10); // 10px spacing between buttons
        buttonContainer.setAlignment(Pos.CENTER); // Center buttons in the line

        // for add task
        VBox newTaskContainer = new VBox(10); // VBox to stack elements vertically
        newTaskContainer.setAlignment(Pos.CENTER_LEFT);

        Button confirmAdd = new Button("Add");
        DatePicker datePicker = new DatePicker();
        // Define the date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ComboBox priorityBox = new ComboBox();
        ComboBox categoryBox = new ComboBox();
        // Set the custom string converter
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? formatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string != null && !string.isEmpty()) ? LocalDate.parse(string, formatter) : null;
            }
        });
        TextField newTaskName = new TextField();

        // Categories Label
        Label categoriesLabel = new Label("Task Categories");
        categoriesLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        root.getChildren().add(categoriesLabel);
        ArrayList<HBox> taskRows = new ArrayList<>(); // Store task rows for removal later
        // Define file
        File file = new File("tasks.json");
        HashMap<Category, ArrayList<Task>> tasks = new HashMap<>();
        // Check if file exists
        if (!file.exists()) {
            try {
                // Create the file
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getName());

                    // Create tasks

                    generateTasks(tasks);
                    sortTasks(tasks);
                    // Convert tasks to JSON and write to file
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.writeValue(file, tasks);

                    System.out.println("Tasks saved to " + file.getAbsolutePath());

                    // Read newly created tasks
                    try {
                        // Read the tasks from the file into the tasks variable using TypeReference
                        tasks.clear();
                        tasks.putAll(
                                objectMapper.readValue(file, new TypeReference<HashMap<Category, ArrayList<Task>>>() {
                                }));
                        final HashMap<Category, ArrayList<Task>> taskMap = tasks;

                        displayTasks(root, tasks, file, taskRows);

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
                tasks.clear();
                tasks.putAll(objectMapper.readValue(file, new TypeReference<HashMap<Category, ArrayList<Task>>>() {
                }));
                final HashMap<Category, ArrayList<Task>> taskMap = tasks;

                sortTasks(tasks);
                displayTasks(root, tasks, file, taskRows);

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                e.printStackTrace();
            }

        }

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                // Ensure only one set of items
                if (priorityBox.getItems().isEmpty()) {
                    priorityBox.getItems().addAll(Priority.HIGH, Priority.MEDIUM, Priority.LOW);
                }
                if (categoryBox.getItems().isEmpty()) {
                    categoryBox.getItems().addAll(Category.Work, Category.Shopping, Category.Personal,
                            Category.Fitness);
                }

                // Change button style
                add.setStyle("-fx-background-color: darkblue; -fx-text-fill: white;");

                // Remove previous UI elements
                newTaskContainer.getChildren().clear();
                root.getChildren().remove(newTaskContainer);
                root.getChildren().removeAll(taskRows);

                // Create new task object (but don't set values yet)
                Task newTask = new Task(null, false, null, null, null);

                // Add UI elements
                newTaskContainer.getChildren().addAll(categoryBox, priorityBox, newTaskName, datePicker, confirmAdd);
                root.getChildren().add(newTaskContainer);

                // Set values when "Confirm" is clicked
                confirmAdd.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent e) {
                        if (newTaskName.getText() != null && !newTaskName.getText().isEmpty()) {
                            newTask.setName(newTaskName.getText());
                        }

                        if (datePicker.getValue() != null) {
                            newTask.setDueDate(formatter.format(datePicker.getValue()).toString());
                        }

                        if (categoryBox.getValue() != null) {
                            newTask.setCategory((Category) categoryBox.getValue());
                        }

                        if (priorityBox.getValue() != null) {
                            newTask.setPriority((Priority) priorityBox.getValue());
                        }

                        // Save the task after setting values
                        saveTaskToJson(newTask, file);
                        updateTasks(file, tasks);
                        displayTasks(root, tasks, file, taskRows);
                    }
                });
            }
        });

        // Exit buttom
        exit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                primaryStage.hide();
            }

        });
        // add button
        buttonContainer.getChildren().add(add); // Add button to HBox
        buttonContainer.getChildren().add(exit); // Add button to HBox
        root.getChildren().add(buttonContainer);

        // Set scene and show stage
        primaryStage.setScene(scene);
        primaryStage.setTitle("Task Manager");
        primaryStage.show();
    }

    public void generateTasks(HashMap<Category, ArrayList<Task>> tasks) {
        ArrayList<Task> workTasks = new ArrayList<>();
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

    public void displayTasks(VBox root, HashMap<Category, ArrayList<Task>> tasks, File file, ArrayList<HBox> taskRows) {
        // Remove existing category button row if it exists
        root.getChildren().removeIf(node -> node instanceof HBox);

        HBox buttonContainer = new HBox(10); // 10px spacing between buttons
        buttonContainer.setAlignment(Pos.CENTER); // Center buttons in the line
        ArrayList<Button> buttons = new ArrayList<>(); // Store all buttons

        // Add category buttons
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

                    // Clear existing tasks before displaying new ones
                    root.getChildren().removeIf(node -> node instanceof VBox);
                    root.getChildren().removeAll(taskRows);
                    taskRows.clear();

                    ArrayList<Task> taskList = tasks.get(category);
                    for (Task task : taskList) {
                        Label taskLabel = new Label(
                                task.getName() + " - " + task.getPriority() + " - " + task.getDueDate());
                        Button onCompleteButton = new Button("✔");

                        if (!task.getIsCompleted())
                            onCompleteButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
                        else {
                            taskLabel.setStyle("-fx-text-fill: gray; -fx-strikethrough: true;");
                            onCompleteButton.setDisable(true);
                        }

                        HBox taskRow = new HBox(10);
                        taskRow.getChildren().addAll(taskLabel, onCompleteButton);
                        taskRows.add(taskRow);
                        root.getChildren().add(taskRow);
                    }
                }
            });

            buttons.add(b);
            buttonContainer.getChildren().add(b);
        }

        // Add buttonContainer back to root only once
        root.getChildren().add(buttonContainer);
    }

    // update the file with the changes
    private void saveTasksToJson(HashMap<Category, ArrayList<Task>> tasks, File file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            sortTasks(tasks);
            objectMapper.writeValue(file, tasks); // Save tasks as JSON
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveTaskToJson(Task newTask, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<Category, ArrayList<Task>> tasks = new HashMap<>();

        // Read existing tasks if the file is not empty
        if (file.exists() && file.length() > 0) {
            try {
                tasks.clear();
                tasks.putAll(objectMapper.readValue(file, new TypeReference<HashMap<Category, ArrayList<Task>>>() {
                }));

            } catch (IOException e) {
                System.out.println("Error reading existing tasks.");
                e.printStackTrace();
            }
        }

        // Add new task to the appropriate category
        tasks.computeIfAbsent(newTask.getCategory(), k -> new ArrayList<>()).add(newTask);

        // Write updated tasks back to the file
        try {
            objectMapper.writeValue(file, tasks);

        } catch (IOException e) {
            System.out.println("Error saving task.");
            e.printStackTrace();
        }
    }

    private void updateTasks(File file, HashMap<Category, ArrayList<Task>> tasks) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            tasks.clear();
            tasks.putAll(objectMapper.readValue(file, new TypeReference<HashMap<Category, ArrayList<Task>>>() {
            }));
        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }

    private void sortTasks(HashMap<Category, ArrayList<Task>> tasks) {
        for (Category category : tasks.keySet()) {
            // sort tasks based on priority
            ArrayList<Task> taskList = tasks.get(category);
            taskList.sort(Comparator.comparing(Task::getPriority));
            Collections.reverse(taskList);
        }

    }

}
