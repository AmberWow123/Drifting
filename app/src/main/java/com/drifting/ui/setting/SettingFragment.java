package com.drifting.ui.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.drifting.ui.account.LoginActivity;
import com.example.drifting.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import com.drifting.container.BagData;
import com.drifting.database.SetDatabase;
import com.drifting.database.models.UserProfile;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static  String name = null;
    private static  String age = null;
    private static  String email = null;
    private static  String gender = null;
    private static  String country = null;
    private static  String privacy = null;
    private static HashMap<String, Boolean> receive_list;
    private static HashMap<String, Boolean> send_list;



    private SetDatabase set = new SetDatabase();
    FirebaseUser auth = FirebaseAuth.getInstance().getCurrentUser();

    private Spinner spinner;
    private static final String[] paths = {"item 1", "item 2", "item 3"};

    protected View mView;

    Button editButton;
    Button editUsernameButton;
    Button editEmailButton;
    Button editAgeButton;
    Button editCountryButton;
    Button editGenderButton;
    Button logout_button;
    //Button reset_password;
    EditText nameEdit;
    EditText des_Edit;
    EditText email_Edit;
    EditText gen_Edit;
    EditText age_Edit;
    EditText coun_Edit;
    Button settingbutton;
    ImageView profileImage;
    Button changeProfileImage;

    ViewSwitcher name1Switcher  ;
    ViewSwitcher age_1switcher;
    ViewSwitcher email_1switcher;
    ViewSwitcher coun_1switcher;
    Spinner gender_spinner;
    Spinner privacy_spinner;


    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);

        mView = rootView;
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editButton = getView().findViewById(R.id.editbutton);

        editUsernameButton = getView().findViewById(R.id.username_edit_button);
        editEmailButton = getView().findViewById(R.id.edit_email_button);
        editAgeButton = getView().findViewById(R.id.edit_age_button);
        editCountryButton = getView().findViewById(R.id.edit_country_button);
        //editGenderButton = getView().findViewById(R.id.edit_gender_button);
        nameEdit = getView().findViewById(R.id.username_edit);
        des_Edit = getView().findViewById(R.id.description_text_edit);
        email_Edit = getView().findViewById(R.id.email_text_edit);
        //gen_Edit = getView().findViewById(R.id.gender_text_edit);
        age_Edit = getView().findViewById(R.id.age_text_edit);
        coun_Edit = getView().findViewById(R.id.country_text_edit);
        settingbutton = getView().findViewById(R.id.settingbutton);
        profileImage = getView().findViewById(R.id.profile_image);
        changeProfileImage = getView().findViewById(R.id.change_avatar);
        logout_button = getView().findViewById(R.id.add_friend_button);
        //reset_password = getView().findViewById(R.id.reset_password_button);

        name1Switcher = getView().findViewById(R.id.my_switcher);
        age_1switcher = getView().findViewById(R.id.my_switcher_age);
        email_1switcher = getView().findViewById(R.id.my_switcher_email);
        coun_1switcher = getView().findViewById(R.id.my_switcher_country);
        gender_spinner = getView().findViewById(R.id.spinner2);
        //privacy_spinner = getView().findViewById(R.id.spinner1);

        //get the spinner from the xml.
        //preference of privacy

        //String[] items_1 = new String[]{"Not visible to others", "Visible to friends only", "Visible to all"};
        //ArrayAdapter<String> adapter_privacy = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, R.id.dropdown_item, items_1);
        //privacy_spinner.setAdapter(adapter_privacy);


        //gender spinner
        String[] items_2 = new String[]{"Unspecified", "Female", "Male"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spinner_item, R.id.dropdown_item, items_2);
        gender_spinner.setAdapter(adapter);

        set.getProfile(this::updateProfile);

        set.getAvatar(this::updateAvatar);



        changeProfileImage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openGallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallaryIntent, 1000);
            }
        });

        logout_button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                BagData.clear();
                startActivity(intent);
                getActivity().finish();
            }
        });

