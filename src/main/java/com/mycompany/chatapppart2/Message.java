package com.mycompany.chatapppart2;

import java.util.Random;
mnm
public class Message {

    private String messageId;
    private int messageNumber;
    private String recipient;
    private String message;

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageId = generateId();
    }

    // FIXED: safe 10-digit ID
    public String generateId() {
        Random r = new Random();
        int id = 100000000 + r.nextInt(900000000);
        return String.valueOf(id);
    }

    public boolean checkMessageID() {
        return messageId.length() == 9; // safer check
    }

    public boolean checkRecipient() {
        return recipient.startsWith("+") && recipient.length() <= 13;
    }

    public boolean validMessage() {
        return message != null && message.length() <= 250;
    }

    // FIXED HASH (SAFE + CLEAN)
    public String createHash() {

        if (message == null || message.trim().isEmpty()) {
            return "00:0:EMPTY";
        }

        String[] words = message.trim().split(" ");

        String first = words[0];
        String last = words[words.length - 1];

        return (messageId.substring(0, 2)
                + ":" + messageNumber
                + ":" + first.toUpperCase()
                + last.toUpperCase());
    }

    public String getDetails() {
        return "Message ID: " + messageId +
                "\nMessage Hash: " + createHash() +
                "\nRecipient: " + recipient +
                "\nMessage: " + message;
    }
}
