package com.mycompany.chatapppart2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    @Test
    void testMessageIdIsValid() {
        Message message = new Message(
                1, "+27812345678", "Hello there");

        assertTrue(message.checkMessageID());
        assertTrue(message.getMessageId().length() <= 10);
    }

    @Test
    void testValidMessageLength() {
        Message message = new Message(
                1, "+27812345678", "Hi Mike dinner tonight");

        assertTrue(message.validMessage());
    }

    @Test
    void testMessageTooLong() {
        String longMessage = "x".repeat(251);

        Message message = new Message(
                1, "+27812345678", longMessage);

        assertFalse(message.validMessage());
    }

    @Test
    void testEmptyMessageIsInvalid() {
        Message message = new Message(
                1, "+27812345678", "   ");

        assertFalse(message.validMessage());
    }

    @Test
    void testValidRecipient() {
        Message message = new Message(
                1, "+27812345678", "Hello");

        assertTrue(message.checkRecipientCell());
    }

    @Test
    void testInvalidRecipientWithoutCountryCode() {
        Message message = new Message(
                1, "0812345678", "Hello");

        assertFalse(message.checkRecipientCell());
    }

    @Test
    void testInvalidRecipientWithLetters() {
        Message message = new Message(
                1, "+27ABCDEFGH", "Hello");

        assertFalse(message.checkRecipientCell());
    }

    @Test
    void testMessageHashContainsRequiredParts() {
        Message message = new Message(
                3, "+27812345678", "hello world");

        String hash = message.createMessageHash();

        assertTrue(hash.startsWith(
                message.getMessageId().substring(0, 2) + ":3:"));

        assertTrue(hash.endsWith("HELLOWORLD"));
    }

    @Test
    void testSentMessageDetails() {
        Message message = new Message(
                1, "+27812345678", "Hello there");

        String details = message.stringSentMessage();

        assertTrue(details.contains("Message ID:"));
        assertTrue(details.contains("Message Hash:"));
        assertTrue(details.contains("Recipient: +27812345678"));
        assertTrue(details.contains("Message: Hello there"));
    }
}
