package com.drifting.ui.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ChatFragment.java
<<<<<<< Updated upstream
=======
<<<<<<< HEAD
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.drifting.ui.login.ChatActivity;
import com.example.drifting.ui.login.ViewBottleActivity;
import android.widget.EditText;

=======
>>>>>>> b509b89f2a3932709a2bdc18147a6cbe88c6fb2a
>>>>>>> Stashed changes
import com.example.drifting.ui.login.ExampleAdapter;
import com.example.drifting.ui.login.ExampleItem;
=======
import com.example.drifting.R;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ChatFragment.java
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ChatFragment.java
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
<<<<<<< Updated upstream
=======

import com.example.drifting.ui.login.ForgotPasswordActivity;
import com.example.drifting.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ChatFragment.java

import java.util.ArrayList;
import java.util.HashMap;

<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ChatFragment.java
import backend.util.database.Bottle_back;

import backend.util.database.Chat;
=======
import com.drifting.database.models.Chat;
import com.drifting.database.SetDatabase;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ChatFragment.java

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

<<<<<<< Updated upstream
    private DatabaseReference ContacsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private static  ArrayList<String> name = new ArrayList<>();
    private static  ArrayList<String> time = new ArrayList<>();
    private static  ArrayList<String> Uer_id = new ArrayList<>();
=======
    private DatabaseReference ContacsRef, ChatRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> time = new ArrayList<>();
    private static ArrayList<String> Uer_id = new ArrayList<>();
    private static ArrayList<String> message = new ArrayList<>();
    private static ArrayList<Chat> chat_messages = new ArrayList<>();
>>>>>>> Stashed changes

    Uri url;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static ArrayList<ExampleItem> exampleList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExampleAdapter adapter;
    public static FragmentActivity chatActivity;
    public static Context chatContext;
    public static ArrayList<ImageView> friend_images = new ArrayList<ImageView>();;


    //contact list
    private DatabaseReference ContacsRef, UsersRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    public ChatFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ChatFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChatFragment newInstance(String param1, String param2) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        chatActivity = getActivity();
        chatContext = getContext();
        //setContentView(R.layout.activity_fogot_password);
        //Context context = getApplicationContext();
       // ImageView friendImage = getView().findViewById(R.id.imageView_friend_image);


    }

    private void filter(String text) {
        ArrayList<ExampleItem> filteredList = new ArrayList<>();

        for (ExampleItem item : exampleList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.filterList(filteredList);
    }

    private void createExampleList() {

<<<<<<< Updated upstream
        exampleList = new ArrayList<>();

=======
<<<<<<< HEAD
        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        ContacsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID);
        UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        UsersRef.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                    //String bottleID = this_bottle.getBottleID();
                    String userID = fAuth.getUid();
                    HashMap<String, Boolean> this_history = this_bottle.getPickHistory();

                    //debug: print picked history
                    for (String users : this_history.keySet()) {
                        Log.d("", "picked content:");
                        Log.d("user:", users);
                    }

                    //check if the bottle is viewed
                    if (this_bottle.getIsViewed()) {
                        Log.d("isViewed", "A viewed bottle was returned");
                        continue;
                    }

                    //TODO: comment for test purpose, REUSE for formal product
                    //check if the bottle is from the same user
                    //                                    if(this_bottle.getUserID().equals(userID)){
                    //                                        continue;
                    //                                    }

                    //check if the bottle has been picked up by the same user before
                    if (this_bottle.pickHistory.containsKey(userID)) {
                        Log.d("isPicked", "A bottle picked before was returned");
                        continue;
                    } else {

                        HomeFragment.Bottle bottle_get = new HomeFragment.Bottle(this_bottle, bottleList.size());
                        bottle_get.comment = "filler comment";
                        bottle_get.setVisible();
                        bottleList.add(bottle_get);
                        Log.d(" Bottle content is :", " aaaaaaaaaaaaaa" + bottle_get.message);
                        Log.d(" BottleList size is :", " " + bottleList.size());
                        Log.d(" vector contains ", bottleList.toString());
                        reference.removeEventListener(this);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exampleList = new ArrayList<>();

        // TODO: backend might use a loop to create all the needed chat rooms here (or whatever applies)
        // TODO: backend might want to sort the chat rooms by time.
        // TODO: time: ex. "15:00" for today; otherwise, "Monday" or "11/23" instead
        // TODO: the 3rd parameter is the most recent message sent by the user's friend
        // TODO: and the 4th parameter is the time of the most recent message sent



        exampleList.add(new ExampleItem( R.drawable.avatar, "Amber", "yooooo! Lets go get some boba >.<", "12:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Sam", "How are you? >.<", "13:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Jiaming", "I am fine! Thank you! And you? >.<", "14:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Tao Jin", "Just finished my midterm >.<", "15:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Lucky", "yaaaaaa >.<", "17:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Vickie", "haaaaaa >.<", "18:00"));
        exampleList.add(new ExampleItem( R.drawable.avatar, "Samuel", "laaaaaa >.<", "19:00"));
=======
        exampleList = new ArrayList<>();
<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ChatFragment.java
=======
        SetDatabase db = new SetDatabase();
        db.get_chat_info(name,message,chat_messages,Uer_id, fromhome);
        //ImageView friendImage = getView().findViewById(R.id.imageView_friend_image);
        //R.layout.
        //ArrayList<ImageView> friend_images = new ArrayList<ImageView>();
        //for (int i = 0; i < Uer_id.size(); i++){
        //    db.add_friend_avatar(friendImage,Uer_id.get(i));
        //    friend_images.add(friendImage);
       // }

        //SetDatabase db = new SetDatabase();
        //ImageView friendImage = new ImageView();
        /*for (int i = 0; i < Uer_id.size(); i++){
            db.add_friend_avatar(friendImage,Uer_id.get(i));
            friend_images.add(friendImage);
        }*/
        Log.e("testc", "created");
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ChatFragment.java

>>>>>>> Stashed changes
        mAuth = FirebaseAuth.getInstance();

        currentUserID = mAuth.getCurrentUser().getUid();
        ContacsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID).child("friend_list");
<<<<<<< Updated upstream
       // UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");


        ContacsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

//                    String friendId = snapshot1.getValue().toString();
//                    Log.d("=================", friendId);
//                    int  equal_sign=  friendId.indexOf("=");
//                    String f_id = friendId.substring(1,equal_sign);
//                    Log.d("========", f_id);
                    String f_id = snapshot1.getKey();
                    Log.d("========", f_id);

                    //TODO: get time

                    DatabaseReference friendRef = FirebaseDatabase.getInstance().getReference().child("user").child(f_id);
                    friendRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot_) {
                            name.add(snapshot_.child("user_name").getValue().toString());
                            Uer_id.add(snapshot_.child("user_id").getValue().toString());
                            //Log.d(" ", name[0]);
//                            FirebaseStorage storage = FirebaseStorage.getInstance();
//                            StorageReference storageRef = storage.getReference();
//                            DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
//                            avatarRef = avatarRef.child(friendId);
//                            avatarRef.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    for (DataSnapshot ss : snapshot.getChildren()) {
//                                         url = ss.getValue(Uri.class);
//                                    }
//                                }
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
                            //exampleList.add(new ExampleItem(R.drawable.avatar, name, "Let's chat", "12:00"));

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                   });

                 //exampleList.add(new ExampleItem(R.drawable.avatar, name, "Let's chat", "12:00"));


                }
            }
