package com.mycompany.chatapppart2;

import java.io.FileWriter;

public class MessageManager {

    public static void save(String data) {
        try {
            FileWriter file = new FileWriter("messages.json", true);
            file.write(data + "\n");
            file.close();
        } catch (Exception e) {
            System.out.println("Error saving file");
        }
    }
}