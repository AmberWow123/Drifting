package com.example.drifting;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.example.drifting.ui.login.ViewBottleActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import backend.util.database.Bottle_back;

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




    public static ArrayList<String> pickedBottle = new ArrayList<String>();
    public static ArrayList<String> pickedTime = new ArrayList<String>();;

    public static ArrayList<String> sentBottle = new ArrayList<String>();
    public static ArrayList<String> sentTime = new ArrayList<String>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button picked_button;
    Button sent_button;
    TableLayout tableLayout;
    ImageView sent_indicator;
    ImageView picked_indicator;

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

<<<<<<< Updated upstream
        //get database reference
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("bottle");
        //get current userID
        FirebaseAuth fAuth;
        fAuth = FirebaseAuth.getInstance();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Bottle_back this_bottle = snapshot1.getValue(Bottle_back.class);
                    //String bottleID = this_bottle.getBottleID();
                    String userID = fAuth.getUid();
                    //check if the bottle is sent from the user
                    if(this_bottle.getUserID() == userID){
                        sentBottle.add(this_bottle.getMessage());
                        sentTime.add(this_bottle.getCity());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
=======
        picked_button = getView().findViewById(R.id.picked_button);
        sent_button = getView().findViewById(R.id.sent_button);
        linearLayout = (LinearLayout) getView().findViewById(R.id.bag_table_layout);
        sent_indicator = getView().findViewById(R.id.sent_indicator);
        picked_indicator = getView().findViewById(R.id.picked_indicator);



>>>>>>> Stashed changes

        picked_button = getView().findViewById(R.id.picked_button);
        sent_button = getView().findViewById(R.id.sent_button);
        tableLayout = (TableLayout) getView().findViewById(R.id.bag_table_layout);
        sent_indicator = getView().findViewById(R.id.sent_indicator);
        picked_indicator = getView().findViewById(R.id.picked_indicator);
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int width_content = (int) (300 * scale + 0.5f);
        int width_date = (int) (120 * scale + 0.5f);
        int height = (int) (80 * scale + 0.5f);

        sent_indicator.setVisibility(View.VISIBLE);
        picked_indicator.setVisibility(View.VISIBLE);



        picked_button.setOnClickListener(new Button.OnClickListener(){
             @Override
            public void onClick(View v) {
<<<<<<< Updated upstream
                 tableLayout.removeAllViews();
=======
                 //get current userID
                 FirebaseAuth fAuth;
                 fAuth = FirebaseAuth.getInstance();
                 String userID = fAuth.getUid();
                 DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                 DatabaseReference user_ref = ref.child("user").child(userID).child("receive_list");

                 Object hm_obj = new Object();

                 //ArrayList<String> bottle_ids = new ArrayList<String>();
                 user_ref.addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         Log.d("ref", user_ref.toString());
                         HashMap<String, Boolean> hp = (HashMap)snapshot.getValue(hm_obj.getClass());
                         for (Map.Entry<String, Boolean> set : hp.entrySet()) {
                             if(set.getValue() == true) {
                                 //set.getKey() is the bottle id
                                 //Log.d("HashMap: ","Key: "+ set.getKey() + " Val: " + set.getValue());
                                 DatabaseReference bottle_ref = ref.child("bottle").child(set.getKey());
                                 bottle_ref.addValueEventListener(new ValueEventListener() {
                                     @Override
                                     public void onDataChange(@NonNull DataSnapshot snapshot_2) {
                                         String msg = snapshot_2.child("message").getValue(String.class);
                                         pickedBottle.add(msg);
                                         long time = snapshot_2.child("timestamp").getValue(Long.class);
                                         pickedTime.add(String.valueOf(time));
                                         String city = snapshot_2.child("city").getValue(String.class);
                                         pickedLocation.add(city);
                                         //Log.d("Msg ", msg);
                                         //Log.d("Time ", String.valueOf(time));
                                         //Log.d("City", city);
                                     }

                                     @Override
                                     public void onCancelled(@NonNull DatabaseError error) {

                                     }
                                 });
                             }
                         }

                         //Log.d("userId", "UserID " + userID);
                         //Log.d("sentBottle", "Bottle " + sentBottle.toString());
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
                 linearLayout.removeAllViews();
>>>>>>> Stashed changes
                 sent_indicator.setVisibility(View.GONE);
                 picked_indicator.setVisibility(View.VISIBLE);

                 for(int i=0; i<pickedBottle.size(); i++) {
<<<<<<< Updated upstream
                     String content = pickedBottle.get(i);
                     String date = pickedTime.get(i);
                     TableRow row = new TableRow(getActivity());


                     TextView textView1 = new TextView(getActivity());
                     TextView textView2 = new TextView(getActivity());
                     TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                     row.setLayoutParams(lp1);
                     lp1.gravity = Gravity.CENTER_VERTICAL;
                     textView1.setLayoutParams(lp1);
                     textView1.setText(content);
                     textView1.setWidth(width_content);
                     textView1.setHeight(height);
                     textView1.setBackgroundResource(R.drawable.textline);
                     textView1.setMaxLines(1);
                     textView1.setGravity(Gravity.CENTER_VERTICAL);

                     textView2.setLayoutParams(lp1);
                     textView2.setGravity(Gravity.CENTER_VERTICAL);
                     textView2.setText(date);
                     textView2.setWidth(width_date);
                     textView2.setHeight(height);
                     textView2.setBackgroundResource(R.drawable.textline);

                     Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.poppins);
                     textView1.setTypeface(typeface);
                     textView2.setTypeface(typeface);
                     textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                     textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                     textView1.setTextColor(0xFF949494);
                     textView2.setTextColor(0xFF949494);
                     row.addView(textView1);
                     row.addView(textView2);
                     tableLayout.addView(row,i);
                     row.setOnClickListener(new View.OnClickListener() {
=======
                     //LinearLayout row = new LinearLayout(getActivity());
                     LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
>>>>>>> Stashed changes
                         @Override
                         public void onClick(View v) {
                             startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                         }
                     });
                 }
<<<<<<< Updated upstream
=======
                 pickedTime.clear();
                 pickedBottle.clear();
                 pickedLocation.clear();
>>>>>>> Stashed changes
            }


        });

        sent_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< Updated upstream
                tableLayout.removeAllViews();
