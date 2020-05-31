/*
 * Copyright 2017 Harsh Sharma
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.harsh.androidclientphpbackend.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

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
