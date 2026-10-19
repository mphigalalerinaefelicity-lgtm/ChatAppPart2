package com.mycompany.chatapppart2;

import org.junit.Test;
import static org.junit.Assert.*;

public class MessageTest {

    @Test
    public void testMessageValidLength() {
        Message m = new Message(1, "+27718693002", "Hi Mike dinner tonight");
        assertTrue(m.validMessage());
    }

    @Test
    public void testMessageTooLong() {
        String longMsg = "x".repeat(260);
        Message m = new Message(1, "+27718693002", longMsg);
        assertFalse(m.validMessage());
    }

    @Test
    public void testRecipientValid() {
        Message m = new Message(1, "+27718693002", "Hello");
        assertTrue(m.checkRecipient());
    }

    @Test
    public void testRecipientInvalid() {
        Message m = new Message(1, "08575975889", "Hello");
        assertFalse(m.checkRecipient());
    }
}