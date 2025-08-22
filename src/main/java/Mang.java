import java.util.Scanner;

public class Mang {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] tasks = new String[100];
        int count = 0;

        System.out.println("____________________________________________________________");
        System.out.println(" Hello! I'm Mang, your friendly neighborhood chatbot!");
        System.out.println(" What can I do for you?");
        System.out.println(" Type 'bye' whenever you want to end our chat.");
        System.out.println("____________________________________________________________");

        while (true) {
            String input = sc.nextLine();

            if (input.equals("bye")) {
                System.out.println("____________________________________________________________");
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            } else if (input.equals("list")){
                System.out.println("____________________________________________________________");
                for (int i = 0; i < count; i++) {
                    System.out.println(" " + (i + 1) + ". " + tasks[i]);
                }
                System.out.println("____________________________________________________________");
            } else {
                if (count < 100) {
                    tasks[count] = input;
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
