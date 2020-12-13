package com.drifting.ui.chat;

import java.util.ArrayList;

import com.drifting.database.models.Chat;

public class ExampleItem {

    //final private int friend_image;
    final private int friend_image;
    final private String friend_name;
    final private String recent_text;
    final private String recent_text_sent_time; // TODO: backend might need to check
    final private String friend_id;
    public ArrayList<Chat> chat_messages;

    public ExampleItem(int friend_image, String name, String text, String time, String id, ArrayList<Chat> chat_messages) {
        this.friend_image = friend_image;
        friend_name = name;
        recent_text = text;
        recent_text_sent_time = time;
        friend_id = id;
        this.chat_messages = chat_messages;
    }

    public int getFriendImage() {
        return friend_image;
    }

    public String getName() {
        return friend_name;
    }

    public String getText() {
        return recent_text;
    }

    public String getTime() {
        return recent_text_sent_time;
    }

    public String getID() {
        return friend_id;
    }

    public ArrayList<Chat> getChat_messages() {
        return chat_messages;
    }

    public void setChat_messages(ArrayList<Chat> chat_messages) {
        this.chat_messages = chat_messages;
    }
}
