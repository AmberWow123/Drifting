package com.drifting.ui.chat;

<<<<<<< Updated upstream
import android.net.Uri;
import android.widget.ImageView;
=======
import java.util.ArrayList;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ExampleItem.java
import backend.util.database.Chat;
>>>>>>> Stashed changes
=======
import com.drifting.database.models.Chat;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ExampleItem.java

public class ExampleItem {

    //final private int friend_image;
    final private int friend_image;
    final private String friend_name;
    final private String recent_text;
    final private String recent_text_sent_time; // TODO: backend might need to check
    final private String friend_id;
<<<<<<< Updated upstream

    public ExampleItem(int image_src, String name, String text, String time, String id) {
=======
    public ArrayList<Chat> chat_messages;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ui/login/ExampleItem.java
    public ExampleItem(int image_src, String name, String text, String time, String id, ArrayList<Chat> chat_messages) {
>>>>>>> Stashed changes
        friend_image = image_src;
=======
    public ExampleItem(int friend_image, String name, String text, String time, String id, ArrayList<Chat> chat_messages) {
        this.friend_image = friend_image;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ExampleItem.java
        friend_name = name;
        recent_text = text;
        recent_text_sent_time = time;
        friend_id = id;
<<<<<<< Updated upstream
=======
        this.chat_messages = chat_messages;
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======

    public ArrayList<Chat> getChat_messages() {
        return chat_messages;
    }

    public void setChat_messages(ArrayList<Chat> chat_messages) {
        this.chat_messages = chat_messages;
    }
>>>>>>> Stashed changes
}
