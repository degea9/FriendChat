package com.android.friendchat.signin;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.friendchat.R;
import com.android.friendchat.base.BaseActivity;
import com.android.friendchat.base.BaseFragment;
import com.android.friendchat.main.RoomActivity;
import com.android.friendchat.utils.LogUtil;
import com.android.friendchat.view.activity.ProfileActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends BaseFragment implements SignInView{
    private static final String TAG = SignInFragment.class.getSimpleName();
    @Bind(R.id.input_layout_email)
    TextInputLayout inputLayoutEmail;
    @Bind(R.id.input_layout_password)
    TextInputLayout inputLayoutPassword;
    @Bind(R.id.input_email)
    EditText edtEmail;
    @Bind(R.id.input_password)
    EditText edtPassword;
    @Bind(R.id.fb_login_button)
    LoginButton fbLoginButton;
    SignInPresenter mPresenter;
    //FaceBook
    private CallbackManager mCallbackManager;

    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this,view);
        mPresenter = new SignInPresenter(this);
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

        mCallbackManager = CallbackManager.Factory.create();
        fbLoginButton.setReadPermissions("email", "public_profile");
        fbLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                LogUtil.d(TAG, "facebook:onSuccess:" + loginResult);
                Toast.makeText(getContext(),"facebook:onSuccess:",Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                LogUtil.d(TAG, "facebook:onCancel");
                Toast.makeText(getContext(),"facebook:onCancel",Toast.LENGTH_SHORT).show();
                // ...
            }

            @Override
            public void onError(FacebookException error) {

                Log.d(TAG, "facebook:onError", error);
                Toast.makeText(getContext(),"facebook:onError",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btn_login)
    public void login() {
        ((BaseActivity) getActivity()).showProgressDialog(getString(R.string.authenticating));
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        mPresenter.signIn(email, password);
    }

    @OnClick(R.id.link_signup)
    public void signUp(){
        ((LoginActivity)getActivity()).addSignUpFragment();
    }

    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        Toast.makeText(getContext(), "handleFacebookAccessToken:" + token, Toast.LENGTH_SHORT).show();
        mPresenter.signInWithFacebook(token.getToken());
    }

    @Override
    public void navigateToProfile() {
        ((BaseActivity) getActivity()).dissmissProgressDialog();
        ((BaseActivity) getActivity()).navigateTo(RoomActivity.class);
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
