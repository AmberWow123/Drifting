package com.example.drifting.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.drifting.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class ChatActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    EditText tfield;
    Button sendMessage;
    ScrollView scroll;
    Button return_button;
    TextView userName_textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        linearLayout = (LinearLayout) findViewById(R.id.chat_linear_layout);
        sendMessage = findViewById(R.id.send_chat_button);
        scroll = findViewById(R.id.content_scrollview);
        return_button = findViewById(R.id.return_button);
        userName_textview = findViewById(R.id.textView2);

        userName_textview.setText(ExampleAdapter.userName);
<<<<<<< Updated upstream
        TextView textView1 = new TextView(this);
        LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams1.gravity = Gravity.LEFT;
        layoutParams1.setMargins(50, 20, 50, 20);
        textView1.setLayoutParams(layoutParams1);
        textView1.setText("TextView1");
        textView1.setTextColor(0xFF000000);
        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
        textView1.setBackgroundResource(R.drawable.border9);
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        textView1.setPadding(35, 20, 35, 20);
        linearLayout.addView(textView1);
=======
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

>>>>>>> Stashed changes

        sendMessage.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView2 = new TextView(ChatActivity.this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.RIGHT;
                layoutParams.setMargins(50, 20, 50, 20);
                textView2.setLayoutParams(layoutParams);
                tfield = (EditText)findViewById(R.id.message_area);
                String content = tfield.getText().toString();
                tfield.setText("");
                textView2.setText(content);
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView2.setTextColor(0xFF000000);
                textView2.setPadding(35,20,35,20);
                textView2.setBackgroundResource(R.drawable.border8);
                linearLayout.addView(textView2);
                scroll.scrollTo(0, scroll.getBottom());
            }
        });

        return_button.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
}