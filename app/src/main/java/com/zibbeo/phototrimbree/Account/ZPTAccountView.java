package com.zibbeo.phototrimbree.Account;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.zibbeo.phototrimbree.AXSessionAuthentification;
import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.Constants.ZPTConstants;
import com.zibbeo.phototrimbree.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class ZPTAccountView extends BaseNavigationDrawer {

    private CallbackManager callbackManager;

    View contentView;
    EditText emailEditText;
    EditText passwordEditText;
    Button signInButton;
    LoginButton signInWithFacebookButton;
    Button signUpButton;
    Button passwordForgotButton;
    TextView or;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_account_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        or = (TextView) contentView.findViewById(R.id.or);
        emailEditText = (EditText) contentView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) contentView.findViewById(R.id.passwordEditText);
        signInButton = (Button) contentView.findViewById(R.id.signInButton);
        signInWithFacebookButton = (LoginButton) contentView.findViewById(R.id.signInWithFacebookButton);
        signUpButton = (Button) contentView.findViewById(R.id.signUpButton);
        passwordForgotButton = (Button) contentView.findViewById(R.id.passwordForgotButton);




    }

    @Override
    protected void onResume() {
        super.onResume();

        //region SIGN IN BUTTON
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tEmail = emailEditText.getText().toString();
                String tPassword = passwordEditText.getText().toString();

                if (!tEmail.equals("") && !tPassword.equals("")) {
                    //region get version app
                    PackageManager manager;
                    PackageInfo info;
                    String version = "";
                    try {
                        manager = contentView.getContext().getPackageManager();
                        info = manager.getPackageInfo(contentView.getContext().getPackageName(), 0);
                        version = info.versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    //endregion

                    //connect to server
                    connectionTask signinTask = new connectionTask(contentView.getContext(),R.string.doSignIn);
                    signinTask.execute(ZPTConstants.signinURL, tEmail, tPassword, version);
                    //go to waiting page
                    Intent intent = new Intent(contentView.getContext(), ZPTAccountConnectionWaitingView.class);
                    startActivity(intent);

                    Log.i("Task Status",signinTask.getStatus().toString());

                } else {
                    Toast.makeText(contentView.getContext(), "Please insert empty field.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        //endregion

        //region PASSWORD FORGOT BUTTON
        passwordForgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contentView.getContext(), ZPTAccountForgotView.class);
                startActivity(intent);
            }
        });
        //endregion

        //region SIGN UP BUTTON
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contentView.getContext(), ZPTAccountSubscriptionView.class);
                startActivity(intent);
            }
        });
        //endregion

        //region SIGN IN FACEBOOK BUTTON
        signInWithFacebookButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("FBsuccess", "" + loginResult.getAccessToken().getUserId() + " " + loginResult.getAccessToken().getToken());
                // set authentification
                mAuthen.clearAllData();
                mAuthen.setStatus(R.string.statusConnected);
                mAuthen.setType(R.string.typeFacebook);
                mAuthen.setFBID(loginResult.getAccessToken().getUserId());
                mAuthen.setFBToken(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.i("FBcancel", "cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.i("FBerror", "" + error);
            }
        });
        //endregion

//        or.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuthen.clearAllData();
//                mAuthen.print();
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
