
public class Task {
    private int id;
    private String description;
    private boolean isCompleted;

    Task(int id, String description, boolean isCompleted) {
        this.id = id;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    void markAsDone() {
        isCompleted = !isCompleted;
        System.out.println(isCompleted);
    }

    public String StringIt() {
        return "Printing description" + description;
    }
}