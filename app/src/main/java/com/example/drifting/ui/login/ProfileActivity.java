package com.example.drifting.ui.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.drifting.R;

public class ProfileActivity extends AppCompatActivity {

    Button editButton;
    EditText nameEdit;
    EditText des_Edit;
    EditText email_Edit;
    EditText gen_Edit;
    EditText age_Edit;
    EditText coun_Edit;
    Button settingbutton;
    ImageView profileImage;
    Button changeProfileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        editButton = findViewById(R.id.editbutton);
        nameEdit = findViewById(R.id.username_edit);
        des_Edit = findViewById(R.id.description_text_edit);
        email_Edit = findViewById(R.id.email_text_edit);
        gen_Edit = findViewById(R.id.gender_text_edit);
        age_Edit = findViewById(R.id.age_text_edit);
        coun_Edit = findViewById(R.id.country_text_edit);
        settingbutton = findViewById(R.id.settingbutton);
        profileImage = findViewById(R.id.profile_image);
        changeProfileImage = findViewById(R.id.change_avatar);

        changeProfileImage.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent openGallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGallaryIntent, 1000);
            }
        });

        editButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewSwitcher nameSwitcher = findViewById(R.id.my_switcher);
                nameSwitcher.showNext();
                TextView nameTV = nameSwitcher.findViewById(R.id.username_view);
                nameTV.setText(nameEdit.getText().toString());

                ViewSwitcher des_switcher = findViewById(R.id.my_switcher_description);
                des_switcher.showNext();
                TextView des_TV = des_switcher.findViewById(R.id.description_text_view);
                des_TV.setText(des_Edit.getText().toString());

                ViewSwitcher email_switcher = findViewById(R.id.my_switcher_email);
                email_switcher.showNext();
                TextView email_TV = email_switcher.findViewById(R.id.email_text_view);
                email_TV.setText(email_Edit.getText().toString());

                ViewSwitcher gen_switcher = findViewById(R.id.my_switcher_gender);
                gen_switcher.showNext();
                TextView gen_TV = gen_switcher.findViewById(R.id.gender_text_view);
                gen_TV.setText(gen_Edit.getText().toString());

                ViewSwitcher age_switcher = findViewById(R.id.my_switcher_age);
                age_switcher.showNext();
                TextView age_TV = age_switcher.findViewById(R.id.age_text_view);
                age_TV.setText(age_Edit.getText().toString());

                ViewSwitcher coun_switcher = findViewById(R.id.my_switcher_country);
                coun_switcher.showNext();
                TextView coun_TV = coun_switcher.findViewById(R.id.country_text_view);
                coun_TV.setText(coun_Edit.getText().toString());
            }
        });

        Intent intent = new Intent(this, SettingActivity.class);

        settingbutton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){
            if (resultCode  == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                profileImage.setImageURI(imageUri);
            }
        }
    }

}