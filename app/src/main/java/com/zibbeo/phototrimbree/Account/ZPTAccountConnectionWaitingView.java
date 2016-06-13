package com.zibbeo.phototrimbree.Account;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.zibbeo.phototrimbree.BaseNavigationDrawer;
import com.zibbeo.phototrimbree.R;

public class ZPTAccountConnectionWaitingView extends BaseNavigationDrawer {

    public static final String ACTION_CLOSE = "com.zibbeo.phototrimbre.ACTION_CLOSE";
    private doCloseReceiver doCloseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View contentView = inflater.inflate(R.layout.zpt_account_connection_waiting_view, null, false);
        mDrawerLayout.addView(contentView, 0);

        IntentFilter filter = new IntentFilter(ACTION_CLOSE);
        doCloseReceiver = new doCloseReceiver();
        registerReceiver(doCloseReceiver, filter);

        Button cancelButton = (Button)contentView.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(doCloseReceiver);
    }

    class doCloseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("doCloseReceiver", "doCloseReceiver");
            if (intent.getAction().equals(ACTION_CLOSE)) {
                ZPTAccountConnectionWaitingView.this.finish();
            }
        }
    }
}
