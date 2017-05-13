package com.harsh.androidclientphpbackend.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


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


public class LoginFragment extends Fragment implements View.OnClickListener {

    private AppCompatButton btn_login;
    private EditText et_email, et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        pref = getActivity().getPreferences(0);

        btn_login = (AppCompatButton) view.findViewById(R.id.btn_login);
        tv_register = (TextView) view.findViewById(R.id.tv_register);
        et_email = (EditText) view.findViewById(R.id.et_email);
        et_password = (EditText) view.findViewById(R.id.et_password);

        progress = (ProgressBar) view.findViewById(R.id.progress);

        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_login:
                Util.hideSoftKeyboard(getActivity());
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    if (!Util.isValidEmail(email)) {
                        Snackbar.make(getView(), "Please Enter Valid Email !", Snackbar.LENGTH_LONG).show();
                        break;
                    }
                    progress.setVisibility(View.VISIBLE);
                    loginProcess(email, password);

                } else {
                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

        }
    }

    private void loginProcess(String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Util.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ApiRequest request = new ApiRequest();
        request.setOperation(Util.LOGIN_OPERATION);
        request.setUser(user);
        Call<ApiResponse> response = apiInterface.operation(request);

        response.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, retrofit2.Response<ApiResponse> response) {

                ApiResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Util.SUCCESS)) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Util.IS_LOGGED_IN, true);
                    editor.putString(Util.EMAIL, resp.getUser().getEmail());
                    editor.putString(Util.NAME, resp.getUser().getName());
                    editor.putString(Util.UNIQUE_ID, resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();
                }
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

    private void goToRegister() {
        Fragment register = new RegisterFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, register);
        ft.commit();
    }

    private void goToProfile() {
        Fragment profile = new ProfileFragment();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, profile);
        ft.commit();
    }
}
