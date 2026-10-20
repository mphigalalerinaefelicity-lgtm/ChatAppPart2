package com.mycompany.chatapppart2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);
    private static final List<Message> sentMessages = new ArrayList<>();
    private static int messageCounter = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to QuickChat.");

        if (!login()) {
            System.out.println("Login failed. Goodbye.");
            return;
        }

        int messageLimit = readPositiveInteger(
                "How many messages would you like to send? ");

        boolean running = true;

        while (running) {
            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1. Send Message");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Quit");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            switch (choice) {
                case "1":
                    sendMessages(messageLimit);
                    break;

                case "2":
                    showSentMessages();
                    break;

                case "3":
                    running = false;
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Choose 1, 2 or 3.");
            }
        }
    }

    private static boolean login() {
        System.out.print("Username: ");
        String username = input.nextLine();

        System.out.print("Password: ");
        String password = input.nextLine();

        // Replace these example credentials if your assignment
        // specifies different login credentials.
        return username.equals("admin") && password.equals("1234");
    }

    private static int readPositiveInteger(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine().trim();

            try {
                int number = Integer.parseInt(value);

                if (number > 0) {
                    return number;
                }

                System.out.println("Enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    public static void sendMessages(int messageLimit) {
        int processedMessages = 0;

        while (processedMessages < messageLimit) {
            System.out.println("\n--- New Message ---");

            System.out.print("Enter recipient (+27 followed by 9 digits): ");
            String recipient = input.nextLine().trim();

            System.out.print("Enter message (1-250 characters): ");
            String text = input.nextLine();

            // Use the next message number only for a valid message.
            Message message = new Message(
                    messageCounter + 1, recipient, text);

            if (!message.checkMessageID()) {
                System.out.println("Invalid message ID.");
                continue;
            }

            if (!message.checkRecipientCell()) {
                System.out.println("Invalid recipient number.");
                continue;
            }

            if (!message.validMessage()) {
                System.out.println(
                        "Message must contain 1-250 characters.");
                continue;
            }

            System.out.println("\n1. Send");
            System.out.println("2. Store");
            System.out.println("3. Discard");
            System.out.print("Choose an action: ");

            String action = input.nextLine().trim();

            switch (action) {
                case "1":
                    messageCounter++;
                    sentMessages.add(message);
                    processedMessages++;

                    System.out.println("\nMessage successfully sent.");
                    System.out.println(message.stringSentMessage());
                    break;

                case "2":
                    try {
                        MessageManager.save(message);
                        messageCounter++;
                        processedMessages++;
                        System.out.println("Message successfully stored.");
                    } catch (IOException e) {
                        System.out.println(
                                "Could not store the message: "
                                + e.getMessage());
                    }
                    break;

                case "3":
                    processedMessages++;
                    System.out.println("Message discarded.");
                    break;

                default:
                    System.out.println(
                            "Invalid action. Message was not sent or stored.");
            }
        }

        System.out.println("\nMessages sent this session: "
                + sentMessages.size());
    }

    public static void showSentMessages() {
        if (sentMessages.isEmpty()) {
            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("\n--- SENT MESSAGES ---");

        for (Message message : sentMessages) {
            System.out.println(message.stringSentMessage());
            System.out.println("---------------------");
        }

        System.out.println("Total sent: " + sentMessages.size());
    }
}
