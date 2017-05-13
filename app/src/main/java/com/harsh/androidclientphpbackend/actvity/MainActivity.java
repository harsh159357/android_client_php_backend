package com.harsh.androidclientphpbackend.actvity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.harsh.androidclientphpbackend.R;
import com.harsh.androidclientphpbackend.fragment.LoginFragment;
import com.harsh.androidclientphpbackend.fragment.ProfileFragment;
import com.harsh.androidclientphpbackend.util.Util;


public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getPreferences(0);
        initFragment();
    }

    private void initFragment() {
        Fragment fragment;
        if (sharedPreferences.getBoolean(Util.IS_LOGGED_IN, false)) {
            fragment = new ProfileFragment();
        } else {
            fragment = new LoginFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
    }

}
