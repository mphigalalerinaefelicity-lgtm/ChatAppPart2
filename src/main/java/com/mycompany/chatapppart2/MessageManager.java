
package com.mycompany.chatapppart2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
 
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class MessageManager {

    private static final Path FILE_PATH = Path.of("messages.json");
    private static final Path TEMP_FILE = Path.of("messages-temp.json");

    private static final Gson GSON =
            new GsonBuilder().setPrettyPrinting().create();

    private static final Type MESSAGE_LIST_TYPE =
            new TypeToken<ArrayList<Message>>() {}.getType();

    // Save a new message to the JSON file.
    public static void save(Message message) throws IOException {

        List<Message> messages = load();

        message.setStatus("Stored");
        messages.add(message);

        overwrite(messages);
    }

    // Load both the old format and the new JSON array format.
    public static List<Message> load() throws IOException {

        if (!Files.exists(FILE_PATH)) {
            return new ArrayList<>();
        }

        String json = Files.readString(FILE_PATH);

        if (json.trim().isEmpty()) {
            return new ArrayList<>();
        }

        try {

            // New format: JSON array.
            if (json.trim().startsWith("[")) {

                List<Message> messages =
                        GSON.fromJson(json, MESSAGE_LIST_TYPE);

                return messages == null
                        ? new ArrayList<>()
                        : messages;
            }

            // Old format: one JSON object per line.
            List<Message> messages = new ArrayList<>();

            for (String line : json.split("\\R")) {

                if (!line.trim().isEmpty()) {

                    Message message =
                            GSON.fromJson(line, Message.class);

                    if (message != null) {
                        message.setStatus("Stored");
                        messages.add(message);
                    }
                }
            }

            return messages;

        } catch (RuntimeException exception) {

            throw new IOException(
                    "Could not read messages.json. "
                    + "The existing file has been preserved.",
                    exception);
        }
    }

    // Write messages to the JSON file.
    public static void overwrite(List<Message> messages)
            throws IOException {

        String json = GSON.toJson(messages, MESSAGE_LIST_TYPE);

        Files.writeString(TEMP_FILE, json);

        try {

            Files.move(
                    TEMP_FILE,
                    FILE_PATH,
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.ATOMIC_MOVE
            );

        } catch (IOException exception) {

            Files.move(
                    TEMP_FILE,
                    FILE_PATH,
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
    }

    // Delete a stored message using its hash.
    public static boolean deleteByHash(String hash)
            throws IOException {

        List<Message> messages = load();

        boolean removed = messages.removeIf(message ->
                message.getMessageHash() != null
                && message.getMessageHash().equals(hash)
        );

        if (removed) {
            overwrite(messages);
        }

        return removed;
    }

    // Display all stored messages.
    public static void displayStoredMessages()
            throws IOException {

        List<Message> messages = load();

        if (messages.isEmpty()) {
            System.out.println("No stored messages found.");
            return;
        }

        for (Message message : messages) {

            System.out.println("-------------------------");
            System.out.println(message.stringSentMessage());
        }
    }
}
