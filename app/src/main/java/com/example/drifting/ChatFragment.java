package com.example.drifting;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.drifting.ui.login.ExampleAdapter;
import com.example.drifting.ui.login.ExampleItem;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChatFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ArrayList<ExampleItem> exampleList;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExampleAdapter adapter;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        final Button searchButton = getView().findViewById(R.id.search_button);
//        final EditText input_friend_to_search = getView().findViewById(R.id.search_field);

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