=======
        // UsersRef = FirebaseDatabase.getInstance().getReference().child("Users");

        //Get chat history from chatRef
        ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");
        ChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    //check all chat history to get the relevant ones
                    //need to have receiver's name, message, and receiver's id
                    //check sender and receiver name
//                    String mmm = snapshot.child("message").getValue().toString();
//                    Log.d("", mmm + "???????");

                    Chat chat = (Chat) snapshot1.getValue(Chat.class);
                    Log.d(">>>>>>>>>>", chat.receiver + "...");
                    Log.d(">>>>>>>>>>", chat.message + "aaa");

                    if (chat.getReceiver().equals(currentUserID) || chat.getSender().equals(currentUserID)) {

                        String sender_id = snapshot1.child("sender").getValue().toString();
                        String receiver_id = snapshot1.child("receiver").getValue().toString();

                        //set the display name always to be others
                        String needed_id;
                        if(receiver_id.equals(currentUserID))  needed_id = sender_id ;
                        else    needed_id = receiver_id;

                        //need to get receiver's name
                        DatabaseReference friendRef = FirebaseDatabase.getInstance().getReference().child("user").child(needed_id);
                        friendRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot_) {
                                String this_name = snapshot_.child("user_name").getValue() != null ?
                                        snapshot_.child("user_name").getValue().toString() : "Default User";

                                //if the conversation exists already
                                if(name.contains(this_name)){
                                    int index = name.indexOf(this_name);
                                    message.set(index, snapshot1.child("message").getValue().toString());
                                    chat_messages.add(chat);
                                }

                                else {
                                    //if not repeated
                                    name.add(this_name);
                                    Uer_id.add(needed_id);
                                    message.add(snapshot1.child("message").getValue().toString());
                                    chat_messages.add(chat);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    } else {
                        continue;
                    }
//                    String f_id = snapshot1.getKey();
//                    Log.d("========", f_id);
//
//                    //TODO: get time
//
//                    DatabaseReference friendRef = FirebaseDatabase.getInstance().getReference().child("user").child(f_id);
//                    friendRef.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot_) {
//                            name.add(snapshot_.child("user_name").getValue().toString());
//                            //Uer_id.add(snapshot_.child("user_id").getValue().toString());
//                            //Log.d(" ", name[0]);
////                            FirebaseStorage storage = FirebaseStorage.getInstance();
////                            StorageReference storageRef = storage.getReference();
////                            DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
////                            avatarRef = avatarRef.child(friendId);
////                            avatarRef.addValueEventListener(new ValueEventListener() {
////                                @Override
////                                public void onDataChange(@NonNull DataSnapshot snapshot) {
////                                    for (DataSnapshot ss : snapshot.getChildren()) {
////                                         url = ss.getValue(Uri.class);
////                                    }
////                                }
////                                @Override
////                                public void onCancelled(@NonNull DatabaseError error) {
////
////                                }
////                            });
//                            //exampleList.add(new ExampleItem(R.drawable.avatar, name, "Let's chat", "12:00"));
//
//                        }
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                   });

                    //exampleList.add(new ExampleItem(R.drawable.avatar, name, "Let's chat", "12:00"));
                }
            }

