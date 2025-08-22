import java.util.Scanner;

public class Mang {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int count = 0;

        // Greet
        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Mang, your friendly neighborhood chatbot!");
        System.out.println(" What can I do for you?");
        System.out.println(" Type 'bye' whenever you want to end our chat.");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine();

            // Exit
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")){
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else if (input.startsWith("mark ")) {
                System.out.println("____________________________________________________________");
                try {
                    int idx = Integer.parseInt(input.substring(5).trim()); // 1-based
                    if (idx < 1 || idx > count) {
                        System.out.println(" Invalid task number.");
                    } else {
                        tasks[idx - 1].markDone();
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println("   " + tasks[idx - 1]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a valid task number, e.g., 'mark 2'.");
                }
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("unmark ")) {
                System.out.println("____________________________________________________________");
                try {
                    int idx = Integer.parseInt(input.substring(7).trim()); // 1-based
                    if (idx < 1 || idx > count) {
                        System.out.println(" Invalid task number.");
                    } else {
                        tasks[idx - 1].markUndone();
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println("   " + tasks[idx - 1]);
                    }
                } catch (NumberFormatException e) {
                    System.out.println(" Please provide a valid task number, e.g., 'unmark 2'.");
                }
                System.out.println("____________________________________________________________");

            } else {
                if (count < 100) {
                    tasks[count] = new Task(input);
                    count++;
                    System.out.println("____________________________________________________________");
                    System.out.println(" added: " + input);
                    System.out.println("____________________________________________________________");
                } else {
                    System.out.println("____________________________________________________________");
                    System.out.println(" Sorry, I can only store up to 100 items.");
                    System.out.println("____________________________________________________________");
                }
            }
        }

        sc.close();

    }
}
