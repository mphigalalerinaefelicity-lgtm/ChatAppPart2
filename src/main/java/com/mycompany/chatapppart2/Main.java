package com.mycompany.chatapppart2;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner input = new Scanner(System.in);
    static ArrayList<Message> sentMessages = new ArrayList<>();
    static int count = 0;

    public static void main(String[] args) {

        System.out.println("Welcome to QuickChat.");

        // LOGIN (required by assignment)
        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        if (!username.equals("admin") || !password.equals("1234")) {
            System.out.println("Login failed.");
            return;
        }

        System.out.print("How many messages do you want to send? ");
        int limit = input.nextInt();
        input.nextLine();

        boolean running = true;

        while (running) {

            System.out.println("\n--- MENU ---");
            System.out.println("1. Send Message");
            System.out.println("2. Show Recently Sent (Coming Soon)");
            System.out.println("3. Quit");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {

                case 1:
                    sendMessages(limit);
                    break;

                case 2:
                    System.out.println("Coming Soon.");
                    break;

                case 3:
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    public static void sendMessages(int limit) {

        for (int i = 0; i < limit; i++) {

            count++;

            System.out.print("Enter recipient (+27...): ");
            String recipient = input.nextLine();

            System.out.print("Enter message (max 250 chars): ");
            String msg = input.nextLine();

            Message m = new Message(count, recipient, msg);

            if (!m.validMessage()) {
                System.out.println("Message exceeds 250 characters.");
                i--;
                continue;
            }

            if (!m.checkRecipient()) {
                System.out.println("Invalid recipient number.");
                i--;
                continue;
            }

            System.out.println("\n1. Send");
            System.out.println("2. Store");
            System.out.println("3. Discard");

            int option = input.nextInt();
            input.nextLine();

            if (option == 1) {
                sentMessages.add(m);
                System.out.println("Message successfully sent.");
                System.out.println(m.getDetails());

            } else if (option == 2) {
                System.out.println("Message successfully stored.");

            } else {
                System.out.println("Press 0 to delete message.");
            }
        }

        System.out.println("\nTotal sent: " + sentMessages.size());
    }
}
