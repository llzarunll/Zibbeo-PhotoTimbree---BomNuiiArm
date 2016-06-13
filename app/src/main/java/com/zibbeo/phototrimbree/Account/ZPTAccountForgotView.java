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
import android.widget.Toast;

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

import javax.net.ssl.HttpsURLConnection;

public class ZPTAccountForgotView extends BaseNavigationDrawer {

    View contentView;

    Button passwordRequestButton;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_account_forgot_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        passwordRequestButton = (Button) contentView.findViewById(R.id.passwordRequestButton);
        emailEditText = (EditText) contentView.findViewById(R.id.emailEditText);

        passwordRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!emailEditText.getText().toString().equals("")){
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
                    String tEmail = emailEditText.getText().toString();
                    //connect to server
                    connectionTask passwordForgotTask = new connectionTask(contentView.getContext(),R.string.doPasswordForgot);
                    passwordForgotTask.execute(ZPTConstants.passwordRequestURL, tEmail, version);

                } else {
                    Toast.makeText(contentView.getContext(), "Please insert email.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
