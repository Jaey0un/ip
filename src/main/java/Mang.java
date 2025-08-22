import java.util.Scanner;

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
            String input = sc.nextLine().trim();

            // Exit
            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;

            } else if (input.equals("list")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("mark ")) {
                int idx = Integer.parseInt(input.substring(5).trim());
                if (idx >= 1 && idx <= count) {
                    tasks[idx - 1].markDone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + tasks[idx - 1]);
                    System.out.println("____________________________________________________________");
                }

            } else if (input.startsWith("unmark ")) {
                int idx = Integer.parseInt(input.substring(7).trim());
                if (idx >= 1 && idx <= count) {
                    tasks[idx - 1].markUndone();
                    System.out.println("____________________________________________________________");
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + tasks[idx - 1]);
                    System.out.println("____________________________________________________________");
                }

            } else if (input.startsWith("todo ")) {
                String desc = input.substring(5).trim();
                tasks[count] = new Todo(desc);
                count++;
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[count - 1]);
                System.out.println(" Now you have " + count + " tasks in the list.");
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("deadline ")) {
                String[] parts = input.substring(9).split("/by", 2);
                String desc = parts[0].trim();
                String by = (parts.length > 1) ? parts[1].trim() : "unspecified";
                tasks[count] = new Deadline(desc, by);
                count++;
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[count - 1]);
                System.out.println(" Now you have " + count + " tasks in the list.");
                System.out.println("____________________________________________________________");

            } else if (input.startsWith("event ")) {
                String[] parts = input.substring(6).split("/from|/to");
                if (parts.length >= 3) {
                    String desc = parts[0].trim();
                    String from = parts[1].trim();
                    String to = parts[2].trim();
                    tasks[count] = new Event(desc, from, to);
                } else {
                    tasks[count] = new Event(input.substring(6).trim(), "unspecified", "unspecified");
                }
                count++;
                System.out.println("____________________________________________________________");
                System.out.println(" Got it. I've added this task:");
                System.out.println("   " + tasks[count - 1]);
                System.out.println(" Now you have " + count + " tasks in the list.");
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