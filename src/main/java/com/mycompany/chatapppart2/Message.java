
package com.mycompany.chatapppart2;

import java.util.UUID;

public class Message {

    private String messageId;
    private int messageNumber;
    private String recipient;
    private String message;
    private String messageHash;
    private String status;

    // Required for Gson when loading stored messages
    public Message() {
    }

    public Message(int messageNumber, String recipient, String message) {
        this(UUID.randomUUID().toString().replace("-", "").substring(0, 10),
                messageNumber, recipient, message);
    }

    // Constructor used when restoring a saved message
    public Message(String messageId, int messageNumber,
            String recipient, String message) {
        this.messageId = messageId;
        this.messageNumber = messageNumber;
        this.recipient = recipient;
        this.message = message;
        this.messageHash = createMessageHash();
        this.status = "Stored";
    }

    public String generateId() {
        return UUID.randomUUID().toString()
                .replace("-", "").substring(0, 10);
    }

    public boolean checkMessageID() {
        return messageId != null
                && !messageId.isEmpty()
                && messageId.length() <= 10;
    }

    public boolean checkRecipientCell() {
        return recipient != null
                && (recipient.matches("\\+27[678]\\d{8}")
                || recipient.matches("0[678]\\d{8}"));
    }

    public boolean validMessage() {
        return message != null
                && !message.trim().isEmpty()
                && message.length() <= 250;
    }

    public String createMessageHash() {
        if (messageId == null || messageId.length() < 2) {
            return "";
        }

        if (message == null || message.trim().isEmpty()) {
            return messageId.substring(0, 2)
                    + ":" + messageNumber + ":";
        }

        String[] words = message.trim().split("\\s+");

        return messageId.substring(0, 2)
                + ":" + messageNumber
                + ":" + words[0].toUpperCase()
                + words[words.length - 1].toUpperCase();
    }

    public String stringSentMessage() {
        return "Message ID: " + messageId
                + "\nMessage Hash: " + createMessageHash()
                + "\nRecipient: " + recipient
                + "\nMessage: " + message
                + "\nStatus: " + status;
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

    public String getMessageHash() {
        return messageHash == null ? createMessageHash() : messageHash;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessageHash(String messageHash) {
        this.messageHash = messageHash;
    }
}
