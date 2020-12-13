package com.drifting.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.drifting.ui.bag.BagFragment;
import com.drifting.ui.chat.ChatFragment;
import com.drifting.ui.homepage.HomeFragment;
import com.drifting.ui.setting.SettingFragment;
import com.example.drifting.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavBar extends AppCompatActivity {


    public static FragmentManager fm;
    public static ChatFragment chatf;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fm = getSupportFragmentManager();
        chatf = new ChatFragment();
        chatf.createExampleList(true);

        setContentView(R.layout.activity_nav_bar);

        BottomNavigationView bottomNav=findViewById(R.id.bottomNav);

        bottomNav.getMenu().getItem(2).setChecked(true);


        bottomNav.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

//        Handler mHandler = new Handler();
//        mHandler.postDelayed(new Runnable() {
//            public void run() {
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
//            }
//        }, 3000);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod=new
            BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment fragment = null;

            switch (menuItem.getItemId())
            {
                case R.id.chat:
                    fragment = chatf;
                    chatf.createExampleList(false);
                    break;

                case R.id.setting:
                    fragment = new SettingFragment();
                    break;

                case R.id.home:
                    fragment = new HomeFragment();
                    break;

                case R.id.bag:
                    fragment = new BagFragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).detach(fragment).commitNowAllowingStateLoss();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).attach(fragment).commitNowAllowingStateLoss();

            return true;
        }
    };
}