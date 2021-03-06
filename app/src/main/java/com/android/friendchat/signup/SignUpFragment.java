package com.android.friendchat.signup;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.main.MainActivity;
import com.android.friendchat.signin.LoginActivity;
import com.facebook.login.widget.LoginButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends BaseFragment implements SignUpView {
    private static final String TAG = SignUpFragment.class.getSimpleName();
    @Bind(R.id.input_layout_username)
    TextInputLayout inputLayoutUsername;
    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.input_username)
    EditText edtUsername;
    @Bind(R.id.input_email)
    EditText edtEmail;
    @Bind(R.id.input_password)
    EditText edtPassword;
    @Bind(R.id.fb_login_button)
    LoginButton fbLoginButton;
    SignUpPresenter mPresenter;


    public SignUpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        ButterKnife.bind(this, view);
        mPresenter = new SignUpPresenter(this);
        setupView();
        return view;
    }

    private void setupView() {
        edtUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayoutUsername.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputLayoutPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick(R.id.btn_signup)
    public void signUp() {
        ((BaseActivity) getActivity()).showProgressDialog(getString(R.string.connecting));
        String username = edtUsername.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        mPresenter.signUp(username,email, password);
    }

    @OnClick(R.id.link_login)
    public void login(){
        ((LoginActivity)getActivity()).addLoginFragment();
    }

    @Override
    public void showValidateErrorMessage(String errMessage) {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        inputLayoutUsername.setError(errMessage);
        inputLayoutEmail.setError(errMessage);
        inputLayoutPassword.setError(errMessage);
    }

    @Override
    public void navigateToMain() {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        ((BaseActivity) getActivity()).navigateTo(MainActivity.class);
    }

    @Override
    public void showSignUpFailureMessage(String reason) {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        ((BaseActivity) getActivity()).showMessageDialog(reason);
    }
}
