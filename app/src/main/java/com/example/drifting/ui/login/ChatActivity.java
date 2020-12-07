package com.example.drifting.ui.login;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import backend.util.database.Chat;
import backend.util.database.SetDatabase;

public class ChatActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    EditText tfield;
    Button sendMessage;
    ScrollView scroll;
    Button return_button;
    TextView userName_textview;
    String friend_id;
    ArrayList<Chat> chat_messages;
    ArrayList<Chat> mychat = new ArrayList<>();

    // private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference UserRef;
    FirebaseUser firebaseUser;

    //message type int
    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //set page layout
        linearLayout = (LinearLayout) findViewById(R.id.chat_linear_layout);
        sendMessage = findViewById(R.id.send_chat_button);
        scroll = findViewById(R.id.content_scrollview);
        return_button = findViewById(R.id.return_button);
        userName_textview = findViewById(R.id.textView2);

        //get needed info
        userName_textview.setText(ExampleAdapter.userName);
        friend_id = ExampleAdapter.friend_id;
        chat_messages = ExampleAdapter.chat_messages;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        //filter out the specific chats using chat_messages
        for (int i = 0; i < chat_messages.size(); i++) {
            Chat chat = chat_messages.get(i);
            String this_sender = chat.getSender();
            String this_receiver = chat.getReceiver();
            //get the specific info!
            if (this_receiver.equals(firebaseUser.getUid()) && this_sender.equals(friend_id)
                    || this_receiver.equals(friend_id) && this_sender.equals(firebaseUser.getUid())) {
                mychat.add(chat);
            }
        }

        //loop to display all messages
        for (int i = 0; i < mychat.size(); i++) {
            Chat chat = mychat.get(i);
            String this_sender = chat.getSender();
            String this_receiver = chat.getReceiver();
            String message = chat.getMessage();

            // I send the message
            if (this_sender.equals(firebaseUser.getUid())) {
                TextView textView2 = new TextView(ChatActivity.this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(50, 20, 50, 20);
                textView2.setLayoutParams(layoutParams);
                textView2.setText(message);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView2.setTextColor(0xFF000000);
                textView2.setPadding(35, 20, 35, 20);
                textView2.setBackgroundResource(R.drawable.border8);
                linearLayout.addView(textView2);
                scroll.scrollTo(0, scroll.getBottom());
            }

            //I got the message
            else {
                TextView textView1 = new TextView(this);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                layoutParams1.setMargins(50, 20, 50, 20);
                textView1.setLayoutParams(layoutParams1);
                textView1.setText(message);
                textView1.setTextColor(0xFF000000);
                textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
                textView1.setBackgroundResource(R.drawable.border9);
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView1.setPadding(35, 20, 35, 20);
                linearLayout.addView(textView1);
            }

        }


//        DatabaseReference chat_ref = FirebaseDatabase.getInstance().getReference("Chats");
//        chat_ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mychat.clear();
//                for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                    Chat chat =
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })

//
//        TextView textView1 = new TextView(this);
//        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
//                LayoutParams.WRAP_CONTENT);
//        layoutParams1.gravity = Gravity.LEFT;
//        layoutParams1.setMargins(50, 20, 50, 20);
//        textView1.setLayoutParams(layoutParams1);
//        textView1.setText("TextView1");
//        textView1.setTextColor(0xFF000000);
//        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
//        textView1.setBackgroundResource(R.drawable.border9);
//        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        textView1.setPadding(35, 20, 35, 20);
//        linearLayout.addView(textView1);

        //send button
        sendMessage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView2 = new TextView(ChatActivity.this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(50, 20, 50, 20);
                textView2.setLayoutParams(layoutParams);
                tfield = (EditText) findViewById(R.id.message_area);
                String content = tfield.getText().toString();
                tfield.setText("");
                textView2.setText(content);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView2.setTextColor(0xFF000000);
                textView2.setPadding(35, 20, 35, 20);
                textView2.setBackgroundResource(R.drawable.border8);
                linearLayout.addView(textView2);
                scroll.scrollTo(0, scroll.getBottom());
                sendMessage(firebaseUser.getUid(), friend_id, content);
            }
        });

        return_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void sendMessage(String sender, String receiver, String message) {

//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
//
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("sender", sender);
//        hashMap.put("receiver", receiver);
//        hashMap.put("message", message);
//
//        reference.child("Chats").push().setValue(hashMap);

        Chat new_chat = new Chat(sender, receiver, message);
        SetDatabase db = new SetDatabase();
        db.addNewChat(new_chat);

    }


}