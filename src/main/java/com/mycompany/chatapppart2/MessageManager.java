package com.mycompany.chatapppart2;

import java.io.FileWriter;
import java.io.IOException;

public class MessageManager {

    public static void save(Message message) throws IOException {
        try (FileWriter file = new FileWriter("messages.json", true)) {
            String json = "{"
                    + "\"messageId\":\"" + escapeJson(message.getMessageId()) + "\","
                    + "\"messageNumber\":" + message.getMessageNumber() + ","
                    + "\"recipient\":\"" + escapeJson(message.getRecipient()) + "\","
                    + "\"message\":\"" + escapeJson(message.getMessage()) + "\","
                    + "\"messageHash\":\"" + escapeJson(message.createMessageHash()) + "\""
                    + "}";

            file.write(json);
            file.write(System.lineSeparator());
        }
    }

    private static String escapeJson(String value) {
        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
