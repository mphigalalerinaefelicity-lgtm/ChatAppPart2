package com.mycompany.chatapppart2;

import java.util.UUID;

public class Message {

    private final String messageId;
    private final int messageNumber;
    private final String recipient;
    private final String message;

    public Message(int messageNumber, String recipient, String message) {
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageId = generateId();
    }

    public String generateId() {
        // Generate a unique ID that is no longer than 10 characters.
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageId != null
                && !messageId.isEmpty()
                && messageId.length() <= 10;
    }

    public boolean checkRecipientCell() {
        // South African international format: +27 followed by 9 digits.
        return recipient != null
                && recipient.matches("\\+27\\d{9}");
    }

    public boolean validMessage() {
        return message != null
                && !message.trim().isEmpty()
                && message.length() <= 250;
    }

    public String createMessageHash() {
        if (message == null || message.trim().isEmpty()) {
            return messageId.substring(0, 2)
                    + ":" + messageNumber + ":";
        }

        String[] words = message.trim().split("\\s+");
        String firstWord = words[0].toUpperCase();
        String lastWord = words[words.length - 1].toUpperCase();

        return messageId.substring(0, 2)
                + ":" + messageNumber
                + ":" + firstWord + lastWord;
    }

    public String stringSentMessage() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + message;
    }

    public String getMessageId() {
        return messageId;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessage() {
        return message;
    }
}
