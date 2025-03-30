package com.example;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Priority {
    LOW,
    MEDIUM,
    HIGH
}

enum Category {
    Work,
    Personal,
    Shopping,
    Fitness;
}

public class Task {
    private static int id;
    private String name;
    private boolean isCompleted;

    @JsonProperty("priority")
    private Priority priority;

    @JsonProperty("category")
    private Category category;

    Task(String name, boolean isCompleted, Priority priority, Category category) {
        id++;
        this.name = name;
        this.isCompleted = isCompleted;
        this.category = category;
        this.priority = priority;
    }

    public void markAsDone() {
        this.isCompleted = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public boolean getIsCompleted() {
        return this.isCompleted;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public String toString() {
        return "Task{name='" + name + "', isCompleted=" + isCompleted + ", priority=" + priority + ", category="
                + category + '}';
    }

}