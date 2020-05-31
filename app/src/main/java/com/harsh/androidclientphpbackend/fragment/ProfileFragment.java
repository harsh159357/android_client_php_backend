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

package com.harsh.androidclientphpbackend.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.google.android.material.snackbar.Snackbar;
import com.harsh.androidclientphpbackend.R;
import com.harsh.androidclientphpbackend.activity.SplashActivity;
import com.harsh.androidclientphpbackend.model.ApiRequest;
import com.harsh.androidclientphpbackend.model.ApiResponse;
import com.harsh.androidclientphpbackend.model.User;
import com.harsh.androidclientphpbackend.util.ApiInterface;
import com.harsh.androidclientphpbackend.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView tv_name, tv_email, tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password, btn_logout;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        pref = getActivity().getPreferences(0);
        tv_name.setText("Welcome : " + pref.getString(Util.NAME, ""));
        tv_email.setText(pref.getString(Util.EMAIL, ""));
    }

    private void initViews(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_email = (TextView) view.findViewById(R.id.tv_email);
        btn_change_password = (AppCompatButton) view.findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton) view.findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText) view.findViewById(R.id.et_old_password);
        et_new_password = (EditText) view.findViewById(R.id.et_new_password);
        tv_message = (TextView) view.findViewById(R.id.tv_message);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if (!old_password.isEmpty() && !new_password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    changePasswordProcess(pref.getString(Util.EMAIL, ""), old_password, new_password);

                } else {
                    Util.hideSoftKeyboard(getActivity());
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }

    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Util.IS_LOGGED_IN, false);
        editor.putString(Util.EMAIL, "");
        editor.putString(Util.NAME, "");
        editor.putString(Util.UNIQUE_ID, "");
        editor.apply();
        ActivityCompat.finishAffinity(getActivity());
        Intent intent = new Intent(getActivity(), SplashActivity.class);
        intent.putExtra("finish", true);
        startActivity(intent);
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }

    private void changePasswordProcess(String email, String old_password, String new_password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ApiRequest request = new ApiRequest();
        request.setOperation(Util.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ApiResponse> response = apiInterface.operation(request);

        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {

                ApiResponse resp = response.body();
                if (resp.getResult().equals(Util.SUCCESS)) {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                    Util.hideSoftKeyboard(getActivity());
                } else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());
                    Util.hideSoftKeyboard(getActivity());
                }
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.d(Util.TAG, "failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());
                Util.hideSoftKeyboard(getActivity());
            }
        });
    }
}
