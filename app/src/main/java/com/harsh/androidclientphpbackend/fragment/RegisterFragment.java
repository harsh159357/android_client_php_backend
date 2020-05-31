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
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.snackbar.Snackbar;
import com.harsh.androidclientphpbackend.R;
import com.harsh.androidclientphpbackend.model.ApiRequest;
import com.harsh.androidclientphpbackend.model.ApiResponse;
import com.harsh.androidclientphpbackend.model.User;
import com.harsh.androidclientphpbackend.util.ApiInterface;
import com.harsh.androidclientphpbackend.util.Util;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_register;
    private EditText et_email, et_password, et_name;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        btn_register = (AppCompatButton) view.findViewById(R.id.btn_register);
        tv_login = (TextView) view.findViewById(R.id.tv_login);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:
                Util.hideSoftKeyboard(getActivity());
                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    if (!Util.isValidEmail(email)) {
                        Snackbar.make(getView(), "Please Enter Valid Email !", Snackbar.LENGTH_LONG).show();
                        break;
                    }
                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name, email, password);
                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ApiRequest request = new ApiRequest();
        request.setOperation(Util.REGISTER_OPERATION);
        request.setUser(user);
        Call<ApiResponse> response = apiInterface.operation(request);

        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {

                ApiResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Util.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin() {

        Fragment login = new LoginFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, login);
        ft.commit();
    }
}
