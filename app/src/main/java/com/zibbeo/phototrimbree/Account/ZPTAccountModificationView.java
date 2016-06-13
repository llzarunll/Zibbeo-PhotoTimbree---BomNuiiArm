package com.zibbeo.phototrimbree.Account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zibbeo.phototrimbree.AXSessionAuthentification;
import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.Constants.ZPTConstants;
import com.zibbeo.phototrimbree.R;

public class ZPTAccountModificationView extends BaseNavigationDrawer {

    View contentView;

    Button modificationButton;
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_account_modification_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        modificationButton = (Button) contentView.findViewById(R.id.modificationButton);
        emailEditText = (EditText) contentView.findViewById(R.id.emailEditText);
        passwordEditText = (EditText) contentView.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = (EditText) contentView.findViewById(R.id.confirmPasswordEditText);

    }

    @Override
    protected void onResume() {
        super.onResume();

        //region modification button
        modificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tEmail = emailEditText.getText().toString();
                String tPassword = passwordEditText.getText().toString();
                String tConfirmPassword = confirmPasswordEditText.getText().toString();

                if (!tEmail.equals("") && !tPassword.equals("") && !tConfirmPassword.equals("")) {
                    if (!tPassword.equals(tConfirmPassword)){
                        Toast.makeText(contentView.getContext(), "Confirm password is incorrect.",
                                Toast.LENGTH_LONG).show();
                    } else {
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
                        connectionTask modificationTask = new connectionTask(contentView.getContext(),R.string.doModify);
                        modificationTask.execute(ZPTConstants.signupURL, tEmail, tPassword, version);
                        //go to waiting page
                        Intent intent = new Intent(contentView.getContext(), ZPTAccountConnectionWaitingView.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(contentView.getContext(), "Please insert empty field.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
        //endregion
    }


}
