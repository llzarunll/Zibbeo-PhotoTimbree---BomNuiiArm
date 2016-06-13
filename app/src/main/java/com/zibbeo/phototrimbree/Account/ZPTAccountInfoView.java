package com.zibbeo.phototrimbree.Account;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zibbeo.phototrimbree.AXSessionAuthentification;
import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.R;

public class ZPTAccountInfoView extends BaseNavigationDrawer {

    View contentView;
    Button modifyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_account_info_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        modifyButton = (Button) contentView.findViewById(R.id.modifyButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mAuthen.getType().equals(String.valueOf(AXSessionAuthentification.
                AXSessionAuthentificationType.AXSessionAuthentificationTypeFacebook))){
            modifyButton.setVisibility(View.GONE);
        } else {
            modifyButton.setVisibility(View.VISIBLE);
        }
        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(contentView.getContext(), ZPTAccountModificationView.class);
                startActivity(intent);
            }
        });
    }
}
