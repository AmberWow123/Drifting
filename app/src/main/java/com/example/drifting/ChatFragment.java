package com.example.drifting;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drifting.ui.login.ExampleAdapter;
import com.example.drifting.ui.login.ExampleItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import backend.util.database.Chat;
import backend.util.database.SetDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    private DatabaseReference ContacsRef, ChatRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    private static ArrayList<String> name = new ArrayList<>();
    private static ArrayList<String> time = new ArrayList<>();
    private static ArrayList<String> Uer_id = new ArrayList<>();
    private static ArrayList<String> message = new ArrayList<>();
    private static ArrayList<Chat> chat_messages = new ArrayList<>();

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

        exampleList = new ArrayList<>();
        SetDatabase db = new SetDatabase();
        db.get_chat_info(name,message,chat_messages,Uer_id);
       /* mAuth = FirebaseAuth.getInstance();

        currentUserID = mAuth.getCurrentUser().getUid();
        ContacsRef = FirebaseDatabase.getInstance().getReference().child("Contacts").child(currentUserID).child("friend_list");

        //Get chat history from chatRef
        ChatRef = FirebaseDatabase.getInstance().getReference().child("Chats");
        ChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    Chat chat = (Chat) snapshot1.getValue(Chat.class);
                    //Log.d(">>>>>>>>>>", chat.receiver + "...");
                    //Log.d(">>>>>>>>>>", chat.message + "aaa");

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

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d("size", Integer.toString(name.size()));

        */
        for (int i = 0; i < name.size(); i++) {
            exampleList.add(new ExampleItem(R.drawable.avatar, name.get(i), message.get(i), "12:00", Uer_id.get(i), chat_messages));
        }

        name.clear();
        Uer_id.clear();
        message.clear();
        chat_messages.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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