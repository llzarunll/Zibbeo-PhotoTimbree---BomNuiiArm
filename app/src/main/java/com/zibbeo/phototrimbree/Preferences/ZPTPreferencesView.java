package com.zibbeo.phototrimbree.Preferences;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.R;

public class ZPTPreferencesView extends BaseNavigationDrawer {

    View contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.zpt_preferences_view, null, false);
        mDrawerLayout.addView(contentView, 0);
    }
}
