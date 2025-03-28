
public class Task {
    private int id;
    private String description;
    private boolean isCompleted;

    Task(int id, String description, boolean isCompleted) {
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    int getId() {
        return this.id;
    }

    String getDescription() {
        return this.description;
    }

    boolean getIsCompleted() {
        return this.isCompleted;
    }

    void markAsDone() {
        isCompleted = !isCompleted;
    }

    public String StringIt() {
        return "Printing description" + description;
    }
}