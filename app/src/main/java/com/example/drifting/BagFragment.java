package com.example.drifting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.drifting.ui.login.ViewBagBottleActivity;

import java.util.ArrayList;

import backend.util.container.BagData;
import backend.util.database.SetDatabase;
import backend.util.time.DriftTime;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static ArrayList<String> pickedBottle = BagData.pickedBottle;
    public static ArrayList<String> pickedTime = BagData.pickedTime;
    public static ArrayList<String> pickedLocation = BagData.pickedLocation;
    public static ArrayList<String> sentBottle = BagData.sentBottle;
    public static ArrayList<String> sentTime = BagData.sentTime;
    public static ArrayList<String> sentLocation = BagData.sentLocation;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button picked_button;
    Button sent_button;
    LinearLayout linearLayout;
    ImageView sent_indicator;
    ImageView picked_indicator;
    //TextView bag_date;
    //TextView bag_content;

    public BagFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BagFragment newInstance(String param1, String param2) {
        BagFragment fragment = new BagFragment();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bag, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        picked_button = getView().findViewById(R.id.picked_button);
        sent_button = getView().findViewById(R.id.sent_button);
        linearLayout = (LinearLayout) getView().findViewById(R.id.bag_table_layout);
        sent_indicator = getView().findViewById(R.id.sent_indicator);
        picked_indicator = getView().findViewById(R.id.picked_indicator);





        picked_button.setOnClickListener(new Button.OnClickListener(){
             @Override
            public void onClick(View v) {

                 DriftTime d_time = new DriftTime();

                 linearLayout.removeAllViews();
                 sent_indicator.setVisibility(View.GONE);
                 picked_indicator.setVisibility(View.VISIBLE);

                 for(int i=0; i<pickedBottle.size(); i++) {
                     //LinearLayout row = new LinearLayout(getActivity());
                     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                             LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                     layoutParams.setMargins(0, 0, 0, 10);
                     View customView = getLayoutInflater().inflate(R.layout.bag_item, null);
                     TextView bag_content = (TextView)customView.findViewById(R.id.textView_bag_content);
                     TextView bag_date = (TextView) customView.findViewById(R.id.textView_bag_time);
                     TextView bag_location = (TextView) customView.findViewById(R.id.textView_bag_location);
                     bag_date.setText(pickedTime.get(i));
                     bag_content.setText(pickedBottle.get(i));
                     bag_location.setText(pickedLocation.get(i));
                     linearLayout.addView(customView, layoutParams);
                     customView.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             startActivity(new Intent(getActivity(), ViewBagBottleActivity.class));
                         }
                     });
                 }

//                 pickedTime.clear();
//                 pickedBottle.clear();
//                 pickedLocation.clear();
            }
        });

        sent_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                DriftTime d_time = new DriftTime();
                linearLayout.removeAllViews();
                sent_indicator.setVisibility(View.VISIBLE);
                picked_indicator.setVisibility(View.GONE);

                for (int i = 0; i < sentBottle.size(); i++) {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 10);
                    View customView = getLayoutInflater().inflate(R.layout.bag_item, null);
                    TextView bag_content = (TextView)customView.findViewById(R.id.textView_bag_content);
                    TextView bag_date = (TextView) customView.findViewById(R.id.textView_bag_time);
                    TextView bag_location = (TextView) customView.findViewById(R.id.textView_bag_location);
                    bag_date.setText(sentTime.get(i));
                    bag_content.setText(sentBottle.get(i));
                    bag_location.setText(sentLocation.get(i));
                    linearLayout.addView(customView, layoutParams);
                    customView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), ViewBagBottleActivity.class));
                        }
                    });
                }

//                sentBottle.clear();
//                sentTime.clear();
//                sentLocation.clear();
            }
        });
        sent_button.performClick();
        sent_button.setSoundEffectsEnabled(true);
        picked_button.setSoundEffectsEnabled(true);

    }

}

