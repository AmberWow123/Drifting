package com.drifting.ui.chat;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import com.drifting.database.models.Chat;
import com.drifting.database.SetDatabase;

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
//    private LayoutInflater inflater;
//    private ViewGroup container;

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


    public void createExampleList(boolean fromhome) {

        exampleList = new ArrayList<>();
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

        for (int i = 0; i < name.size(); i++) {
            exampleList.add(new ExampleItem(R.drawable.avatar, name.get(i), message.get(i), "12:00", Uer_id.get(i), chat_messages));
        }


        name.clear();
        Uer_id.clear();
        message.clear();
        chat_messages.clear();
        //friend_images.clear();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        this.inflater = inflater;
//        this.container = container;

        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_View);
        // create all the chat rooms (like all the friends the user had added before)
        adapter = new ExampleAdapter(exampleList);
        recyclerView.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

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

        final Handler h = new Handler();

    }


}