=======
                //get current userID
                FirebaseAuth fAuth;
                fAuth = FirebaseAuth.getInstance();
                String userID = fAuth.getUid();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                DatabaseReference user_ref = ref.child("user").child(userID).child("send_list");

                Object hm_obj = new Object();

                //ArrayList<String> bottle_ids = new ArrayList<String>();
                user_ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String, Boolean> hp = (HashMap)snapshot.getValue(hm_obj.getClass());
                        for (Map.Entry<String, Boolean> set : hp.entrySet()) {
                            //set.getKey() is the bottle id
                            //Log.d("HashMap: ","Key: "+ set.getKey() + " Val: " + set.getValue());
                            DatabaseReference bottle_ref = ref.child("bottle").child(set.getKey());
                            bottle_ref.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot_2) {
                                    String msg = snapshot_2.child("message").getValue(String.class);
                                    sentBottle.add(msg);
                                    long time = snapshot_2.child("timestamp").getValue(Long.class);
                                    sentTime.add(String.valueOf(time));
                                    String city = snapshot_2.child("city").getValue(String.class);
                                    sentLocation.add(city);
                                    //Log.d("Msg ", msg);
                                    //Log.d("Time ", String.valueOf(time));
                                    //Log.d("City", city);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }

                        //Log.d("userId", "UserID " + userID);
                        //Log.d("sentBottle", "Bottle " + sentBottle.toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                linearLayout.removeAllViews();
>>>>>>> Stashed changes
                sent_indicator.setVisibility(View.VISIBLE);
                picked_indicator.setVisibility(View.GONE);


                for (int i = 0; i < sentBottle.size(); i++) {
                    String content = sentBottle.get(i);
                    String date = sentTime.get(i);
                    TableRow row = new TableRow(getActivity());


                    TextView textView1 = new TextView(getActivity());
                    TextView textView2 = new TextView(getActivity());
                    TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    row.setLayoutParams(lp1);
                    lp1.gravity = Gravity.CENTER_VERTICAL;
                    textView1.setLayoutParams(lp1);
                    textView1.setText(content);
                    textView1.setWidth(width_content);
                    textView1.setHeight(height);
                    textView1.setBackgroundResource(R.drawable.textline);
                    textView1.setMaxLines(1);
                    textView1.setGravity(Gravity.CENTER_VERTICAL);

                    textView2.setLayoutParams(lp1);
                    textView2.setGravity(Gravity.CENTER_VERTICAL);
                    textView2.setText(date);
                    textView2.setWidth(width_date);
                    textView2.setHeight(height);
                    textView2.setBackgroundResource(R.drawable.textline);

                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.poppins);
                    textView1.setTypeface(typeface);
                    textView2.setTypeface(typeface);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                    textView1.setTextColor(0xFF949494);
                    textView2.setTextColor(0xFF949494);
                    row.addView(textView1);
                    row.addView(textView2);
                    tableLayout.addView(row, i);
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getActivity(), ViewBottleActivity.class));
                        }
                    });

                }
                sentBottle.clear();
<<<<<<< Updated upstream
=======
                sentTime.clear();
                sentLocation.clear();

>>>>>>> Stashed changes
            }

        });
        sent_button.performClick();
        sent_button.setSoundEffectsEnabled(true);
        picked_button.setSoundEffectsEnabled(true);
    }
}