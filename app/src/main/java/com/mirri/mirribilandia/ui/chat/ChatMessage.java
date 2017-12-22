package com.mirri.mirribilandia.ui.chat;

import android.util.Log;

public class ChatMessage {
    boolean left;
    String message;
    String id;
    String orario;

    public ChatMessage(boolean left, String message, String id, String orario) {
        super();
        this.left = left;
        this.message = message;
        this.id = id;
        this.orario = convertDate(orario);
    }

    private String convertDate(String date) {
        String[] separated = date.split(" ");
        String hour = separated[1].substring(0,5);
        return hour;
    }
}