//        reset_password.setOnClickListener(new Button.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ForgotPasswordActivity.class);
//                startActivity(intent);
//                getActivity().finish();
//            }
//        });


        editUsernameButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitcher nameSwitcher = getView().findViewById(R.id.my_switcher);
                nameSwitcher.showNext();
                TextView nameTV = nameSwitcher.findViewById(R.id.username_view);
                nameTV.setText(nameEdit.getText().toString());
                String name = nameEdit.getText().toString();

                UserProfile us = new UserProfile(auth.getUid(), name, email,  null, null, gender, country, age, privacy, receive_list, send_list);
                SetDatabase set = new SetDatabase();
                set.addNewUser(us);

            }
        });

        editEmailButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitcher email_switcher = getView().findViewById(R.id.my_switcher_email);
                email_switcher.showNext();
                TextView email_TV = email_switcher.findViewById(R.id.email_text_view);
                email_TV.setText(email_Edit.getText().toString());
                String email = email_Edit.getText().toString();

                UserProfile us = new UserProfile(auth.getUid(), name, email, null, null, gender, country, age, privacy, receive_list, send_list);
                SetDatabase set = new SetDatabase();
                set.addNewUser(us);
            }
        });

        editAgeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewSwitcher age_switcher = getView().findViewById(R.id.my_switcher_age);
                age_switcher.showNext();
                TextView age_TV = age_switcher.findViewById(R.id.age_text_view);
                age_TV.setText(age_Edit.getText().toString());
                String age = age_Edit.getText().toString();

                UserProfile us = new UserProfile(auth.getUid(), name, email,  null, null, gender, country, age, privacy, receive_list, send_list);
                SetDatabase set = new SetDatabase();
                set.addNewUser(us);
            }
        });

        editCountryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ViewSwitcher coun_switcher = getView().findViewById(R.id.my_switcher_country);
                coun_switcher.showNext();
                TextView coun_TV = coun_switcher.findViewById(R.id.country_text_view);
                coun_TV.setText(coun_Edit.getText().toString());
                String country = coun_Edit.getText().toString();

                UserProfile us = new UserProfile(auth.getUid(), name, email,  null, null, gender, country, age, privacy, receive_list, send_list);
                SetDatabase set = new SetDatabase();
                set.addNewUser(us);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode  == RESULT_OK){
                Uri imageUri = data.getData();

                CropImage.activity(imageUri)
                        .start(getContext(), this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                profileImage.setImageURI(resultUri);
                SetDatabase set = new SetDatabase();
                set.uploadAvatars(auth.getUid(),resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    public void updateAvatar(String input){
        Picasso.get().load(input).into(profileImage);
    }

    public void updateProfile(UserProfile profile) {
        name = profile.user_name;
        age = profile.age;
        email = profile.user_email;
        gender = profile.user_gender;
        country = profile.user_country;
        privacy = profile.privacy;
        receive_list = profile.receive_list;
        send_list = profile.send_list;

        TextView nameTV1 = name1Switcher.findViewById(R.id.username_view);
        Log.d("afeawf",profile.user_name);
        nameTV1.setText(profile.user_name);

        TextView age1TV = age_1switcher.findViewById(R.id.age_text_view);
        age1TV.setText(profile.age);

        TextView email_1TV = email_1switcher.findViewById(R.id.email_text_view);
        email_1TV.setText(profile.user_email);

        TextView coun_1TV = coun_1switcher.findViewById(R.id.country_text_view);
        coun_1TV.setText(profile.user_country);

        switch(profile.user_gender) {
            case "Unspecified":
                gender_spinner.setSelection(0);
                break;
            case "Female":
                gender_spinner.setSelection(1);
                break;
            case "Male":
                gender_spinner.setSelection(2);
                break;
            default:
                break;
        }

//        switch(profile.privacy) {
//            case "Not visible to others":
//                privacy_spinner.setSelection(0);
//                break;
//            case "Visible to friends only":
//                privacy_spinner.setSelection(1);
//                break;
//            case "Visible to all":
//                privacy_spinner.setSelection(2);
//                break;
//            default:
//                break;
//        }
        gender_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                UserProfile us = new UserProfile(auth.getUid(), name, email,null,  null, item, country, age, privacy, receive_list, send_list);
                SetDatabase set = new SetDatabase();
                set.addNewUser(us);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        privacy_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String item = parent.getItemAtPosition(position).toString();
//                UserProfile us = new UserProfile(auth.getUid(), name, email, null, null, gender, country, age, item, receive_list, send_list);
//                SetDatabase set = new SetDatabase();
//                set.addNewUser(us);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }
}