>>>>>>> Stashed changes
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//        DatabaseReference avatarRef = FirebaseDatabase.getInstance().getReference("avatars/");
//        String user_id = mAuth.getUid();
//        avatarRef = avatarRef.child(user_id);
//        avatarRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot ss : snapshot.getChildren()) {
//                    String url = ss.getValue(String.class);
//                    Picasso.get().load(url).into(profileImage);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
<<<<<<< Updated upstream
 //       exampleList = new ArrayList<>();
=======
        //       exampleList = new ArrayList<>();
>>>>>>> Stashed changes

//        // TODO: backend might use a loop to create all the needed chat rooms here (or whatever applies)
//        // TODO: backend might want to sort the chat rooms by time.
//        // TODO: time: ex. "15:00" for today; otherwise, "Monday" or "11/23" instead
//        // TODO: the 3rd parameter is the most recent message sent by the user's friend
//        // TODO: and the 4th parameter is the time of the most recent message sent

<<<<<<< Updated upstream
            Log.d("size", Integer.toString(name.size()));
          for(int i = 0; i < name.size(); i++) {
              exampleList.add(new ExampleItem(R.drawable.avatar, name.get(i), "You are friends now! Let's start chatting.", "12:00", Uer_id.get(i)));
          }

          name.clear();
=======
        Log.d("size", Integer.toString(name.size()));
        for (int i = 0; i < name.size(); i++) {
            exampleList.add(new ExampleItem(R.drawable.avatar, name.get(i), message.get(i), "12:00", Uer_id.get(i), chat_messages));
        }

        name.clear();
        Uer_id.clear();
        message.clear();
        chat_messages.clear();
        //friend_images.clear();

>>>>>>> b509b89f2a3932709a2bdc18147a6cbe88c6fb2a
>>>>>>> Stashed changes
    }

//    private void filter(String text) {
//        ArrayList<ExampleItem> filteredList = new ArrayList<>();
//
//        for (ExampleItem item : exampleList) {
//            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//
//        adapter.filterList(filteredList);
//    }

//    private void createExampleList() {
//        exampleList = new ArrayList<>();
//
//        // TODO: backend might use a loop to create all the needed chat rooms here (or whatever applies)
//        // TODO: backend might want to sort the chat rooms by time.
//        // TODO: time: ex. "15:00" for today; otherwise, "Monday" or "11/23" instead
//        // TODO: the 3rd parameter is the most recent message sent by the user's friend
//        // TODO: and the 4th parameter is the time of the most recent message sent
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Amber", "yooooo! Lets go get some boba >.<", "12:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Sam", "How are you? >.<", "13:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Jiaming", "I am fine! Thank you! And you? >.<", "14:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Tao Jin", "Just finished my midterm >.<", "15:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Lucky", "yaaaaaa >.<", "17:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Vickie", "haaaaaa >.<", "18:00"));
//        exampleList.add(new ExampleItem( R.drawable.avatar, "Samuel", "laaaaaa >.<", "19:00"));
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< Updated upstream:app/src/main/java/com/example/drifting/ChatFragment.java

//        final Button searchButton = getView().findViewById(R.id.search_button);
//        final EditText input_friend_to_search = getView().findViewById(R.id.search_field);
=======
//        this.inflater = inflater;
//        this.container = container;
>>>>>>> Stashed changes:app/src/main/java/com/drifting/ui/chat/ChatFragment.java

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_View);
        // create all the chat rooms (like all the friends the user had added before)
        createExampleList();

        adapter = new ExampleAdapter(exampleList);

        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // the input of the search field
        EditText name_to_search = getView().findViewById(R.id.search_field);
        /*ImageView friend_image = getView().findViewById(R.id.imageView_friend_image);

        SetDatabase db = new SetDatabase();
        for (int i = 0; i < Uer_id.size(); i++){
            db.add_friend_avatar(friend_image,Uer_id.get(i));
            friend_images.add(friend_image);
        }*/

        // as anything typed in the search field, it will search for that input
        name_to_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

}