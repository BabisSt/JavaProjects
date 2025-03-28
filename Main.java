import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String option = "";
        ArrayList<Task> task = null;
        while (!option.equalsIgnoreCase("4")) {
            System.out.println("\n===== TO-DO LIST =====");
            Scanner selection = new Scanner(System.in);
            System.out.println("Options");
            System.out.println("1. Add task");
            System.out.println("2. Mark task as done");
            System.out.println("3. Delete task");
            System.out.println("4. Exit");

            option = selection.nextLine();
            if (option.equalsIgnoreCase("1")) {
                System.out.println("AAAA");
            }

        }

    }
}
