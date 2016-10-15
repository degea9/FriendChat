package com.android.friendchat.auth;


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
import com.android.friendchat.view.activity.ProfileActivity;
import com.android.friendchat.view.contract.AuthContract;
import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment implements AuthContract {
    private static final String TAG = LoginFragment.class.getSimpleName();
    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.input_email)
    EditText edtEmail;
    @Bind(R.id.input_password)
    EditText edtPassword;
    AuthPresenter mPresenter;
    //FaceBook
    private CallbackManager mCallbackManager;
    private FirebaseAuth mAuth;


    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        mPresenter = new AuthPresenter(this);
        setupView();
        return view;
    }

    private void setupView() {
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

    @OnClick(R.id.btn_login)
    public void login() {
        ((BaseActivity) getActivity()).showProgressDialog(getString(R.string.authenticating));
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        mPresenter.login(email, password);
    }

    @OnClick(R.id.fb_login_button)
    public void facebookLogin() {
    }

    @Override
    public void navigateToProfile() {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        ((BaseActivity) getActivity()).navigateTo(ProfileActivity.class);
    }

    @Override
    public void showLoginFailureMessage() {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        ((BaseActivity) getActivity()).showMessageDialog(getString(R.string.login_failure));
    }

    @Override
    public void showValidateErrorMessage(String errMessage) {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        inputLayoutEmail.setError(errMessage);
        inputLayoutPassword.setError(errMessage);
    }
}
