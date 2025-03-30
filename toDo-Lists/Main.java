import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = "";
        String readOption = ""; // to avoid exiting loop because of selection
        ArrayList<Task> tasks = new ArrayList<>(); // this way the tasks.size() wont crash the programm
        while (!option.equalsIgnoreCase("4")) {

            // print tasks
            System.out.println("\n===== TO-DO LIST =====");
            if (tasks.size() != 0) {
                printTasks(tasks);
            }

            // print menu
            Scanner selection = new Scanner(System.in);
            System.out.println("Options");
            System.out.println("1. Add task");
            System.out.println("2. Mark task as done");
            System.out.println("3. Delete task");
            System.out.println("4. Exit");

            option = selection.nextLine();

            // options

            // add
            if (option.equalsIgnoreCase("1")) {
                Scanner tasksName = new Scanner(System.in);
                System.out.println("Tasks name :");
                readOption = tasksName.nextLine();
                Task newTask = new Task(tasks.size() + 1, readOption, false);
                tasks.add(newTask);
                // mark as done
            } else if (option.equalsIgnoreCase("2")) {
                if (tasks.size() == 0) {
                    System.out.println("No task to mark");
                } else {
                    System.out.println("Select task");
                    Scanner tasksName = new Scanner(System.in);
                    printTasks(tasks);
                    readOption = tasksName.nextLine();
                    if (tasks.get(Integer.parseInt(readOption)).getIsCompleted()) {
                        System.out.println("Task is already completed");
                    } else {
                        tasks.get(Integer.parseInt(readOption)).markAsDone();
                    }

                }
                // delete
            } else if (option.equalsIgnoreCase("3")) {
                if (tasks.size() == 0) {
                    System.out.println("No task to delete");
                } else {
                    System.out.println("Select task");
                    Scanner tasksName = new Scanner(System.in);
                    printTasks(tasks);
                    readOption = tasksName.nextLine();
                    if (Integer.parseInt(readOption) >= tasks.size()) {
                        System.out.println("This task doesn't exist");
                    } else {
                        tasks.remove(Integer.parseInt(readOption));
                    }
                }
            } else {
                System.out.println("Give a valid option");
            }

        }

    }

    public static void printTasks(ArrayList<Task> tasks) {

        for (int i = 0; i < tasks.size(); i++) {
            String completeMark = tasks.get(i).getIsCompleted() ? "X" : " ";
            System.out
                    .println(i + "." + " [" + completeMark + "] "
                            + tasks.get(i).getDescription());
        }
    }
}
