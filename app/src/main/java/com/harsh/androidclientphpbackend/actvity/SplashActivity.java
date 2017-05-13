package com.harsh.androidclientphpbackend.actvity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;

import com.harsh.androidclientphpbackend.R;
import com.harsh.androidclientphpbackend.util.Util;


public class SplashActivity extends AppCompatActivity {

    Handler splashHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashHandler = new Handler();
        splashHandler.postDelayed(splashRunnable, Util.DELAY_MILLIS);
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        if (evt.getAction() == MotionEvent.ACTION_DOWN) {
            splashHandler.removeCallbacks(splashRunnable);
            launchMainActivity();
        }
        return true;
    }

    private Runnable splashRunnable = new Runnable() {
        @Override
        public void run() {
            launchMainActivity();
        }
    };

    private void launchMainActivity() {
        finish();
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
