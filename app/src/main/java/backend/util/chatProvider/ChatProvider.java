package backend.util.chatProvider;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatProvider {
    private final DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Chats");
    private volatile String latestMessage = "";
    private final String currentUserID;
    private final String friendID;
    private int isThereNewMessage = 0;

    public ChatProvider(String currentUserID, String friendID){
        this.currentUserID = currentUserID;
        this.friendID = friendID;
    }

}
