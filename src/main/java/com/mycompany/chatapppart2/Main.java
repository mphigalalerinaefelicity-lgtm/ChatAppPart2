
package com.mycompany.chatapppart2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner input = new Scanner(System.in);

    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();

    private static final Path HISTORY_FILE = Path.of("message-history.json");

    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().create();

    private static final Type MESSAGE_LIST_TYPE =
            new TypeToken<ArrayList<Message>>() {}.getType();

    private static int messageCounter = 0;
    private static String senderName = "QuickChat User";

    public static void main(String[] args) {

        System.out.println("Welcome to QuickChat.");

        RegistrationClassOne registration = new RegistrationClassOne();

        System.out.println("\n--- USER REGISTRATION ---");

        System.out.print("Enter first name: ");
        String firstName = input.nextLine();

        System.out.print("Enter surname: ");
        String lastName = input.nextLine();

        senderName = firstName + " " + lastName;

        System.out.print("Enter username: ");
        String username = input.nextLine();

        System.out.print("Enter password: ");
        String password = input.nextLine();

        System.out.print("Enter cellphone number (+27...): ");
        String cellphone = input.nextLine();

        String result = registration.registerUser(
                firstName, lastName, username, password, cellphone);

        System.out.println(result);

        if (!result.equals("User registered successfully.")) {
            return;
        }

        LoginUserOne login = new LoginUserOne(registration);

        System.out.println("\n--- LOGIN ---");

        System.out.print("Username: ");
        String loginUsername = input.nextLine();

        System.out.print("Password: ");
        String loginPassword = input.nextLine();

        if (!login.loginUser(loginUsername, loginPassword)) {
            System.out.println(login.returnLoginStatus());
            return;
        }

        System.out.println(login.returnLoginStatus());

        loadMessageHistory();

        int messageLimit = readPositiveInteger(
                "How many messages would you like to process? ");

        boolean running = true;

        while (running) {

            System.out.println("\n--- QUICKCHAT MENU ---");
            System.out.println("1. Send Messages");
            System.out.println("2. Show Sent Messages");
            System.out.println("3. Quit");
            System.out.println("4. Stored Messages");
            System.out.print("Choose an option: ");

            switch (input.nextLine().trim()) {

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

                case "4":
                    storedMessagesMenu();
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static int readPositiveInteger(String prompt) {

        while (true) {

            System.out.print(prompt);

            try {
                int number = Integer.parseInt(input.nextLine().trim());

                if (number > 0) {
                    return number;
                }

                System.out.println("Enter a number greater than zero.");

            } catch (NumberFormatException e) {
                System.out.println("Enter a valid whole number.");
            }
        }
    }

    // Load sent and disregarded messages from their separate history file.
    private static void loadMessageHistory() {

        if (!Files.exists(HISTORY_FILE)) {
            return;
        }

        try {

            String json = Files.readString(HISTORY_FILE);

            if (json.trim().isEmpty()) {
                return;
            }

            List<Message> history =
                    GSON.fromJson(json, MESSAGE_LIST_TYPE);

            if (history != null) {

                for (Message message : history) {

                    if ("Sent".equalsIgnoreCase(message.getStatus())) {
                        sentMessages.add(message);

                    } else if ("Disregarded".equalsIgnoreCase(
                            message.getStatus())) {
                        disregardedMessages.add(message);
                    }

                    messageCounter = Math.max(
                            messageCounter,
                            message.getMessageNumber());
                }
            }

            System.out.println("Previous message history loaded.");

        } catch (IOException | RuntimeException e) {
            System.out.println(
                    "Could not load message history: " + e.getMessage());
        }
    }

    // Save sent and disregarded messages without changing messages.json.
    private static void saveMessageHistory() {

        List<Message> history = new ArrayList<>();

        history.addAll(sentMessages);
        history.addAll(disregardedMessages);

        try {
            Files.writeString(
                    HISTORY_FILE,
                    GSON.toJson(history, MESSAGE_LIST_TYPE));

        } catch (IOException e) {
            System.out.println(
                    "Could not save message history: " + e.getMessage());
        }
    }

    public static void sendMessages(int messageLimit) {

        int processed = 0;

        while (processed < messageLimit) {

            System.out.println("\n--- NEW MESSAGE ---");

            System.out.print("Enter recipient: ");
            String recipient = input.nextLine().trim();

            if (recipient.equals("0")) {
                System.out.println("Returning to main menu.");
                break;
            }

            System.out.print("Enter message (1-250 characters): ");
            String text = input.nextLine();

            Message message = new Message(
                    messageCounter + 1, recipient, text);

            if (!message.checkMessageID()
                    || !message.checkRecipientCell()
                    || !message.validMessage()) {

                System.out.println("Invalid message details. Try again.");
                continue;
            }

            System.out.println("\n1. Send");
            System.out.println("2. Disregard");
            System.out.println("3. Store");
            System.out.print("Choose an action: ");

            String action = input.nextLine().trim();

            switch (action) {

                case "1":

                    message.setStatus("Sent");
                    sentMessages.add(message);

                    messageCounter++;
                    processed++;

                    saveMessageHistory();

                    System.out.println("Message successfully sent.");
                    System.out.println(message.stringSentMessage());
                    break;

                case "2":

                    message.setStatus("Disregarded");
                    disregardedMessages.add(message);

                    messageCounter++;
                    processed++;

                    saveMessageHistory();

                    System.out.println("Message disregarded.");
                    break;

                case "3":

                    try {

                        MessageManager.save(message);

                        messageCounter++;
                        processed++;

                        System.out.println("Message successfully stored.");
                        System.out.println(message.stringSentMessage());

                    } catch (IOException e) {
                        System.out.println(
                                "Storage error: " + e.getMessage());
                    }

                    break;

                default:
                    System.out.println(
                            "Invalid action. Choose 1, 2 or 3.");
            }
        }

        System.out.println("\nMessage processing completed.");
    }

    public static void showSentMessages() {

        if (sentMessages.isEmpty()) {
            System.out.println("No messages have been sent yet.");
            return;
        }

        System.out.println("\n--- SENT MESSAGES REPORT ---");

        for (Message message : sentMessages) {

            System.out.println(message.stringSentMessage());
            System.out.println("---------------------------");
        }

        System.out.println("Total sent: " + sentMessages.size());
    }

    // PART 3: STORED MESSAGES MENU

    public static void storedMessagesMenu() {

        boolean inMenu = true;

        while (inMenu) {

            System.out.println("\n--- STORED MESSAGES MENU ---");
            System.out.println("1. Display sender and recipient");
            System.out.println("2. Display longest stored message");
            System.out.println("3. Search message by ID");
            System.out.println("4. Search stored messages by recipient");
            System.out.println("5. Delete stored message by hash");
            System.out.println("6. Display full stored messages report");
            System.out.println("7. Search sent and stored messages by recipient");
            System.out.println("8. Return to main menu");
            System.out.print("Choose an option: ");

            String choice = input.nextLine().trim();

            try {

                switch (choice) {

                    case "1":
                        displaySenderAndRecipient();
                        break;

                    case "2":
                        displayLongestMessage();
                        break;

                    case "3":
                        searchMessageById();
                        break;

                    case "4":
                        searchStoredByRecipient();
                        break;

                    case "5":
                        deleteStoredByHash();
                        break;

                    case "6":
                        MessageManager.displayStoredMessages();
                        break;

                    case "7":
                        searchSentAndStoredByRecipient();
                        break;

                    case "8":
                        inMenu = false;
                        break;

                    default:
                        System.out.println("Invalid option.");
                }

            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());
            }
        }
    }

    private static void displaySenderAndRecipient()
            throws IOException {

        List<Message> messages = MessageManager.load();

        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        for (Message message : messages) {

            System.out.println("Sender: " + senderName);
            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("-------------------------");
        }
    }

    private static void displayLongestMessage()
            throws IOException {

        List<Message> messages = MessageManager.load();

        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        Message longest = messages.get(0);

        for (Message message : messages) {

            if (message.getMessage().length()
                    > longest.getMessage().length()) {
                longest = message;
            }
        }

        System.out.println("\n--- LONGEST STORED MESSAGE ---");
        System.out.println(longest.stringSentMessage());
        System.out.println("Length: " + longest.getMessage().length());
    }

    private static void searchMessageById()
            throws IOException {

        System.out.print("Enter message ID: ");
        String id = input.nextLine().trim();

        for (Message message : sentMessages) {

            if (message.getMessageId().equalsIgnoreCase(id)) {
                System.out.println(message.stringSentMessage());
                return;
            }
        }

        for (Message message : disregardedMessages) {

            if (message.getMessageId().equalsIgnoreCase(id)) {
                System.out.println(message.stringSentMessage());
                return;
            }
        }

        for (Message message : MessageManager.load()) {

            if (message.getMessageId().equalsIgnoreCase(id)) {
                System.out.println(message.stringSentMessage());
                return;
            }
        }

        System.out.println("Message ID not found.");
    }

    private static void searchStoredByRecipient()
            throws IOException {

        System.out.print("Enter recipient number: ");
        String recipient = input.nextLine().trim();

        boolean found = false;

        for (Message message : MessageManager.load()) {

            if (message.getRecipient().equals(recipient)) {
                System.out.println(message.stringSentMessage());
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No stored messages found for this recipient.");
        }
    }

    private static void deleteStoredByHash()
            throws IOException {

        System.out.print("Enter message hash: ");
        String hash = input.nextLine().trim();

        if (MessageManager.deleteByHash(hash)) {
            System.out.println("Message successfully deleted.");

        } else {
            System.out.println("Message hash not found.");
        }
    }

    private static void searchSentAndStoredByRecipient()
            throws IOException {

        System.out.print("Enter recipient number: ");
        String recipient = input.nextLine().trim();

        boolean found = false;

        for (Message message : sentMessages) {

            if (message.getRecipient().equals(recipient)) {
                System.out.println(message.stringSentMessage());
                found = true;
            }
        }

        for (Message message : MessageManager.load()) {

            if (message.getRecipient().equals(recipient)) {
                System.out.println(message.stringSentMessage());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No sent or stored messages found.");
        }
    }
}
